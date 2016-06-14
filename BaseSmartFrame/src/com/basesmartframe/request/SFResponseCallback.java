package com.basesmartframe.request;


import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 16-5-20.
 */
public interface SFResponseCallback<T> {

    void onResult(boolean success, T bean);

    void onStart(AjaxParams params);

    void onLoading(long count, long current);
}
