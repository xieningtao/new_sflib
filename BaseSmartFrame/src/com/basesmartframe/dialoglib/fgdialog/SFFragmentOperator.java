package com.basesmartframe.dialoglib.fgdialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import com.basesmartframe.log.L;

/**
 * Created by xieningtao on 15-4-16.
 */
public class SFFragmentOperator implements SFFragmentAction {

	public static interface SFFragmentAnimatorFactory {

		int enterAnimator();

		int exitAnimator();

		int popEnterAniamtor();

		int popExitAnimator();
	}

	public static class SFSimpleFragmentAnimatorFactory implements
			SFFragmentAnimatorFactory {
		@Override
		public int enterAnimator() {
			return 0;
		}

		@Override
		public int exitAnimator() {
			return 0;
		}

		@Override
		public int popEnterAniamtor() {
			return 0;
		}

		@Override
		public int popExitAnimator() {
			return 0;
		}
	}

	private final FragmentManager mfm;

	private final FragmentTransaction mft;

	private final SFFragmentAnimatorFactory mFactory;

	public SFFragmentOperator(FragmentManager fm,
			SFFragmentAnimatorFactory factory) {
		this.mfm = fm;
		this.mft = fm.beginTransaction();
		this.mFactory = factory;
	}

	public SFFragmentOperator(FragmentManager fm) {
		this(fm, null);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void addFragmentAnimator() {
		if (null != mFactory) {
			int in = mFactory.enterAnimator();
			int out = mFactory.exitAnimator();
			int pop_in = mFactory.popEnterAniamtor();
			int pop_out = mFactory.popExitAnimator();
			if (0 != in
					&& 0 != out
					&& 0 != pop_in
					&& 0 != pop_out
					&& Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
				mft.setCustomAnimations(in, out, pop_in, pop_out);
			} else if (0 != in && 0 != out)
				mft.setCustomAnimations(in, out);

		}
	}

	@Override
	public void addFragmentToBackStack(Fragment fragment, String name,
			int containerId) {
		if (fragment == null) {
			L.warn(this, "fragment is null");
			return;
		}
		addFragmentAnimator();
		mft.add(containerId, fragment);
		mft.addToBackStack(name);
		mft.commit();
	}

	@Override
	public void addFragment(Fragment fragment, int containerId) {
		if (fragment == null) {
			L.warn(this, "fragment is null");
			return;
		}
		mft.add(containerId, fragment);
		mft.commit();
	}

	@Override
	public void replaceFragmentToBackStack(Fragment fragment, String name,
			int containerId) {
		if (fragment == null) {
			L.warn(this, "fragment is null");
			return;
		}
		addFragmentAnimator();
		mft.replace(containerId, fragment);
		mft.addToBackStack(name);
		mft.commit();
	}

	@Override
	public void replaceFragment(Fragment fragment, int containerId) {
		if (fragment == null) {
			L.warn(this, "fragment is null");
			return;
		}
		addFragmentAnimator();
		mft.replace(containerId, fragment);
		mft.commit();
	}

	@Override
	public boolean removeFragment(Fragment fragment) {
		if (fragment == null) {
			L.warn(this, "fragment is null");
			return false;
		}
		addFragmentAnimator();
		mft.remove(fragment);
		mft.commit();
		return true;
	}

	@Override
	public void popFragment() {
		mfm.popBackStack();
	}

	public boolean isLastOneFragment() {
		int count = mfm.getBackStackEntryCount();
		return count == 1 ? true : false;
	}

}
