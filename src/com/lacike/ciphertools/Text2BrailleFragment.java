package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creates fragment for Text2Braille conversion (currently image with letters and Braille)
 */
public class Text2BrailleFragment extends Fragment {
	
	public static Text2BrailleFragment newInstance() {
		return new Text2BrailleFragment();
	}
	
	/**
	 * Returns view for {@link Text2BrailleFragment}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.text2braille, container, false);

		return view;
	}
}
