package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/16 0016.
 */
public interface SFHttpCallback<T> {

    void onSuccess(SFRequest request, T g);

    void onFailed(SFRequest request, Exception e);
}
