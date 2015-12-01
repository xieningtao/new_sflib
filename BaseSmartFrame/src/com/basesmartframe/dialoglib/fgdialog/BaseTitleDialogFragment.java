package com.basesmartframe.dialoglib.fgdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseFragment;

public abstract class BaseTitleDialogFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_dialog_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FrameLayout title_fl = (FrameLayout) view
				.findViewById(R.id.title_container);
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		View title_view = getTitleView(title_fl,inflater);
		if (null != title_view) {
			if (title_fl.getChildCount() > 0) {
				title_fl.removeAllViews();
			}
			onTitleViewCreated(title_view);
			title_fl.addView(title_view);
		}
	}

	abstract View getTitleView(View parentView,LayoutInflater inflater);

	abstract void onTitleViewCreated(View title_view);

}
