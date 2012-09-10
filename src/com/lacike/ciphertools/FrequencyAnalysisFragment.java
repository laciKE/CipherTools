package com.lacike.ciphertools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.lacike.util.Pair;
import com.lacike.util.SharedBundle;
import com.lacike.util.StringNormalizer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Creates fragment for frequency analysis.
 */
public class FrequencyAnalysisFragment extends Fragment {

	public static FrequencyAnalysisFragment newInstance() {
		return new FrequencyAnalysisFragment();
	}

	/**
	 * Returns view for {@link FrequencyAnalysisFragment} and sets
	 * {@link OnClickListener} for each {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frequency_analysis, container,
				false);

		View frequencyButton;
		frequencyButton = view.findViewById(R.id.frequency_clear);
		frequencyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clear(v);
			}
		});

		frequencyButton = view.findViewById(R.id.frequency_submit);
		frequencyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				analyze(v);
			}
		});
		
		Bundle sharedBundle = SharedBundle.getInstance();
		String message = sharedBundle.getString(ToolActivity.MESSAGE);
		if (message != null) {
			EditText frequencyInput = (EditText) view.findViewById(R.id.frequency_input);
			frequencyInput.setText(message);
		}

		return view;
	}
	
	/**
	 * Saves current regExp to SharedBundle.
	 */
	@Override
	public void onDestroyView() {
		Bundle sharedBundle = SharedBundle.getInstance();
		EditText frequencyInput = (EditText) getActivity().findViewById(
				R.id.frequency_input);
		sharedBundle.putString(ToolActivity.MESSAGE, frequencyInput.getText().toString());

		super.onDestroyView();
	}

	/**
	 * Analyzes text from input and displays result to output.
	 */
	protected void analyze(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView.findViewById(R.id.frequency_input);
		String text = StringNormalizer.toUpperLetters(input.getText()
				.toString());

		LinearLayout output = (LinearLayout) rootView
				.findViewById(R.id.frequency_output);

		ArrayList<Pair<String, Integer>> frequency = null;
		RadioGroup mode = (RadioGroup) rootView
				.findViewById(R.id.frequency_mode);
		
		int ngram = 1;
		switch (mode.getCheckedRadioButtonId()) {
		case R.id.frequency_letters:
			ngram = 1;
			break;
		case R.id.frequency_bigrams:
			ngram = 2;
			break;
		case R.id.frequency_trigrams:
			ngram = 3;
			break;
		}

		frequency = ngrams(text, ngram);
		
		if (frequency == null) {
			clear(view);
			Toast.makeText(
					this.getActivity(),
					getResources().getQuantityString(
							R.plurals.invalid_frequency_input, ngram, ngram),
					Toast.LENGTH_SHORT).show();
			input.requestFocus();
		} else {
			formatOutput(frequency, output);
		}
	}

	/**
	 * Formates results to table.
	 */
	protected void formatOutput(ArrayList<Pair<String, Integer>> frequency,
			LinearLayout output) {
		int numberOfColumns = 2;
		LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		LayoutParams cellParams = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1);
		LayoutParams verticalDividerParams = new LayoutParams(1,
				LayoutParams.MATCH_PARENT);
		LayoutParams horizontalDividerParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 1);

		output.removeAllViews();
		Context context = this.getActivity();

		TextView divider = new TextView(context);
		divider.setLayoutParams(horizontalDividerParams);
		divider.setBackgroundColor(Color.DKGRAY);
		output.addView(divider);

		for (int i = 0; i < frequency.size() % numberOfColumns; i++) {
			frequency.add(new Pair<String, Integer>("", 0));
		}

		int numberOfRows = frequency.size() / numberOfColumns;

		for (int i = 0; i < numberOfRows; i++) {
			LinearLayout row = new LinearLayout(context);
			row.setLayoutParams(rowParams);
			row.setOrientation(LinearLayout.HORIZONTAL);

			divider = new TextView(context);
			divider.setLayoutParams(verticalDividerParams);
			divider.setBackgroundColor(Color.DKGRAY);
			row.addView(divider);
			for (int w = 0; w < numberOfColumns; w++) {
				TextView cell = new TextView(context);
				cell.setLayoutParams(cellParams);
				cell.setGravity(Gravity.RIGHT);
				cell.setTypeface(Typeface.MONOSPACE);
				cell.setTextAppearance(context,
						android.R.style.TextAppearance_Large);
				cell.setText(frequency.get(w * numberOfRows + i).first + " ");
				row.addView(cell);

				cell = new TextView(context);
				cell.setLayoutParams(cellParams);
				cell.setGravity(Gravity.LEFT);
				cell.setTypeface(Typeface.MONOSPACE);
				cell.setTextAppearance(context,
						android.R.style.TextAppearance_Large);
				Integer count = frequency.get(w * numberOfRows + i).second;
				if (count > 0) {
					cell.setText(String.valueOf(count.intValue()));
				}
				row.addView(cell);

				divider = new TextView(context);
				divider.setLayoutParams(verticalDividerParams);
				divider.setBackgroundColor(Color.DKGRAY);
				row.addView(divider);
			}

			output.addView(row);

			divider = new TextView(context);
			divider.setLayoutParams(horizontalDividerParams);
			divider.setBackgroundColor(Color.DKGRAY);
			output.addView(divider);
		}
	}

	/**
	 * Analyzes frequency of ngrams in given String.
	 */
	public static ArrayList<Pair<String, Integer>> ngrams(String text, int n) {
		if (text.length() < n) {
			return null;
		}

		Map<String, Integer> frequency = new HashMap<String, Integer>();
		StringBuilder key = new StringBuilder(text.substring(0, n));
		String keyString = key.toString();
		frequency.put(keyString, 1);

		for (int i = n; i < text.length(); i++) {
			key.deleteCharAt(0);
			key.append(text.charAt(i));
			keyString = key.toString();
			Integer value = frequency.get(keyString);
			if (value != null) {
				frequency.put(keyString, value + 1);
			} else {
				frequency.put(keyString, 1);
			}
		}

		ArrayList<Pair<String, Integer>> result = new ArrayList<Pair<String, Integer>>();

		Set<String> keys = frequency.keySet();
		for (String string : keys) {
			Pair<String, Integer> pair = new Pair<String, Integer>(string,
					frequency.get(string));
			result.add(pair);
		}

		Collections.sort(result, new CustomComparator());

		return result;
	}

	/**
	 * Clears input {@link EditText} and output {@link LinearLayout}.
	 */
	protected void clear(View view) {
		View rootView = view.getRootView();
		EditText input = (EditText) rootView.findViewById(R.id.frequency_input);
		input.setText("");

		LinearLayout output = (LinearLayout) rootView
				.findViewById(R.id.frequency_output);
		output.removeAllViews();
	}

}

/**
 * Compares Pair<String, Integer> by Integer field.
 */
class CustomComparator implements Comparator<Pair<String, Integer>> {
	@Override
	public int compare(Pair<String, Integer> lhs, Pair<String, Integer> rhs) {
		return rhs.second.compareTo(lhs.second);
	}
}
