package com.sf.httpclient.newcore;


import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 16-5-20.
 */
public abstract class SFRequest extends BaseHttpReqRes {
    public final String mUrl;
    private MethodType mMethodType = MethodType.GET;

    private AjaxParams mAjaxParams;

    public SFRequest(String url, MethodType methodType) {
        this(url, methodType, null);
    }

    public SFRequest(String url, MethodType methodType, AjaxParams params) {
        this.mUrl = url;
        this.mAjaxParams = params;
        this.mMethodType = methodType;
    }

    public AjaxParams getAjaxParams() {
        return mAjaxParams;
    }

    public void setAjaxParams(AjaxParams ajaxParams) {
        mAjaxParams = ajaxParams;
    }

    public MethodType getMethodType() {
        return mMethodType;
    }

    public abstract  Class getClassType();
}
