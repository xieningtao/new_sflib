package com.basesmartframe.baseui;

import android.app.Fragment;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.basesmartframe.baseutil.SFBus;
import com.basesmartframe.log.L;

public class BaseFragment extends Fragment{
	protected final String TAG=getClass().getName();
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
	
	public void onEvent(BaseAjaxCallBack.HttpResult result) {
		result.mCallBack.onResult(result.bean, result.params);
	}
}
