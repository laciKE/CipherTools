package com.lacike.ciphertools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creates fragment for Atbash substitution.
 */
public class AtbashFragment extends SubstitutionFragment {

	public static AtbashFragment newInstance() {
		return new AtbashFragment();
	}

	/**
	 * Returns view for {@link AtbashFragment} and sets help message.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_atbash);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * Substitutes letter using Atbash. Letter c replaces with letter 'z'-c,
	 * where 'a'=0.
	 */
	@Override
	protected char substituteLetter(char c) {
		if (Character.isLowerCase(c)) {
			return (char) ('z' - c + 'a');
		} else {
			return (char) ('Z' - c + 'A');
		}
	}

}
