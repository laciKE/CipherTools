package com.lacike.ciphertools;

import android.content.Intent;
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

		Intent intent = getIntent();
		String label = intent.getStringExtra(LABEL);
		setTitle(label);
		int index = intent.getIntExtra(INDEX, 0);
		Fragment fragment = ToolFragmentFactory.newToolFragment(index);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.tool_frame, fragment).commit();
	}
}
