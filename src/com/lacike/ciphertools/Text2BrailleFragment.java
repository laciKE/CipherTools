package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Text2BrailleFragment extends Fragment {
	
	public static Text2BrailleFragment newInstance() {
		return new Text2BrailleFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.text2braille, container, false);

		return view;
	}
}
