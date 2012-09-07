package com.lacike.ciphertools;

import android.support.v4.app.Fragment;

/**
 * Creates ToolFragment for concrete tool. Used in ToolActivity and
 * MainActivity.
 */
public class ToolFragmentFactory {

	private static String[] sToolFragments = new String[] {
			"com.lacike.ciphertools.Braille2TextFragment",
			"com.lacike.ciphertools.Text2BrailleFragment",
			"com.lacike.ciphertools.Morse2TextFragment",
			"com.lacike.ciphertools.Text2MorseFragment",
			"com.lacike.ciphertools.VigenereFragment",
			"com.lacike.ciphertools.Rot13Fragment",
			"com.lacike.ciphertools.AtbashFragment",
			"com.lacike.ciphertools.SemaphoreFragment",
			"com.lacike.ciphertools.FlagsMeaningFragment",
			"com.lacike.ciphertools.ColorsFragment",
			"com.lacike.ciphertools.AlphabetFragment",
			"com.lacike.ciphertools.FrequencyAnalysisFragment",
			"com.lacike.ciphertools.CalendarFragment",
			"com.lacike.ciphertools.StreetsFragment"
		};

	/**
	 * Returns new ToolFragment for concrete tool.
	 */
	public static Fragment newToolFragment(int index) {
		Fragment toolFragment = null;
		try {
			toolFragment = (Fragment) Class.forName(sToolFragments[index])
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return toolFragment;
	}
}
