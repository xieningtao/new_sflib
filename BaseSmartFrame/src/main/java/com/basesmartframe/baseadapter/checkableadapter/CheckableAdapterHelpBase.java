package com.basesmartframe.baseadapter.checkableadapter;

import java.util.HashSet;
import java.util.Set;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.basesmartframe.R;
import com.basesmartframe.baseadapter.multiadapter.ItemClickInActionModePolicy;
import com.basesmartframe.baseadapter.multiadapter.MultiChoiceAdapter;
import com.basesmartframe.baseadapter.multiadapter.MultiChoiceAdapterHelperBase;
import com.basesmartframe.baseadapter.multiadapter.MultiChoiceBaseAdapter;

public abstract class CheckableAdapterHelpBase implements
		OnItemLongClickListener, OnItemClickListener, OnCheckedChangeListener {
	protected static final String TAG = MultiChoiceAdapterHelperBase.class
			.getSimpleName();
	private static final String BUNDLE_KEY = "mca__selection";
	private Set<Long> checkedItems = new HashSet<Long>();
	protected AdapterView<? super MultiChoiceBaseAdapter> adapterView;
	protected BaseAdapter owner;
	private OnItemClickListener itemClickListener;
	private Boolean itemIncludesCheckBox;
	/*
	 * Defines what happens when an item is clicked and the action mode was
	 * already active
	 */
	// private ItemClickInActionModePolicy itemClickInActionModePolicy = null;
	private boolean ignoreCheckedListener;

	protected CheckableAdapterHelpBase(BaseAdapter owner) {
		this.owner = owner;
	}

	public void restoreSelectionFromSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			return;
		}
		long[] array = savedInstanceState.getLongArray(BUNDLE_KEY);
		checkedItems.clear();
		if (array != null) {
			for (long id : array) {
				checkedItems.add(id);
			}
		}
	}

	public void setAdapterView(AdapterView<? super BaseAdapter> adapterView) {
		this.adapterView = adapterView;
		checkActivity();
		adapterView.setOnItemLongClickListener(this);
		adapterView.setOnItemClickListener(this);
		// adapterView.setAdapter(owner);
		parseAttrs();

		if (!checkedItems.isEmpty()) {
			startActionMode();
			onItemSelectedStateChanged();
		}
	}

	public void checkActivity() {
		Context context = getContext();
		if (context instanceof ListActivity) {
			throw new RuntimeException(
					"ListView cannot belong to an activity which subclasses ListActivity");
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.itemClickListener = listener;
	}

	public void save(Bundle outState) {
		long[] array = new long[checkedItems.size()];
		int i = 0;
		for (Long id : checkedItems) {
			array[i++] = id;
		}
		outState.putLongArray(BUNDLE_KEY, array);
	}

	private void multiCheck(long handle, boolean checked) {
		if (checked) {
			checkItem(handle);
		} else {
			uncheckItem(handle);
		}
	}

	private void singleCheck(long handle, boolean checked) {
		checkedItems.clear();
		if (checked) {
			multiCheck(handle, checked);
		} else {
			owner.notifyDataSetChanged();
			onItemSelectedStateChanged();
		}
	}

	public void setItemChecked(long handle, boolean checked) {
		 multiCheck(handle, checked);

//		singleCheck(handle, checked);
	}

	public void checkItem(long handle) {
		boolean wasSelected = isChecked(handle);
		if (wasSelected) {
			return;
		}
		if (!isActionModeStarted()) {
			startActionMode();
		}
		checkedItems.add(handle);
		owner.notifyDataSetChanged();
		onItemSelectedStateChanged();
	}

	public void uncheckItem(long handle) {
		boolean wasSelected = isChecked(handle);
		if (!wasSelected) {
			return;
		}
		checkedItems.remove(handle);
		owner.notifyDataSetChanged();
		onItemSelectedStateChanged();
	}

	public Set<Long> getCheckedItems() {
		// Return a copy to prevent concurrent modification problems
		return new HashSet<Long>(checkedItems);
	}

	public int getCheckedItemCount() {
		return checkedItems.size();
	}

	public boolean isChecked(long handle) {
		return checkedItems.contains(handle);
	}

	public Context getContext() {
		return adapterView.getContext();
	}

	public String getActionModeTitle(int count) {
		Resources res = getContext().getResources();
		return res.getQuantityString(R.plurals.selected_items, count, count);
	}

	private void onItemSelectedStateChanged() {
		int count = getCheckedItemCount();
		if (count == 0) {
			finishActionMode();
			return;
		}
		MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
		setActionModeTitle(adapter.getActionModeTitle(count));
	}

	protected abstract void setActionModeTitle(String title);

	protected abstract boolean isActionModeStarted();

	protected abstract void startActionMode();

	protected abstract void finishActionMode();

	protected abstract void clearActionMode();

	private void parseAttrs() {
		Context ctx = getContext();
		int styleAttr = R.attr.multiChoiceAdapterStyle;
		int defStyle = R.style.MultiChoiceAdapter_DefaultSelectedItemStyle;
		TypedArray ta = ctx.obtainStyledAttributes(null,
				R.styleable.MultiChoiceAdapter, styleAttr, defStyle);
		ta.recycle();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
		if (!adapter.isItemCheckable(position)) {
			return false;
		}
		int correctedPosition = correctPositionAccountingForHeader(adapterView,
				position);
		long handle = positionToSelectionHandle(correctedPosition);
		boolean wasChecked = isChecked(handle);
		setItemChecked(handle, !wasChecked);
		return true;
	}

	private int correctPositionAccountingForHeader(AdapterView<?> adapterView,
			int position) {
		ListView listView = (adapterView instanceof ListView) ? (ListView) adapterView
				: null;
		int headersCount = listView == null ? 0 : listView
				.getHeaderViewsCount();
		if (headersCount > 0) {
			position -= listView.getHeaderViewsCount();
		}
		return position;
	}

	protected long positionToSelectionHandle(int position) {
		return position;
	}


	public void onDestroyActionMode() {
		checkedItems.clear();
		clearActionMode();
		owner.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		onItemLongClick(adapterView, view, position, id);
		if (itemClickListener != null) {
			itemClickListener.onItemClick(adapterView, view, position, id);
		}
	}

	public View getView(int position, View viewWithoutSelection) {
		if (viewWithoutSelection instanceof Checkable) {
			long handle = positionToSelectionHandle(position);
			boolean selected = isChecked(handle);
			ignoreCheckedListener = true;
			((Checkable) viewWithoutSelection).setChecked(selected);
			ignoreCheckedListener = false;
		}
		if (itemIncludesCheckBox(viewWithoutSelection)) {
			initItemCheckbox(position, (ViewGroup) viewWithoutSelection);
		}
		return viewWithoutSelection;
	}

	private boolean itemIncludesCheckBox(View v) {
		if (itemIncludesCheckBox == null) {
			if (!(v instanceof ViewGroup)) {
				itemIncludesCheckBox = false;
			} else {
				ViewGroup root = (ViewGroup) v;
				itemIncludesCheckBox = root.findViewById(android.R.id.checkbox) != null;
			}
		}
		return itemIncludesCheckBox;
	}

	private void initItemCheckbox(int position, ViewGroup view) {
		CheckBox checkBox = view.findViewById(android.R.id.checkbox);
		boolean checked = isChecked(position);
		checkBox.setTag(position);
		checkBox.setChecked(checked);
		checkBox.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (ignoreCheckedListener) {
			return;
		}
		int position = (Integer) buttonView.getTag();
		setItemChecked(position, isChecked);
	}

}
