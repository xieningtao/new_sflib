//package com.basesmartframe.basehttp;
//
//
//import com.sf.httpclient.core.AjaxCallBack;
//import com.sf.httpclient.core.AjaxParams;
//
//public class WrapBaseAjaxCallBack<T> extends AjaxCallBack<String> {
//
//	private final BaseAjaxCallBack<T> mCallBack;
//
//	public WrapBaseAjaxCallBack(BaseAjaxCallBack<T> callBack,AjaxParams params){
//		this.mCallBack=callBack;
//		callBack.setAjaxParams(params);
//	}
//	@Override
//	public void onStart() {
//		super.onStart();
//		mCallBack.onStart();
//	}
//
//	@Override
//	public void onLoading(long count, long current) {
//		super.onLoading(count, current);
//		mCallBack.onLoading(count, current);
//	}
//
//	@Override
//	public void onSuccess(String t) {
//		super.onSuccess(t);
//		mCallBack.onSuccess(t);
//	}
//
//	@Override
//	public void onFailure(Throwable t, int errorNo, String strMsg) {
//		super.onFailure(t, errorNo, strMsg);
//		mCallBack.onFailure(t, errorNo, strMsg);
//	}
//
//}
