package com.lacike.ciphertools.util;

import android.os.Bundle;

/**
 * Shares bundle between activities and fragments. Implementation by Singleton
 * design pattern with eager initialization.
 */
public class SharedBundle {

	private static final Bundle bundle = new Bundle();

	private SharedBundle() {
	}

	public static Bundle getInstance() {
		return bundle;
	}

}
