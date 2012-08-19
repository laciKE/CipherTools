package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Creates fragment for Braille2Text conversion
 */
public class Braille2TextFragment extends Fragment {

	public static Braille2TextFragment newInstance() {
		return new Braille2TextFragment();
	}

	private static int[] sBrailleInputs = new int[] { R.id.braille1,
			R.id.braille2, R.id.braille4, R.id.braille8, R.id.braille16,
			R.id.braille32 };

	/**
	 * Data for conversion from Braille to letter. For more info see
	 * braille2text().
	 */
	private static String[] sBraille = new String[] { "", "A", "", "C", "",
			"B", "I", "F", "", "E", "", "D", "", "H", "J", "G", "", "K", "",
			"M", "", "L", "S", "P", "", "O", "", "N", "", "R", "T", "Q", "",
			"", "", "", "", "", "", "", "", "", "", "", "", "", "W", "", "",
			"U", "", "X", "", "V", "", "", "", "Z", "", "Y", "", "W", "", "" };

	/**
	 * Returns view for {@link Braille2TextFragment} and sets
	 * {@link OnClickListener} for each input
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.braille2text, container, false);

		View brailleInput;
		for (int id : sBrailleInputs) {
			brailleInput = view.findViewById(id);
			brailleInput.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					braille2text(v);
				}
			});
		}

		return view;
	}

	/**
	 * Converts Braille symbol from checked views to letter. To each dot in
	 * Braille letter is assigned the power of two (1,2,4,8,16,32). This method
	 * sums the numbers of checked dots and on this position in sBraille is
	 * result.
	 */
	protected void braille2text(View view) {
		View rootView = view.getRootView();
		int brailleValue = 0;
		int value = 1;
		for (int id : sBrailleInputs) {
			CheckBox brailleInput = (CheckBox) rootView.findViewById(id);
			if (brailleInput.isChecked()) {
				brailleValue += value;
			}
			value *= 2;
		}

		TextView brailleOutput = (TextView) rootView
				.findViewById(R.id.braille2text_output);
		brailleOutput.setText(sBraille[brailleValue]);
	}
}
