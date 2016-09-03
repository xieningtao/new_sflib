package com.sf.httpclient.newcore.cache;

import android.text.TextUtils;

import com.sf.httpclient.newcore.HttpType;
import com.sf.httpclient.newcore.SFHttpStringHandler;
import com.sf.httpclient.newcore.cachecore.FileCacheManager;
import com.sf.httpclient.newcore.cachecore.MemoryCacheManager;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
abstract public class SFHttpStringCacheHandler extends SFHttpStringHandler {

    private final SFCacheRequest mSFRequest;

    public SFHttpStringCacheHandler(SFCacheRequest request) {
        this.mSFRequest = request;
    }

    protected SFCacheRequest getSFRequest() {
        return mSFRequest;
    }

    abstract protected void onCacheHandlerResult(final String result);

    @Override
    public void start() {
        if (mSFRequest.getHttpType() == HttpType.NETWORK) {
            super.start();
        } else if (mSFRequest.getHttpType() == HttpType.LOCAL_THEN_NETWORK) {
            CacheIndexBean cacheIndexBean = mSFRequest.getCacheIndexBean();
            String result = MemoryCacheManager.getInstance().get(cacheIndexBean);
            if (TextUtils.isEmpty(result)) {
                result = FileCacheManager.getInstance().get(cacheIndexBean);
            }
            if (!TextUtils.isEmpty(result)) {
                onCacheHandlerResult(result);
            }
            super.start();
        }
    }

    @Override
    protected void onHandlerResult(final String result) {
        if (mSFRequest.getCacheType() == CacheType.FILE) {
            FileCacheManager.getInstance().save(mSFRequest.getCacheIndexBean(), result);
        } else if (mSFRequest.getCacheType() == CacheType.MEMORY) {
            MemoryCacheManager.getInstance().save(mSFRequest.getCacheIndexBean(), result);
        } else if (mSFRequest.getCacheType() == CacheType.MEMORY_DB) {

        }
        onCacheHandlerResult(result);
    }

    @Override
    protected void taskException(final Exception e) {

    }

    @Override
    public void onCanceled() {

    }
}
