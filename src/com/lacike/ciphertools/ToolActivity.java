package com.lacike.ciphertools;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ToolActivity extends FragmentActivity {
	public static final String INDEX = "index";
	public static final String LABEL = "label";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_activity);

		Configuration configuration = getResources().getConfiguration();
		boolean landscape = (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE);
		//boolean largeScreen = ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
		boolean largeScreen = getResources().getBoolean(R.bool.screen_large);
		if (landscape && largeScreen) {
			// If the screen is now in landscape mode, we can show the
			// dialog in-line so we don't need this activity.
			finish();
			return;
		}

		if (savedInstanceState == null) {
			// During initial setup, plug in the tool fragment.
			Intent intent = getIntent();
			String label = intent.getStringExtra(LABEL);
			setTitle(label);
			int index = intent.getIntExtra(INDEX, 0);
			Fragment fragment = ToolFragmentFactory.newToolFragment(index);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.tool_frame, fragment).commit();
		}
	}
}
