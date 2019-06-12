package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/16 0016.
 */
abstract public class SFHttpBackCallback<T> implements SFHttpCallback<T> {
    public void onStart(SFRequest request) {
    }

    public void onCanceled(SFRequest request) {

    }
}
