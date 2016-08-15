package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
abstract public class SFHttpStringCallback<T> {

    public void onStart(){
        }

    abstract public void onSuccess(T g);

    abstract public void onFailed(Exception e);
}
