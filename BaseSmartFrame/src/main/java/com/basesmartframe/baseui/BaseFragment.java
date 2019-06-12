package com.basesmartframe.baseui;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.loglib.L;
import com.sflib.reflection.core.SFBus;

public class BaseFragment extends Fragment {
	protected final String TAG=getClass().getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.info(TAG,getClass().getName()+ " onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		SFBus.register(this);
		L.info(this,getClass().getName()+ " register evenBus onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		SFBus.unregister(this);
		L.info(this,getClass().getName()+ " unregister evenBus onPause");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		L.info(TAG,getClass().getName()+" method->onHiddenChanged,hidden: "+hidden);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		L.info(TAG,getClass().getName()+ " onDestroyView");
	}
}
