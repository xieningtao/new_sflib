package com.basesmartframe.request;

import com.google.gson.Gson;
import com.sf.httpclient.newcore.SFHttpRequest;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFHttpStringHandler;
import com.sf.loglib.L;
import com.sflib.reflection.core.ThreadHelp;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpGsonHandler extends SFHttpStringHandler {

    private SFHttpStringCallback mSFHttpStringCallback;
    private final SFRequest mSFRequest;

    public SFHttpGsonHandler(SFRequest request, SFHttpStringCallback httpStringCallback) {
        this.mSFRequest = request;
        this.mSFHttpStringCallback = httpStringCallback;
        HttpUriRequest httpUriRequest=SFHttpHelper.getHttpUriRequest(request);
        setUriRequest(httpUriRequest);
    }

    @Override
    protected void onHandlerResult(final String result) {

        if (mSFHttpStringCallback != null) {
            try {
                Gson gson = new Gson();
                final Object object = gson.fromJson(result, mSFRequest.getClassType());
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        mSFHttpStringCallback.onSuccess(object);
                    }
                });
            } catch (final Exception e) {
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        mSFHttpStringCallback.onFailed(e);
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
                    mSFHttpStringCallback.onFailed(e);
                }
            }
        });
    }

}
