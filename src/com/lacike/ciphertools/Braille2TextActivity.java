package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Braille2TextActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_activity);

		Braille2TextFragment fragment = Braille2TextFragment.newInstance();

		getSupportFragmentManager().beginTransaction()
				.add(R.id.tool_frame, fragment).commit();
	}
}
