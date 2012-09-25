package com.lacike.ciphertools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creates fragment Rot13 substitution.
 */
public class Rot13Fragment extends SubstitutionFragment {

	public static Rot13Fragment newInstance() {
		return new Rot13Fragment();
	}

	/**
	 * Returns view for {@link Rot13Fragment} and sets help message.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_rot13);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * Substitutes letter using Rot13. Each letter rotates by 13 places.
	 */
	@Override
	protected char substituteLetter(char c) {
		if (Character.isLowerCase(c)) {
			return (char) (((c - 'a') + 13) % 26 + 'a');
		} else {
			return (char) (((c - 'A') + 13) % 26 + 'A');
		}
	}

}
