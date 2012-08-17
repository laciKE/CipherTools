package com.lacike.ciphertools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToolsFragment extends ListFragment {

	protected String[] tools;
	
	private OnItemSelectedListener onItemSelectedListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Context context = getActivity();
		tools = getResources().getStringArray(R.array.tools);
		setListAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, tools));
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

	public interface OnItemSelectedListener {
		public void onItemSelected(int index);
	}
}
