package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
public interface SFTask<T> {
    T doInBackground();
    void taskDone(T t);
    void onCanceled();
}
