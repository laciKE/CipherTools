package com.lacike.ciphertools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Creates fragment for Morse2Text conversion
 */
public class Morse2TextFragment extends BaseFragment {

	public static Morse2TextFragment newInstance() {
		return new Morse2TextFragment();
	}

	private static int[] sMorseInputs = new int[] { R.id.morse_dot,
			R.id.morse_dash, R.id.morse_separator };

	/**
	 * MorseTree for effective conversion from morse to letter. For more info
	 * see morse2text().
	 */
	private static String sMorseTree = "  ETIANMSURWDKGOHVF?L?PJBXCYZQ??";

	/**
	 * Returns view for {@link Morse2TextFragment} and sets
	 * {@link OnClickListener} for each {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_morse2text);
		
		View view = inflater.inflate(R.layout.morse2text, container, false);

		View morseInput;
		for (int id : sMorseInputs) {
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

	/**
	 * Adds char in Morse code input on keypress on Morse keyboard.
	 */
	protected void morse_input(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		String inputText = input.getText().toString()
				+ ((Button) view).getText().toString();
		input.setText(inputText);
	}

	/**
	 * Converts Morse code to text. For each Morse code do this:
	 * Start in root in morseTree, on dot go to the left subtree, on dash go to
	 * the right subtree. Result letter is in the node. On invalid MorseCode
	 * outputs '?', on newline outputs newline, on other chars outputs space.
	 */
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
			} else if (position < sMorseTree.length()) {
				outputText.append(sMorseTree.charAt(position));
				position = 1;
			} else if (position >= sMorseTree.length()) {
				outputText.append('?');
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

	/**
	 * Deletes last char in Morse code input.
	 */
	protected void backspace(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.morse2text_input);
		String inputText = input.getText().toString();
		if (inputText.length() > 0) {
			input.setText(inputText.subSequence(0, inputText.length() - 1));
		}
	}

	/**
	 * Clears input and output {@link EditText}.
	 */
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
