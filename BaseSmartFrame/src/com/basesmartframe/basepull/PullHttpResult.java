package com.basesmartframe.basepull;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.sfhttpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-11-27.
 */
public abstract class PullHttpResult<V> extends
        BaseAjaxCallBack<V> {

    public PullHttpResult(Class<V> classType) {
        super(classType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResult(Object t, AjaxParams params) {
        onPullResult((V) t, params);
    }

    protected abstract void onPullResult(V t,
                                         AjaxParams params);

}
