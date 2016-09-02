package com.sf.httpclient.newcore.cache;

import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.httpclient.newcore.cache.CacheType;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
abstract public class SFCacheRequest extends SFRequest {
    private CacheType mCacheType=CacheType.MEMORY;

    public SFCacheRequest(String url, MethodType methodType) {
        super(url, methodType);
    }

    public SFCacheRequest(String url, MethodType methodType, AjaxParams params) {
        super(url, methodType, params);
    }

    public CacheType getCacheType() {
        return mCacheType;
    }

    public void setCacheType(CacheType cacheType) {
        mCacheType = cacheType;
    }

    public CacheIndexBean getCacheIndexBean(){
        return CacheIndexManager.getInstance().build(mUrl,getAjaxParams(),mCacheType);
    }
}
