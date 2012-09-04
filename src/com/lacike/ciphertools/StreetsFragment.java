package com.lacike.ciphertools;

import java.io.InputStream;

import com.lacike.util.RegExpFileSearcher;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * Creates fragment for Streets.
 */
public class StreetsFragment extends Fragment {

	public static StreetsFragment newInstance() {
		return new StreetsFragment();
	}

	
	/**
	 * Ids of files with streets.
	 */
	protected static int[] sCitiesId = new int[] { R.raw.bratislava,
			R.raw.brno, R.raw.kosice, R.raw.ostrava, R.raw.praha, R.raw.zlin };

	private Handler mHandler = new Handler();

	/**
	 * Returns view for {@link StreetsFragment} and sets {@link OnClickListener}
	 * for submit {@link Button}.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.streets, container, false);

		View input;
		input = view.findViewById(R.id.streets_submit);
		input.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchStreets(v);
			}
		});

		return view;
	}

	/**
	 * Displays streets matching given pattern using {@link RegExpFileSearcher}.
	 */
	protected void searchStreets(View view) {
		View rootView = view.getRootView();
		Log.d("STREETS", "click on search button");

		ProgressBar progressBar = (ProgressBar) rootView
				.findViewById(R.id.streets_progress_bar);
		
		if (progressBar.getVisibility() == ProgressBar.VISIBLE) {
			return;
		}

		EditText input = (EditText) rootView.findViewById(R.id.streets_input);
		String regExp = input.getText().toString().toUpperCase() + ".*";

		Spinner cities = (Spinner) rootView.findViewById(R.id.streets_city);
		int selectedCity = cities.getSelectedItemPosition();
		InputStream inputStream = getResources().openRawResource(
				sCitiesId[selectedCity]);

		RegExpFileSearcher searcher = new RegExpFileSearcher(
				this.getActivity(), mHandler, progressBar,
				(TextView) rootView.findViewById(R.id.streets_progress_count),
				(ListView) rootView.findViewById(R.id.streets_output), regExp,
				inputStream);

		new Thread(searcher).start();
	}
}
