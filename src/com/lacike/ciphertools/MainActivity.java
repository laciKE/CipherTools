package com.lacike.ciphertools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements
		ToolsFragment.OnItemSelectedListener {

	private boolean dualPane;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools);
		// setContentView(R.layout.braille2text);
		dualPane = (findViewById(R.id.tool_frame) != null);
	}

	@Override
	public void onItemSelected(int index) {
		if (dualPane) {
			Fragment fragment = ToolFragmentFactory.newToolFragment(index);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.tool_frame, fragment);
			fragmentTransaction.addToBackStack(null);
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
