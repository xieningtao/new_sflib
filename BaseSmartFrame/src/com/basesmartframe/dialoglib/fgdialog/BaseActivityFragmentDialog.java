package com.basesmartframe.dialoglib.fgdialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.dialoglib.fgdialog.SFFragmentOperator.SFFragmentAnimatorFactory;
import com.basesmartframe.log.L;

/**
 * Created by xieningtao on 15-4-16.
 */
public abstract class BaseActivityFragmentDialog extends BaseActivity {

	private SFFragmentAction mSF_fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		SFFragmentAnimatorFactory fratory = getFragmentAnimatorFractory();
		mSF_fm = new SFFragmentOperator(getFragmentManager(), fratory);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	abstract protected SFFragmentAnimatorFactory getFragmentAnimatorFractory();

	protected void popFragment() {
		mSF_fm.popFragment();
	}

	protected boolean removeFragment(Fragment fragment) {
		return mSF_fm.removeFragment(fragment);
	}

	protected void replaceFragment(Fragment fragment, int containerId) {
		mSF_fm.replaceFragment(fragment, containerId);
	}

	protected void replaceFragmentToBackStack(Fragment fragment, String name,
			int containerId) {
		mSF_fm.replaceFragmentToBackStack(fragment, name, containerId);
	}

	protected void addFragment(Fragment fragment, int containerId) {
		mSF_fm.addFragment(fragment, containerId);
	}

	protected void addFragmentToBackStack(Fragment fragment, String name,
			int containerId) {
		mSF_fm.addFragmentToBackStack(fragment, name, containerId);
	}

}