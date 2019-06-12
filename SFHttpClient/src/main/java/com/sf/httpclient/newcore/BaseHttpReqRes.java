package com.sf.httpclient.newcore;

/**
 * Created by xieningtao on 16-5-20.
 */
public abstract class BaseHttpReqRes {
    private HttpType mHttpType = HttpType.NETWORK;

    public HttpType getHttpType() {
        return mHttpType;
    }

    public void setHttpType(HttpType httpType) {
        mHttpType = httpType;
    }
}
