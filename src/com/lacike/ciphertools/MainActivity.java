package com.lacike.ciphertools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements
		ToolsFragment.OnItemSelectedListener {

	private boolean dualPane;
	private Class[] toolActivities = new Class[] { 
			Braille2TextActivity.class 
			};

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
			Fragment fragment = Braille2TextFragment.newInstance();
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.tool_frame, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else {
			Intent intent = new Intent(this, toolActivities[index]);
			startActivity(intent);
		}
	}
}
