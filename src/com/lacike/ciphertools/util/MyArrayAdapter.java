package com.lacike.ciphertools.util;

import com.lacike.ciphertools.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom implementation of {@link ArrayAdapter} for displaying list item with
 * icons and text label.
 */
public class MyArrayAdapter extends ArrayAdapter<Object> {
	private final Context mContext;
	private final String mValues[];
	private final int mTextViewResourceId;
	private final int[] mIcons;

	public MyArrayAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		mValues = objects;
		mTextViewResourceId = textViewResourceId;
		TypedArray icons = mContext.getResources().obtainTypedArray(
				R.array.tool_icons);
		mIcons = new int[icons.length()];
		for (int i = 0; i < icons.length(); i++) {
			mIcons[i] = icons.getResourceId(i, -1);
		}

		icons.recycle();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView textView = (TextView) inflater.inflate(mTextViewResourceId,
				parent, false);
		textView.setText(mValues[position]);
		textView.setCompoundDrawablesWithIntrinsicBounds(mIcons[position], 0,
				0, 0);

		return textView;
	}
}