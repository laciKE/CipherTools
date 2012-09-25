package com.lacike.ciphertools;

import com.lacike.ciphertools.util.SharedBundle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Creates fragment for Text2Morse conversion.
 */
public class Text2MorseFragment extends BaseFragment {

	public static Text2MorseFragment newInstance() {
		return new Text2MorseFragment();
	}

	/**
	 * Morse codes for letters.
	 */
	public static String[] sMorseCode = new String[] { ".-", "-...", "-.-.",
			"-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
			"--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-",
			"...-", ".--", "-..-", "-.--", "--.." };

	/**
	 * Returns view for {@link Text2MorseFragment} and sets
	 * {@link OnClickListener} for each {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_text2morse);
		
		View view = inflater.inflate(R.layout.text2morse, container, false);

		View morseButton;
		morseButton = view.findViewById(R.id.text2morse_submit);
		morseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				text2morse(v);
			}
		});

		morseButton = view.findViewById(R.id.text2morse_clear);
		morseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clear(v);
			}
		});
	
		Bundle sharedBundle = SharedBundle.getInstance();
		String message = sharedBundle.getString(ToolActivity.MESSAGE);
		if (message != null) {
			EditText morseInput = (EditText) view.findViewById(R.id.text2morse_input);
			morseInput.setText(message);
		}

		return view;
	}
	
	/**
	 * Saves current regExp to SharedBundle.
	 */
	@Override
	public void onDestroyView() {
		Bundle sharedBundle = SharedBundle.getInstance();
		EditText morseInput = (EditText) getView().findViewById(
				R.id.text2morse_input);
		sharedBundle.putString(ToolActivity.MESSAGE, morseInput.getText().toString());

		super.onDestroyView();
	}

	/**
	 * Converts text to Morse code.
	 */
	protected void text2morse(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.text2morse_input);
		String inputText = input.getText().toString();
		inputText = inputText.toUpperCase();
		inputText = inputText.replaceAll("[^A-Z \n]", "");

		StringBuffer outputText = new StringBuffer();
		for (int i = 0; i < inputText.length(); i++) {
			if (inputText.charAt(i) == ' ') {
				outputText.append('/');
			} else if (inputText.charAt(i) == '\n') {
				outputText.append('\n');
			} else {
				int charCode = inputText.charAt(i) - 'A';
				outputText.append(sMorseCode[charCode] + '/');
			}
		}

		EditText output = (EditText) rootView
				.findViewById(R.id.text2morse_output);
		output.setText(outputText);
	}

	/**
	 * Clears input and output {@link EditText}.
	 */
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