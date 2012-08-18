package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Text2MorseFragment extends Fragment {

	public static Text2MorseFragment newInstance() {
		return new Text2MorseFragment();
	}

	public static String[] morseCode = new String[] { ".-", "-...", "-.-.",
			"-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
			"--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-",
			"...-", ".--", "-..-", "-.--", "--.." };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.text2morse, container, false);

		View morseInput;
		morseInput = view.findViewById(R.id.text2morse_submit);
		morseInput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				text2morse(v);
			}
		});

		morseInput = view.findViewById(R.id.text2morse_clear);
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

	protected void text2morse(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.text2morse_input);
		String inputText = input.getText().toString();
		inputText = inputText.toUpperCase();
		inputText = inputText.replaceAll("[^A-Z \n]", "");

		StringBuffer outputText = new StringBuffer();
		for (int i = 0; i < inputText.length(); i++) {
			if (inputText.charAt(i) == ' '){
				outputText.append('/');
			} else if (inputText.charAt(i) == '\n') {
				outputText.append('\n');
			} else {
				int charCode = inputText.charAt(i)-'A';
				outputText.append(morseCode[charCode]+'/');
			}
		}

		EditText output = (EditText) rootView
				.findViewById(R.id.text2morse_output);
		output.setText(outputText);
	}

	protected void clear(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.text2morse_input);
		input.setText("");

		EditText output = (EditText) rootView
				.findViewById(R.id.text2morse_output);
		output.setText("");
	}
}