package com.basesmartframe.basehttp;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.sfhttpclient.FinalHttp;
import com.sfhttpclient.core.AjaxCallBack;
import com.sfhttpclient.core.AjaxParams;

public class SFHttpClient {

	public static FinalHttp createHttpClient() {
		return new FinalHttp();
	}

	// ------------------get 请求-----------------------
	public static <T> void get(String url, BaseAjaxCallBack<T> callBack) {
		createHttpClient().get(url, null, callBack);
	}

	public static <T> void get(String url, AjaxParams params,
			BaseAjaxCallBack<T> callBack) {
		createHttpClient().get(url, params,
				new WrapBaseAjaxCallBack<T>(callBack, params));
	}

	public static <T> void get(String url, Header[] headers, AjaxParams params,
			BaseAjaxCallBack<T> callBack) {
		createHttpClient().get(url, headers, params,
				new WrapBaseAjaxCallBack<T>(callBack, params));
	}

	public static Object getSync(String url) {
		return createHttpClient().getSync(url,null);
	}

	public static Object getSync(String url, AjaxParams params) {
		return createHttpClient().getSync(url, params);
	}

	public Object getSync(String url, Header[] headers, AjaxParams params) {
		return createHttpClient().getSync(url, headers, params);
	}

	// ------------------post 请求-----------------------
	public static <T> void post(String url, BaseAjaxCallBack<T> callBack) {
		createHttpClient().post(url, callBack);
	}

	public static <T> void post(String url, AjaxParams params,
			BaseAjaxCallBack<T> callBack) {
		createHttpClient().post(url, params, new WrapBaseAjaxCallBack<T>(callBack, params));
	}

	public static <T> void post(String url, HttpEntity entity, String contentType,
			AjaxCallBack<? extends Object> callBack) {
		createHttpClient().post(url, entity, contentType, callBack);
	}

	public static <T> void post(String url, Header[] headers, AjaxParams params,
			String contentType, BaseAjaxCallBack<T> callBack) {
		createHttpClient().post(url, headers, params, contentType, new WrapBaseAjaxCallBack<T>(callBack, params));
	}

	public static <T> void post(String url, Header[] headers, HttpEntity entity,
			String contentType, BaseAjaxCallBack<T> callBack) {
		createHttpClient().post(url, headers, entity, contentType, callBack);
	}

	public static Object postSync(String url) {
		return postSync(url, null);
	}

	public static Object postSync(String url, AjaxParams params) {
		return createHttpClient().postSync(url, params);
	}

	public static Object postSync(String url, HttpEntity entity, String contentType) {
		return  createHttpClient().postSync(url, entity, contentType);
	}

	public static Object postSync(String url, Header[] headers, AjaxParams params,
			String contentType) {
		return createHttpClient().postSync(url, headers, params, contentType);
	}

	public Object postSync(String url, Header[] headers, HttpEntity entity,
			String contentType) {
		return createHttpClient().postSync(url, headers, entity, contentType);
	}


}
