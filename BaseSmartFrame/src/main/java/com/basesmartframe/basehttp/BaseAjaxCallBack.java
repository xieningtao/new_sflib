//package com.basesmartframe.basehttp;
//
//import android.text.TextUtils;
//
//import com.sf.loglib.L;
//import com.google.gson.*;
//import com.sf.httpclient.core.AjaxCallBack;
//import com.sf.httpclient.core.AjaxParams;
//
//
//public abstract class BaseAjaxCallBack<T> extends AjaxCallBack<String> {
//	private final String TAG = "http";
//
//	public static class HttpResult {
//		public final Object bean;
//		public final AjaxParams params;
//		public final BaseAjaxCallBack<?> mCallBack;
//
//		public HttpResult(Object bean, AjaxParams params,
//				BaseAjaxCallBack<?> mCallBack) {
//			super();
//			this.bean = bean;
//			this.params = params;
//			this.mCallBack = mCallBack;
//		}
//	}
//
//
//	public abstract void onResult(Object t, AjaxParams params);
//
//	private final Class<T> mClassType;
//
//	private AjaxParams mParams;
//
//	public BaseAjaxCallBack(Class<T> classType) {
//		this.mClassType = classType;
//	}
//
//	public void setAjaxParams(AjaxParams params) {
//		this.mParams = params;
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		L.info(TAG, "onStart");
//	}
//
//	@Override
//	public void onSuccess(String t) {
//		super.onSuccess(t);
//		L.info(TAG, "onSuccess");
////		if (BuildConfig.DEBUG) {
////			String tempResult = t == null ? "" : t;
////			L.info(TAG, "result: " + tempResult);
////		}
//		T bean = null;
//		if (!TextUtils.isEmpty(t)) {
//			try {
//				Gson gson = new Gson();
//				bean = gson.fromJson(t, mClassType);
//			} catch (Exception e) {
//				L.error(TAG, "fail to parse json : " + t);
//				bean = null;
//			}
//			//TODO change eventbus to sfbus
//			// onResult(bean, mParams);
////			EventBus.getDefault().post(new HttpResult(bean, mParams, this));
//		}
//	}
//
//	@Override
//	public void onLoading(long count, long current) {
//		super.onLoading(count, current);
//		L.info(TAG, "onLoading");
//	}
//
//	@Override
//	public void onFailure(Throwable t, int errorNo, String strMsg) {
//		super.onFailure(t, errorNo, strMsg);
//		L.info(TAG, "onFailure: exception: " + t + " errorNO: " + errorNo
//				+ " strMsg: " + strMsg);
//		// onResult(null, mParams);
////		EventBus.getDefault().post(new HttpResult(null, mParams, this));
//		//TODO change eventbus to sfbug
//	}
//
//}
