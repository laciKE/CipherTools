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
 * Creates fragment for simple substitution (like Rot13, Atbash,...)
 */
public abstract class SubstitutionFragment extends BaseFragment {

	/**
	 * Returns view for {@link SubstitutionFragment} and sets {@link OnClickListener}
	 * for each {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.substitution, container, false);

		View button;
		button = view.findViewById(R.id.substitution_submit);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				substitute(v);
			}
		});

		button = view.findViewById(R.id.substitution_clear);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clear(v);
			}
		});
		
		Bundle sharedBundle = SharedBundle.getInstance();
		String message = sharedBundle.getString(ToolActivity.MESSAGE);
		if (message != null) {
			EditText input = (EditText) view.findViewById(R.id.substitution_input);
			input.setText(message);
		}

		return view;
	}
	
	/**
	 * Saves current regExp to SharedBundle.
	 */
	@Override
	public void onDestroyView() {
		Bundle sharedBundle = SharedBundle.getInstance();
		EditText input = (EditText) getActivity().findViewById(
				R.id.substitution_input);
		sharedBundle.putString(ToolActivity.MESSAGE, input.getText().toString());

		super.onDestroyView();
	}

	/**
	 * Does substitution. Each letter replaces using substititeLetter().
	 */
	protected void substitute(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.substitution_input);
		String inputText = input.getText().toString();

		StringBuffer outputText = new StringBuffer();
		for (int i = 0; i < inputText.length(); i++) {
			if (Character.isLetter(inputText.charAt(i))) {
				outputText.append(substituteLetter(inputText.charAt(i)));
			} else {
				outputText.append(inputText.charAt(i));
			}
		}

		EditText output = (EditText) rootView
				.findViewById(R.id.substitution_output);
		output.setText(outputText);
	}

	/**
	 * Substitutes one letter. By default, returns parameter. Should be
	 * override.
	 */
	protected abstract char substituteLetter(char c);

	/**
	 * Clears input and output {@link EditText}.
	 */
	protected void clear(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView
				.findViewById(R.id.substitution_input);
		input.setText("");

		EditText output = (EditText) rootView
				.findViewById(R.id.substitution_output);
		output.setText("");
	}

}
