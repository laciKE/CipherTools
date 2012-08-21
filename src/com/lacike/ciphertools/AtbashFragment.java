package com.lacike.ciphertools;

/**
 * Creates fragment for Atbash substitution.
 */
public class AtbashFragment extends SubstitutionFragment {

	public static AtbashFragment newInstance() {
		return new AtbashFragment();
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
