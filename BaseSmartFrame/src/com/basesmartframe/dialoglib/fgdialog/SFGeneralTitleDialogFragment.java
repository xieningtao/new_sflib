package com.basesmartframe.dialoglib.fgdialog;

import com.basesmartframe.R;

import android.view.LayoutInflater;
import android.view.View;

public class SFGeneralTitleDialogFragment extends BaseTitleDialogFragment {

	@Override
	void onTitleViewCreated(View title_view) {
		// TODO Auto-generated method stub

	}

	@Override
	View getTitleView(View parentView, LayoutInflater inflater) {
		return inflater.inflate(R.layout.three_items_title, null);
	}

}
