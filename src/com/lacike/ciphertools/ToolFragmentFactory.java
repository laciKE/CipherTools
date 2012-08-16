package com.lacike.ciphertools;

import android.support.v4.app.Fragment;

public class ToolFragmentFactory {
	
	private static String[] toolFragments = new String[] {
			"com.lacike.ciphertools.Braille2TextFragment",
			"com.lacike.ciphertools.Text2BrailleFragment",
			"com.lacike.ciphertools.Morse2TextFragment",
			"com.lacike.ciphertools.Text2MorseFragment",
	};
	
	public static Fragment newToolFragment(int index) {
		Fragment toolFragment = null;
		try {
			toolFragment = (Fragment) Class.forName(toolFragments[index]).newInstance();
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
