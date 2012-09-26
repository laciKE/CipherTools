package com.lacike.ciphertools;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Checks dual pane mode (on tablets in landscape uses dual pane layout,
 * otherwise single pane layout). Shows {@link ToolsFragment} with list of
 * available tools, onItemSelected shows concrete tool.
 */
public class MainActivity extends FragmentActivity implements
		OnSharedPreferenceChangeListener, ToolsFragment.OnItemSelectedListener {

	public static final String INDEX = "mIndex";

	private boolean mSharedPreferenceChanged = false;
	private boolean mDualPane = false;
	private int mIndex = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		TypedArray themes = getResources().obtainTypedArray(R.array.themes_id);
		int themeId = themes.getResourceId(SettingsActivity.getTheme(this),
				R.style.AppTheme);
		setTheme(themeId);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools);

		mDualPane = (findViewById(R.id.tool_frame) != null);

		if (savedInstanceState != null) {
			mIndex = savedInstanceState.getInt(INDEX, -1);
		}

		if (mDualPane && (mIndex > -1)) {
			onItemSelected(mIndex);
		} else {
			Fragment fragment = getSupportFragmentManager().findFragmentById(
					R.id.tool_frame);
			if (fragment != null) {
				FragmentTransaction fragmentTransaction = getSupportFragmentManager()
						.beginTransaction();
				fragmentTransaction.remove(fragment);
				fragmentTransaction.commit();
			}
			mIndex = -1;
		}

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mSharedPreferenceChanged) {
			mSharedPreferenceChanged = false;
			Intent intent = getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(INDEX, mIndex);
	}

	/**
	 * Shows concrete tool associated with selected item. In dual pane layout
	 * shows fragment, in single pane starts {@link ToolActivity} with index
	 * parameter in {@link Intent}
	 */
	@Override
	public void onItemSelected(int index) {
		this.mIndex = index;
		if (mDualPane) {
			String title = getResources().getString(
					R.string.title_activity_main)
					+ "::"
					+ getResources().getStringArray(R.array.tools)[index];
			setTitle(title);
			Fragment fragment = ToolFragmentFactory.newToolFragment(index);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.tool_frame, fragment);
			// fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else {
			Intent intent = new Intent(this, ToolActivity.class);
			intent.putExtra(ToolActivity.INDEX, index);
			String toolLabel = getResources().getStringArray(R.array.tools)[index];
			intent.putExtra(ToolActivity.LABEL, toolLabel);
			startActivity(intent);
		}
	}

	/**
	 * Creates options menu with help and settings items.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	/**
	 * Handles help item selection.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.menu_preferences:
			showPreferences();
			return true;
		case R.id.menu_about:
			showMessageDialog(R.string.about, R.string.about_message);
			return true;
			/*
			 * case R.id.menu_help: // if none tool is selected, shows general
			 * help if ((mIndex == -1) || !mDualPane) {
			 * showMessageDialog(R.string.help, R.string.help_general); return
			 * true; }
			 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Shows dialog with title and message.
	 */
	protected void showMessageDialog(int title, int message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setPositiveButton(R.string.ok, null);
		dialog.show();
	}

	protected void showPreferences() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(SettingsActivity.THEME)) {
			mSharedPreferenceChanged = true;
		}
	}
}
