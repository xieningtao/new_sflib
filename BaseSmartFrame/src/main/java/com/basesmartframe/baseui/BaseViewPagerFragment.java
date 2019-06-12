package com.basesmartframe.baseui;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;

public class BaseViewPagerFragment<T> extends BasePullListFragment<T> {

	@Override
	protected boolean onRefresh() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onLoadMore() {
		return false;
	}

	@Override
	protected int[] getLayoutIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void bindView(BaseAdapterHelper help, int position, T bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}


}
