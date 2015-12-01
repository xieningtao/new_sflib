package com.basesmartframe.baseadapter.multiadapter;

import android.widget.BaseAdapter;

public class SFNoActionMultiChoiceBaseAdapterHelp extends
		SFNoActionMultiChoiceAdapterHelperBase {

	private boolean isActionStarted = false;

	protected SFNoActionMultiChoiceBaseAdapterHelp(BaseAdapter owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	public void setActionMode(boolean isStart) {
		this.isActionStarted = isStart;
	}

	@Override
	protected boolean isActionModeStarted() {
		return isActionStarted;
	}

	@Override
	protected void startActionMode() {
		isActionStarted = true;
	}

	@Override
	protected void finishActionMode() {
		isActionStarted = false;
		onDestroyActionMode();
	}

	@Override
	protected void clearActionMode() {
		isActionStarted = false;
	}

}
