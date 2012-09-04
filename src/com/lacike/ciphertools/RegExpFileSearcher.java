package com.lacike.ciphertools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Filters lines from file that matches given regular expression.
 */
public class RegExpFileSearcher implements Runnable {

	private Context mContext;
	private Resources mResources;
	private Handler mHandler;
	private ProgressBar mProgressBar;
	private TextView mProgressCount;
	private ListView mOutputList;
	private String mRegExp;
	private InputStream mInputStream;
	private String mFileName;

	public RegExpFileSearcher(Context context, Handler handler,
			ProgressBar progressBar, TextView progressCount,
			ListView outputList, String regExp, InputStream inputStream) {
		mContext = context;
		mResources = mContext.getResources();
		mHandler = handler;
		mProgressBar = progressBar;
		mProgressCount = progressCount;
		mOutputList = outputList;
		mRegExp = StringNormalizer.normalize(regExp);
		mInputStream = inputStream;
	}

	/**
	 * Filters lines from mInputStream that matches mRegExp, outputs result in
	 * mOutputList. During search updates mProgressBar and mProgressCount (text
	 * info for progressBar). If error eccured, displays it using {@link Toast}.
	 */
	@Override
	public void run() {
		ArrayList<String> results = new ArrayList<String>();
		try {
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(mInputStream));
			String line;
			int lineCount = Integer.parseInt(inputStream.readLine());
			int lineNumber = 0;
			mHandler.post(new ProgressDisplayer(lineNumber, lineCount, true));

			while ((line = inputStream.readLine()) != null) {
				lineNumber++;

				String normalizedLine = StringNormalizer.normalize(line);
				try {
					if (normalizedLine.matches(mRegExp)) {
						results.add(line);
					}
				} catch (PatternSyntaxException e) {
					throw e;
				}

				if (lineNumber % 100 == 0) {
					mHandler.post(new ProgressDisplayer(lineNumber, lineCount,
							false));
				}
			}

			inputStream.close();
		} catch (IOException e) {
			mHandler.post(new ErrorDisplayer(mResources.getString(
					R.string.file_error, mFileName)));
		} catch (PatternSyntaxException e) {
			final String message = e.getLocalizedMessage();
			mHandler.post(new ErrorDisplayer(mResources.getString(
					R.string.pattern_syntax_error, message)));
		} finally {
			/*
			 * Hides progressBar and progressCount and shows results in output
			 * list.
			 */
			final String[] items = new String[results.size()];
			for (int i = 0; i < results.size(); i++) {
				items[i] = results.get(i);
			}

			mHandler.post(new Runnable() {
				@Override
				public void run() {
					mProgressBar.setVisibility(ProgressBar.GONE);
					mProgressCount.setVisibility(TextView.GONE);
					mOutputList.setAdapter(new ArrayAdapter<String>(mContext,
							android.R.layout.simple_list_item_1, items));
				}
			});
		}
	}

	/**
	 * Updates mProgressBar and mProgressCount.
	 */
	protected class ProgressDisplayer implements Runnable {

		protected int mProgress;
		protected int mMax;
		protected boolean mInitialize;

		public ProgressDisplayer(int progress, int max, boolean initialize) {
			mProgress = progress;
			mMax = max;
			mInitialize = initialize;
		}

		@Override
		public void run() {
			if (mInitialize) {
				mProgressBar.setVisibility(ProgressBar.VISIBLE);
				mProgressCount.setVisibility(TextView.VISIBLE);
				mProgressBar.setMax(mMax);
			}
			mProgressBar.setProgress(mProgress);
			mProgressCount.setText(mProgress + "/" + mMax);
		}
	}

	/**
	 * Shows Toast with error message.
	 */
	protected class ErrorDisplayer implements Runnable {

		protected String mMessage;

		public ErrorDisplayer(String message) {
			mMessage = message;
		}

		@Override
		public void run() {
			Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
		}
	}
}
