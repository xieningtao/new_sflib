package com.basesmartframe.filecache.cache;


import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.httpclient.FinalHttp;
import com.sf.httpclient.core.AjaxCallBack;
import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;

/**
 * Created by xieningtao on 15-11-13.
 */
public abstract class DownloadTask<T> {
    public final String TAG = getClass().getName();
    private final String mUrl;
    private final Class<T> mClassType;

    public DownloadTask(String url, Class<T> classType) {
        mUrl = url;
        this.mClassType = classType;
    }

    public void realRunGet(final AjaxParams params) {
        doRequest(params,MethodType.GET);

    }

    private void doRequest(final AjaxParams params, MethodType methodType) {
        SFRequest request = new SFRequest(mUrl, methodType) {
            @Override
            public Class getClassType() {
                return mClassType;
            }
        };
        request.setAjaxParams(params);
        SFHttpGsonHandler mGsonHandler = new SFHttpGsonHandler(request, new SFHttpStringCallback<T>() {

            @Override
            public void onSuccess(SFRequest request, T g) {
            }

            @Override
            public void onFailed(SFRequest request, Exception e) {

            }
        });
        mGsonHandler.start();
    }

    public void realRunPost(final AjaxParams params) {
        doRequest(params,MethodType.POST);
    }

    abstract void onResponse(boolean success, AjaxParams params, String t);
}
