package com.lacike.ciphertools;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Creates fragment for tool, sets activity's title to tool's name. On
 * orientation change, checks large screen and landscape mode (dual pane
 * layout). If app should be in dual pane mode, finish.
 */
public class ToolActivity extends FragmentActivity {
	public static final String INDEX = "index";
	public static final String LABEL = "label";
	
	/**
	 * Names of shared fields in SharedBundle.
	 */
	public static final String REGEXP = "regexp";
	public static final String MESSAGE = "message";

	/**
	 * Checks dual pane mode, creates fragment for tool, sets activity's title
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		TypedArray themes = getResources().obtainTypedArray(R.array.themes_id);
		int themeId = themes.getResourceId(SettingsActivity.getTheme(this), R.style.AppTheme);
		setTheme(themeId);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_activity);

		Configuration configuration = getResources().getConfiguration();
		boolean landscape = (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE);
		// boolean largeScreen = ((configuration.screenLayout &
		// Configuration.SCREENLAYOUT_SIZE_MASK) >=
		// Configuration.SCREENLAYOUT_SIZE_LARGE);
		boolean largeScreen = getResources().getBoolean(R.bool.screen_large);
		if (landscape && largeScreen) {
			// If the screen is now in landscape mode, we can show the
			// dialog in-line so we don't need this activity.
			finish();
			return;
		}

		String label;

		if (savedInstanceState == null) {
			// During initial setup, plug in the tool fragment.
			Intent intent = getIntent();
			label = intent.getStringExtra(LABEL);
			int index = intent.getIntExtra(INDEX, 0);
			Fragment fragment = ToolFragmentFactory.newToolFragment(index);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.tool_frame, fragment).commit();
		} else {
			label = savedInstanceState.getString(LABEL);
		}

		setTitle(label);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(LABEL, (String) getTitle());
	}
}
