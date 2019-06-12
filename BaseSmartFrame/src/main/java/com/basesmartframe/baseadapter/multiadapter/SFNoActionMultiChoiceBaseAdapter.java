package com.basesmartframe.baseadapter.multiadapter;

import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public abstract class SFNoActionMultiChoiceBaseAdapter extends BaseAdapter implements MultiChoiceAdapter {

	private SFNoActionMultiChoiceBaseAdapterHelp helper = new SFNoActionMultiChoiceBaseAdapterHelp(this);

	/**
	 * @param savedInstanceState
	 *            Pass your activity's saved instance state here. This is
	 *            necessary for the adapter to retain its selection in the event
	 *            of a configuration change
	 */
	public SFNoActionMultiChoiceBaseAdapter(Bundle savedInstanceState) {
		helper.restoreSelectionFromSavedInstanceState(savedInstanceState);
	}

	/**
	 * Sets the adapter view on which this adapter will operate. You should call
	 * this method from the onCreate method of your activity. This method calls
	 * setAdapter on the adapter view, so you don't have to do it yourself
	 * 
	 * @param The
	 *            adapter view (typically a ListView) this adapter will operate
	 *            on
	 */
	public void setAdapterView(AdapterView<? super BaseAdapter> adapterView) {
		helper.setAdapterView(adapterView);
	}

	/**
	 * Register a callback to be invoked when an item in the associated
	 * AdapterView has been clicked
	 * 
	 * @param listener
	 *            The callback that will be invoked
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		helper.setOnItemClickListener(listener);
	}

	/**
	 * Always call this method from your activity's onSaveInstanceState method.
	 * This is necessary for the adapter to retain its selection in the event of
	 * a configuration change
	 * 
	 * @param outState
	 *            The same bundle you are passed in onSaveInstanceState
	 */
	public void save(Bundle outState) {
		helper.save(outState);
	}

	/**
	 * Changes the selection of an item. If the item was already in the
	 * specified state, nothing is done. May cause the activation of the action
	 * mode if an item is selected an no items were previously selected
	 * 
	 * @param position
	 *            The position of the item to select
	 * @param checked
	 *            The desired state (selected or not) for the item
	 */
	public void setItemChecked(long position, boolean checked) {
		helper.setItemChecked(position, checked);
	}

	/**
	 * Returns the indices of the currently selectly items.
	 * 
	 * @return Indices of the currently selectly items. The empty set if no item
	 *         is selected
	 */
	public Set<Long> getCheckedItems() {
		return helper.getCheckedItems();
	}

	/**
	 * Returns the number of selected items
	 * 
	 * @return Number of selected items
	 */
	public int getCheckedItemCount() {
		return helper.getCheckedItemCount();
	}

	/**
	 * Returns true if the item at the specified position is selected
	 * 
	 * @param position
	 *            The item position
	 * @return Whether the item is selected
	 */
	public boolean isChecked(long position) {
		return helper.isChecked(position);
	}

	public void setItemClickInActionModePolicy(
			ItemClickInActionModePolicy policy) {
		helper.setItemClickInActionModePolicy(policy);
	}

	public ItemClickInActionModePolicy getItemClickInActionModePolicy() {
		return helper.getItemClickInActionModePolicy();
	}

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set. Subclasses should implement this method instead of the traditional
	 * ListAdapter#getView
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	protected abstract View getViewImpl(int position, View convertView,
			ViewGroup parent);

	/**
	 * Subclasses can invoke this method in order to finish the action mode.
	 * This has the side effect of unselecting all items
	 */
	protected void finishActionMode() {
		helper.finishActionMode();
	}

	/**
	 * Convenience method for subclasses that need an activity context
	 */
	protected Context getContext() {
		return helper.getContext();
	}

	//
	// ActionMode.Callback implementation
	//


	//
	// MultiChoiceAdapter implementation
	//

	@Override
	public boolean isItemCheckable(int position) {
		return true;
	}

	@Override
	public String getActionModeTitle(int count) {
		return helper.getActionModeTitle(count);
	}

	//
	// BaseAdapter implementation
	//

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		View viewWithoutSelection = getViewImpl(position, convertView, parent);
		return helper.getView(position, viewWithoutSelection);
	}
}
