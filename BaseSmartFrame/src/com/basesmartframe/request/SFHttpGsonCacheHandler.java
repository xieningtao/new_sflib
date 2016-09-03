package com.basesmartframe.request;

import com.google.gson.Gson;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.cache.SFCacheRequest;
import com.sf.httpclient.newcore.cache.SFHttpStringCacheHandler;
import com.sf.loglib.L;
import com.sflib.reflection.core.ThreadHelp;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class SFHttpGsonCacheHandler extends SFHttpStringCacheHandler {
    private SFHttpStringCallback mSFHttpStringCallback;

    public SFHttpGsonCacheHandler(SFCacheRequest request, SFHttpStringCallback httpStringCallback) {
        super(request);
        this.mSFHttpStringCallback = httpStringCallback;
        HttpUriRequest httpUriRequest = SFHttpHelper.getHttpUriRequest(request);
        setUriRequest(httpUriRequest);
    }

    @Override
    protected void onCacheHandlerResult(String result) {
        if (mSFHttpStringCallback != null) {
            try {
                Gson gson = new Gson();
                final Object object = gson.fromJson(result, getSFRequest().getClassType());
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        mSFHttpStringCallback.onSuccess(getSFRequest(), object);
                    }
                });
            } catch (final Exception e) {
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        mSFHttpStringCallback.onFailed(getSFRequest(), e);
                    }
                });
                L.error(TAG, TAG + ".onHandlerResult exception: " + e);

            }
        }
    }

    @Override
    protected void taskException(final Exception e) {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                if (mSFHttpStringCallback != null) {
                    mSFHttpStringCallback.onFailed(getSFRequest(), e);
                }
            }
        });
    }

    @Override
    public void onCanceled() {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                if (mSFHttpStringCallback != null) {
                    mSFHttpStringCallback.onCanceled(getSFRequest());
                }
            }
        });
    }
}
