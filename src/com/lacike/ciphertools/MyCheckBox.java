package com.lacike.ciphertools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * Provides CheckBox with {@link RadioButton} lookout.
 */
public class MyCheckBox extends RadioButton {

	public MyCheckBox(Context context) {
		super(context);
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Toggles state like a {@link CheckBox}. (From false to true and from true
	 * to false)
	 */
	@Override
	public void toggle() {
		setChecked(!isChecked());
	}
}
