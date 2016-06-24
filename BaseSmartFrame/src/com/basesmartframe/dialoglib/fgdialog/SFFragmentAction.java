package com.basesmartframe.dialoglib.fgdialog;

import android.app.Fragment;

import com.sf.loglib.L;

public interface SFFragmentAction {

	public abstract void popFragment();

	public abstract boolean removeFragment(Fragment fragment);

	public abstract void replaceFragment(Fragment fragment, int containerId);

	public abstract void replaceFragmentToBackStack(Fragment fragment,
			String name, int containerId);

	public abstract void addFragment(Fragment fragment, int containerId);

	public abstract void addFragmentToBackStack(Fragment fragment, String name,
			int containerId);

}