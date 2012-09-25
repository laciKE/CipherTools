package com.lacike.ciphertools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creates fragment for Colors
 */
public class ColorsFragment extends BaseFragment {

	public static ColorsFragment newInstance() {
		return new ColorsFragment();
	}
	
	/**
	 * Returns view for {@link ColorsFragment}
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_colors);
		
		View view = inflater.inflate(R.layout.colors, container, false);

		return view;
	}
}
