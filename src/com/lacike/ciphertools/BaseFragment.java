package com.lacike.ciphertools;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Base fragment implements help menu.
 */
public class BaseFragment extends Fragment {

	/**
	 * Id of help message, descendant sets it via setHelpMessage().
	 */
	private int mHelpMessageId = R.string.help;
	
	/**
	 * Sets hasOptionsMenu because handling onOptionsItemSelected.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	/**
	 * Creates options menu with help and settings items.
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.tool_menu, menu);
	}
	
	/**
	 * Handles help item selection.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_help:
	            showHelp();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Shows dialog with help message.
	 */
	protected void showHelp(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle(R.string.help);
		dialog.setMessage(mHelpMessageId);
		dialog.setPositiveButton(R.string.ok, null);
		dialog.show();
	}
	
	/**
	 * Sets resource id of help message.
	 * @param id
	 */
	protected void setHelpMessage(int id){
		mHelpMessageId = id;
	}
}
