package com.sf.httpclient.newcore;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpRequest {
    public final HttpUriRequest request;
    public final SFHttpStringCallback mSFHttpStringCallback;

    public SFHttpRequest(HttpUriRequest request, SFHttpStringCallback SFHttpStringCallback) {
        this.request = request;
        mSFHttpStringCallback = SFHttpStringCallback;
    }
}
