package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class Braille2TextFragment extends Fragment {

	public static Braille2TextFragment newInstance() {
		return new Braille2TextFragment();
	}

	private static int[] brailleInputs = new int[] { R.id.braille1,
			R.id.braille2, R.id.braille4, R.id.braille8, R.id.braille16,
			R.id.braille32 };

	private static String[] braille = new String[] { "", "A", "", "C", "", "B",
			"I", "F", "", "E", "", "D", "", "H", "J", "G", "", "K", "", "M",
			"", "L", "S", "P", "", "O", "", "N", "", "R", "T", "Q", "", "",
			"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "U",
			"", "X", "", "V", "", "", "", "Z", "", "Y", "", "W", "", "" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.braille2text, container, false);

		View brailleInput;
		for (int id : brailleInputs) {
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

	protected void braille2text(View view) {
		View rootView = view.getRootView();
		int brailleValue = 0;
		int value = 1;
		for (int id : brailleInputs) {
			CheckBox brailleInput = (CheckBox) rootView.findViewById(id);
			if (brailleInput.isChecked()) {
				brailleValue += value;
			}
			value *= 2;
		}

		TextView brailleOutput = (TextView) rootView
				.findViewById(R.id.braille2text_output);
		brailleOutput.setText(braille[brailleValue]);
	}
}
