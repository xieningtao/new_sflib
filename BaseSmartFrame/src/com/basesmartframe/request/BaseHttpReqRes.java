package com.basesmartframe.request;

/**
 * Created by xieningtao on 16-5-20.
 */
public abstract class BaseHttpReqRes {
    private RequestChannel mHttpType = RequestChannel.NETWORK;

    public RequestChannel getHttpType() {
        return mHttpType;
    }

    public void setHttpType(RequestChannel httpType) {
        mHttpType = httpType;
    }
}
