package com.basesmartframe.filecache;

import com.basesmartframe.filecache.cache.ResponsePullListEntity;

/**
 * Created by xieningtao on 15-11-13.
 */
public class BaseFileCacheMessage {
    private Object mResult;
    private ResponsePullListEntity mResponsePullListEntity;
    private boolean succes;

    public BaseFileCacheMessage(Object result, ResponsePullListEntity responsePullListEntity) {
        this.mResult = result;
        this.mResponsePullListEntity = responsePullListEntity;
    }

    public BaseFileCacheMessage() {
    }

    public Object getResult() {
        return mResult;
    }

    public void setResult(Object result) {
        mResult = result;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public ResponsePullListEntity getResponsePullListEntity() {
        return mResponsePullListEntity;
    }

    public void setResponsePullListEntity(ResponsePullListEntity responsePullListEntity) {
        mResponsePullListEntity = responsePullListEntity;
    }
}
