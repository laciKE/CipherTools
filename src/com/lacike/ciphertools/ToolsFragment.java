package com.lacike.ciphertools;

import com.lacike.ciphertools.util.MyArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

/**
 * Creates fragment for list of available tools. OnListItemClick, delegate event
 * to {@link OnItemSelectedListener} implemented in {@link MainActivity}. Used
 * in dual pane layout.
 */
public class ToolsFragment extends ListFragment {

	protected String[] tools;

	private OnItemSelectedListener onItemSelectedListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Context context = getActivity();
		tools = getResources().getStringArray(R.array.tools);
		setListAdapter(new MyArrayAdapter(context,
				R.layout.list_item, tools));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onItemSelectedListener = (OnItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnItemSelectedListener interface");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);
		onItemSelectedListener.onItemSelected(position);
	}

	/**
	 * Calls when onListItemClick in {@link ToolsFragment}.
	 */
	public interface OnItemSelectedListener {
		public void onItemSelected(int index);
	}
	
}
