package com.lacike.ciphertools;

import android.support.v4.app.Fragment;

public class ToolFragmentFactory {
	
	private static String[] toolFragments = new String[] {
			"com.lacike.ciphertools.Braille2TextFragment",
			"Braille2TextFragment"
	};
	
	public static Fragment newToolFragment(int index) {
		Fragment toolFragment = null;
		try {
			toolFragment = (Fragment) Class.forName(toolFragments[index]).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toolFragment;
	}
}
