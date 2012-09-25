package com.lacike.ciphertools;

import com.lacike.ciphertools.util.SharedBundle;
import com.lacike.ciphertools.util.StringNormalizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Creates fragment for Vigenere cipher
 */
public class VigenereFragment extends BaseFragment {

	public static VigenereFragment newInstance() {
		return new VigenereFragment();
	}

	/**
	 * Returns view for {@link VigenereFragment} and sets
	 * {@link OnClickListener} for each {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHelpMessage(R.string.help_vigenere);
		
		View view = inflater.inflate(R.layout.vigenere, container, false);

		View vigenereButton;
		vigenereButton = view.findViewById(R.id.vigenere_clear);
		vigenereButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clear(v);
			}
		});

		vigenereButton = view.findViewById(R.id.vigenere_submit);
		vigenereButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vigenere(v);
			}
		});
		
		Bundle sharedBundle = SharedBundle.getInstance();
		String message = sharedBundle.getString(ToolActivity.MESSAGE);
		if (message != null) {
			EditText vigenereInput = (EditText) view.findViewById(R.id.vigenere_input);
			vigenereInput.setText(message);
		}

		return view;
	}
	
	/**
	 * Saves current regExp to SharedBundle.
	 */
	@Override
	public void onDestroyView() {
		Bundle sharedBundle = SharedBundle.getInstance();
		EditText vigenereInput = (EditText) getView().findViewById(
				R.id.vigenere_input);
		sharedBundle.putString(ToolActivity.MESSAGE, vigenereInput.getText().toString());

		super.onDestroyView();
	}

	/**
	 * Reads and validate input (key and message). Calls decode() or encode().
	 */
	protected void vigenere(View view) {
		View rootView = view.getRootView();
		EditText keyInput = (EditText) rootView.findViewById(R.id.vigenere_key);
		String key = StringNormalizer.toUpperLetters(keyInput.getText()
				.toString());

		if ((key == null) || (key.length() == 0)) {
			Toast.makeText(getActivity(), R.string.invalid_vigenere_key,
					Toast.LENGTH_SHORT).show();
			keyInput.requestFocus();
			return;
		}

		EditText messageInput = (EditText) rootView
				.findViewById(R.id.vigenere_input);
		String message = StringNormalizer.toUpperLetters(messageInput.getText()
				.toString());

		// A==1?
		int a = 0;
		RadioGroup alphabet = (RadioGroup) rootView
				.findViewById(R.id.vigenere_alphabet);
		if (alphabet.getCheckedRadioButtonId() == R.id.vigenere_a1) {
			a = 1;
		}
		RadioGroup operation = (RadioGroup) rootView
				.findViewById(R.id.vigenere_operation);

		String outputMessage;
		if (operation.getCheckedRadioButtonId() == R.id.vigenere_encode) {
			outputMessage = encode(key, message, a);
		} else {
			outputMessage = decode(key, message, a);
		}

		EditText output = (EditText) rootView
				.findViewById(R.id.vigenere_output);
		output.setText(outputMessage);

	}

	/**
	 * Encodes message with key in alphabet with 'A' char has value alphabet.
	 */
	public String encode(String key, String message, int alphabet) {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < message.length(); i++) {
			char outputChar = (char) ((26 - alphabet
					- key.charAt(i % key.length()) + message.charAt(i)) % 26 + 'A');
			output.append(outputChar);
		}

		return output.toString();
	}

	/**
	 * Decodes message with key in alphabet with 'A' char has value alphabet.
	 */
	public String decode(String key, String message, int alphabet) {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < message.length(); i++) {
			char outputChar = (char) ((key.charAt(i % key.length())
					+ message.charAt(i) + alphabet) % 26 + 'A');
			output.append(outputChar);
		}

		return output.toString();
	}

	/**
	 * Clears input and output {@link EditText}.
	 */
	protected void clear(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView.findViewById(R.id.vigenere_input);
		input.setText("");

		EditText output = (EditText) rootView
				.findViewById(R.id.vigenere_output);
		output.setText("");
	}
}
