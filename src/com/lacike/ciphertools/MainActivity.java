package com.lacike.ciphertools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Checks dual pane mode (on tablets in landscape uses dual pane layout,
 * otherwise single pane layout). Shows {@link ToolsFragment} with list of
 * available tools, onItemSelected shows concrete tool.
 */
public class MainActivity extends FragmentActivity implements
		ToolsFragment.OnItemSelectedListener {

	public static final String INDEX = "mIndex";

	private boolean mDualPane;
	private int mIndex = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools);
		// setContentView(R.layout.braille2text);
		mDualPane = (findViewById(R.id.tool_frame) != null);

		if (savedInstanceState != null) {
			mIndex = savedInstanceState.getInt(INDEX, -1);
		}

		if (mDualPane && (mIndex > -1)) {
			onItemSelected(mIndex);
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
}
