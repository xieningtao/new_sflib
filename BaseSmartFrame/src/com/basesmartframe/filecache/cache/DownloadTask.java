package com.basesmartframe.filecache.cache;


import com.sf.httpclient.FinalHttp;
import com.sf.httpclient.core.AjaxCallBack;
import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-11-13.
 */
public abstract class DownloadTask {
    public final String TAG = getClass().getName();
    private final String mUrl;

    public DownloadTask(String url) {
        mUrl = url;
    }

    public void realRunGet(final AjaxParams params) {
        FinalHttp http = new FinalHttp();
        http.get(mUrl, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                onResponse(true, params, s);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                StringBuilder builder = new StringBuilder();
                String errorMsg = builder.append("errorMsg: ").append(strMsg).append(" errorNo: ").append(errorNo).toString();
                onResponse(false, params, errorMsg);
            }
        });
    }

    public void realRunPost(final AjaxParams params) {
        FinalHttp http = new FinalHttp();
        http.post(mUrl, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
            }
        });
    }

    abstract void onResponse(boolean success, AjaxParams params, String t);
}
