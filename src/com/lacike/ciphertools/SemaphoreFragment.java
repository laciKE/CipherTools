package com.lacike.ciphertools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Creates fragment for semaphore alphabet
 */
public class SemaphoreFragment extends Fragment {

	public static SemaphoreFragment newInstance() {
		return new SemaphoreFragment();
	}

	public static final String CHECKED_INPUTS = "checkedInputs";

	/**
	 * Ids of input RadioButtons for Semaphore
	 */
	private static int[] sSemaphoreInputs = new int[] { R.id.semaphore0,
			R.id.semaphore1, R.id.semaphore2, R.id.semaphore3, R.id.semaphore4,
			R.id.semaphore5, R.id.semaphore6, R.id.semaphore7 };

	/**
	 * Data for conversion from Semaphore to description. For more info see
	 * semaphore2text().
	 */
	private static SparseIntArray sSemaphore = new SparseIntArray() {
		{
			put(0, 0); // ready
			put(1, 4); // a
			put(2, 5); // b
			put(3, 6); // c
			put(4, 7); // d
			put(5, 8); // e
			put(6, 9); // f
			put(7, 10); // g
			put(12, 11); // h
			put(13, 12); // i
			put(14, 14); // k
			put(15, 15); // l
			put(16, 16); // m
			put(17, 17); // n
			put(23, 18); // o
			put(24, 19); // p
			put(25, 20); // q
			put(26, 21); // r
			put(27, 22); // s
			put(34, 23); // t
			put(35, 24); // u
			put(36, 28); // y
			put(37, 3); // cancel
			put(45, 1); // numerals
			put(46, 13); // j
			put(47, 25); // v
			put(56, 26); // w
			put(57, 27); // x
			put(67, 29); // z
		}
	};

	/**
	 * Semaphores description (R.array.semaphore_description).
	 */
	private String[] mSemaphoreDesctiption;

	/**
	 * Two last checked inputs.
	 */
	private int[] mCheckedInputs = new int[] { 0, 0 };

	/**
	 * Returns view for {@link SemaphoreFragment} and sets
	 * {@link OnClickListener} for each input
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.semaphore, container, false);

		mSemaphoreDesctiption = getResources().getStringArray(
				R.array.semaphore_description);

		View semaphoreInput;
		for (int id : sSemaphoreInputs) {
			semaphoreInput = view.findViewById(id);
			semaphoreInput.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					semaphore2text(v);
				}
			});
		}

		return view;
	}

	/**
	 * Restores previous semaphore figure.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			mCheckedInputs = savedInstanceState.getIntArray(CHECKED_INPUTS);
			View lastChecked = getView().findViewById(
					sSemaphoreInputs[mCheckedInputs[1]]);
			semaphore2text(lastChecked);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putIntArray(CHECKED_INPUTS, mCheckedInputs);
	}

	/**
	 * Translates semaphore figure to meaning. Checks current semaphore figure
	 * and gets its description.
	 */
	protected void semaphore2text(View v) {
		View rooView = v.getRootView();

		// Ignores click to last clicked input if it is not for "ready" figure
		if (!((parseId(v) == mCheckedInputs[1]) && (mCheckedInputs[1] != 0))) {
			int inputId;
			RadioButton input;

			// Unchecks old input.
			inputId = sSemaphoreInputs[mCheckedInputs[0]];
			input = (RadioButton) rooView.findViewById(inputId);
			input.setChecked(false);

			mCheckedInputs[0] = mCheckedInputs[1];
			mCheckedInputs[1] = parseId(v);

			// Makes sure that checked inputs are checked
			for (int i = 0; i <= 1; i++) {
				inputId = sSemaphoreInputs[mCheckedInputs[i]];
				input = (RadioButton) rooView.findViewById(inputId);
				input.setChecked(true);
			}
		}

		int semaphoreValue = getSemaphoreValue();
		TextView output = (TextView) rooView.findViewById(R.id.semaphore_text);
		String semaphoreDescription = getSemaphoreDescription(semaphoreValue);
		output.setText(semaphoreDescription);

		ImageView image = (ImageView) rooView
				.findViewById(R.id.semaphore_image);
		image.setContentDescription(semaphoreDescription);
		image.setImageResource(getSemaphoreImage(semaphoreValue));
	}

	/**
	 * Computes semaphore value from mCheckedInputs.
	 */
	protected int getSemaphoreValue() {
		int semaphoreValue;
		if (mCheckedInputs[0] < mCheckedInputs[1]) {
			semaphoreValue = 10 * mCheckedInputs[0] + mCheckedInputs[1];
		} else {
			semaphoreValue = 10 * mCheckedInputs[1] + mCheckedInputs[0];
		}

		return semaphoreValue;
	}

	/**
	 * Finds description index in sSemaphore and returns description.
	 */
	protected String getSemaphoreDescription(int semaphoreValue) {
		return mSemaphoreDesctiption[sSemaphore.get(semaphoreValue)];
	}

	/**
	 * Finds description index in sSemaphore, determines drawable's name and
	 * returns resource's id.
	 */
	protected int getSemaphoreImage(int semaphoreValue) {
		int index = sSemaphore.get(semaphoreValue);
		String resourceName;
		if (index >= 4) {
			resourceName = "semaphore_" + (char) (index - 4 + 'a');
		} else {
			switch (index) {
			case 0:
				resourceName = "semaphore_ready";
				break;
			case 1:
				resourceName = "semaphore_numeric";
				break;
			case 2:
				resourceName = "semaphore_error";
				break;
			case 3:
				resourceName = "semaphore_cancel";
				break;
			default:
				resourceName = "semaphore_error";
				break;
			}
		}

		int resourceId = getResources().getIdentifier(resourceName, "drawable",
				"com.lacike.ciphertools");

		return resourceId;
	}

	/**
	 * Returns index of v in sSemaphoreInputs.
	 */
	protected int parseId(View v) {
		int resourceId = v.getId();
		int id = 0;
		while (sSemaphoreInputs[id] != resourceId) {
			id++;
		}
		return id;
	}
}
