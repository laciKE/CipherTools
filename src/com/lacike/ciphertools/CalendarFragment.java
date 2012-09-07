package com.lacike.ciphertools;

import java.io.InputStream;

import com.lacike.util.RegExpFileSearcher;
import com.lacike.util.StringNormalizer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Creates fragment for Calendar.
 */
public class CalendarFragment extends Fragment {

	public static CalendarFragment newInstance() {
		return new CalendarFragment();
	}

	/**
	 * Ids of files with calendars.
	 */
	protected static int[] sCalendarsId = new int[] { R.raw.calendar_cs,
			R.raw.calendar_sk };

	private Handler mHandler = new Handler();

	/**
	 * Returns view for {@link CalendarFragment} and sets
	 * {@link OnClickListener} for submit {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.calendar, container, false);

		View input;
		input = view.findViewById(R.id.calendar_submit);
		input.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchNames(v);
			}
		});

		return view;
	}

	/**
	 * Displays names matching given pattern using {@link RegExpFileSearcher}.
	 */
	protected void searchNames(View view) {
		View rootView = view.getRootView();

		ProgressBar progressBar = (ProgressBar) rootView
				.findViewById(R.id.calendar_progress_bar);

		if (progressBar.getVisibility() == ProgressBar.VISIBLE) {
			return;
		}

		EditText input = (EditText) rootView.findViewById(R.id.calendar_input);
		String inputText = input.getText().toString().toUpperCase();
		
		String regExp;
		/*
		 * if input contains number, maybe search by date, otherwise by name 
		 */
		if(StringNormalizer.containsNumbers(inputText)) {
			regExp = inputText + ".*";
		} else {
			regExp = ".* " + inputText + ".*";
		}
		
		Spinner calendars = (Spinner) rootView.findViewById(R.id.calendar_country);
		int selectedCalendar = calendars.getSelectedItemPosition();
		InputStream inputStream = getResources().openRawResource(
				sCalendarsId[selectedCalendar]);

		RegExpFileSearcher searcher = new RegExpFileSearcher(
				this.getActivity(), mHandler, progressBar,
				(TextView) rootView.findViewById(R.id.calendar_progress_count),
				(ListView) rootView.findViewById(R.id.calendar_output), regExp,
				inputStream);

		new Thread(searcher).start();
	}
}
