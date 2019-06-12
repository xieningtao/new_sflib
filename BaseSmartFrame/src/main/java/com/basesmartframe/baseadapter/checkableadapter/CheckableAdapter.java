package com.basesmartframe.baseadapter.checkableadapter;

import java.util.Set;

import com.basesmartframe.baseadapter.multiadapter.ItemClickInActionModePolicy;
import com.basesmartframe.baseadapter.multiadapter.MultiChoiceAdapter;
import com.basesmartframe.baseadapter.multiadapter.MultiChoiceAdapterHelper;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public class CheckableAdapter extends BaseAdapter implements  MultiChoiceAdapter{
	private final CheckableAdapterHelp helper = new CheckableAdapterHelp(this);
	private final BaseAdapter mAdapter;
	public CheckableAdapter(Bundle savedInstanceState,BaseAdapter adapter){
		this.mAdapter=adapter;
		helper.restoreSelectionFromSavedInstanceState(savedInstanceState);
	}
	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View viewWithoutSelection=mAdapter.getView(position, convertView, parent);
		return helper.getView(position, viewWithoutSelection);
	}
	
	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}
	
	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}
	
	@Override
	public void setAdapterView(AdapterView<? super BaseAdapter> adapterView) {
		helper.setAdapterView(adapterView);
	}
	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		helper.setOnItemClickListener(listener);
		
	}
	@Override
	public void save(Bundle outState) {
		helper.save(outState);
	}
	@Override
	public void setItemChecked(long position, boolean checked) {
		helper.setItemChecked(position, checked);
	}
	@Override
	public Set<Long> getCheckedItems() {
		return helper.getCheckedItems();
	}
	@Override
	public int getCheckedItemCount() {
		return helper.getCheckedItemCount();
	}
	@Override
	public boolean isChecked(long position) {
		return helper.isChecked(position);
	}
	@Override
	public void setItemClickInActionModePolicy(
			ItemClickInActionModePolicy policy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isItemCheckable(int position) {
		return true;
	}
	
	
	
	
	@Override
	public ItemClickInActionModePolicy getItemClickInActionModePolicy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getActionModeTitle(int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
