package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Morse2TextFragment extends Fragment {

	public static Morse2TextFragment newInstance() {
		return new Morse2TextFragment();
	}

	private static int[] morseInputs = new int[] { R.id.morse_dot,
			R.id.morse_dash, R.id.morse_separator };

	private static String morseTree = "  ETIANMSURWDKGOHVF?L?PJBXCYZQ??";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.morse2text, container, false);

		View morseInput;
		for (int id : morseInputs) {
			morseInput = view.findViewById(id);
			morseInput.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					morse_input(v);
				}
			});
		}

		morseInput = view.findViewById(R.id.morse2text_submit);
		morseInput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				morse2text(v);
			}
		});

		morseInput = view.findViewById(R.id.morse_backspace);
		morseInput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backspace(v);
			}
		});

		morseInput = view.findViewById(R.id.morse_clear);
		morseInput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clear(v);
			}
		});

		// view.findViewById(R.id.morse2text_input).setEnabled(false);
		// view.findViewById(R.id.morse2text_output).setEnabled(false);

		return view;
	}

	protected void morse_input(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		String inputText = input.getText().toString()
				+ ((Button) view).getText().toString();
		input.setText(inputText);
	}

	protected void morse2text(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		String inputText = input.getText().toString() + " ";

		StringBuffer outputText = new StringBuffer();
		int position = 1;
		for (int i = 0; i < inputText.length(); i++) {
			if (inputText.charAt(i) == '.') {
				position *= 2;
			} else if (inputText.charAt(i) == '-') {
				position = position * 2 + 1;
			} else if (inputText.charAt(i) == '\n') {
				outputText.append('\n');
				position = 1;
			} else if (position < morseTree.length()) {
				outputText.append(morseTree.charAt(position));
				position = 1;
			} else {
				outputText.append(' ');
				position = 1;			
			}
		}

		EditText output = (EditText) rootView
				.findViewById(R.id.morse2text_output);
		output.setText(outputText);
	}

	protected void backspace(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		String inputText = input.getText().toString();
		if (inputText.length() > 0) {
			input.setText(inputText.subSequence(0, inputText.length() - 1));
		}
	}

	protected void clear(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		input.setText("");
		
		EditText output = (EditText) rootView
				.findViewById(R.id.morse2text_output);
		output.setText("");
	}
}
