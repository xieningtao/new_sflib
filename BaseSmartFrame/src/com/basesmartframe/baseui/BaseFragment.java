package com.basesmartframe.baseui;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.basesmartframe.log.L;

import de.greenrobot.event.EventBus;
import android.app.Fragment;

public class BaseFragment extends Fragment{
	protected final String TAG=getClass().getName();
	@Override
	public void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
		L.info(this,getClass().getName()+ " register evenBus onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);
		L.info(this,getClass().getName()+ " unregister evenBus onPause");
	}
	
	public void onEvent(BaseAjaxCallBack.HttpResult result) {
		result.mCallBack.onResult(result.bean, result.params);
	}
}
