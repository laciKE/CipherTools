package com.lacike.ciphertools;

/**
 * Creates fragment Rot13 substitution.
 */
public class Rot13Fragment extends SubstitutionFragment {

	public static Rot13Fragment newInstance() {
		return new Rot13Fragment();
	}

	/**
	 * Substitutes letter using Rot13. Each letter rotetes by 13 places.
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
