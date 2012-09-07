package com.lacike.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Normalizes strings (converts to upperCase, removes accents).
 */
public class StringNormalizer {

	/**
	 * Map for removing accents.
	 */
	protected static Map<Character, Character> sNormalizationMap = new HashMap<Character, Character>() {
		private static final long serialVersionUID = 1L;
		{
			put('Á', 'A');
			put('Ä', 'A');
			put('Č', 'C');
			put('Ć', 'C');
			put('Ď', 'D');
			put('É', 'E');
			put('Ě', 'E');
			put('Í', 'I');
			put('Ĺ', 'L');
			put('Ľ', 'L');
			put('Ň', 'N');
			put('Ó', 'O');
			put('Ô', 'O');
			put('Ö', 'O');
			put('Ő', 'O');
			put('Ŕ', 'R');
			put('Ř', 'R');
			put('Š', 'S');
			put('Ť', 'T');
			put('Ú', 'U');
			put('Ů', 'U');
			put('Ű', 'U');
			put('Ý', 'Y');
			put('Ž', 'Z');
		}
	};

	/**
	 * Removes accents from given string. Currently supports only Czech, Slovak
	 * and some other Central European uppercase characters.
	 */
	public static String normalize(String string) {
		StringBuilder result = new StringBuilder(string);
		for (int i = 0; i < string.length(); i++) {
			Character c = sNormalizationMap.get(result.charAt(i));
			if (c != null) {
				result.setCharAt(i, c);
			}
		}

		return result.toString();
	}

	/**
	 * Returns string transformed to upper case string containing only letters
	 * ('A'-'Z').
	 */
	public static String toUpperLetters(String str) {
		return str.toUpperCase().replaceAll("[^A-Z]", "");
	}
	
	/**
	 * Checks if string contains numbers.
	 */
	public static boolean containsNumbers(String str) {
		return str.matches(".*[0-9].*");
	}
}
