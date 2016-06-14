package com.basesmartframe.filecache.cache;


import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 15-5-21.
 */
public abstract class CacheEntity {

    public static interface DiffNetWorkCache<T>{
        int onDiffNetworkAndCache(T network, T cache);
    }
    public final String mDirKey;
    public final int mPageKey;
    public final DataSrcType mType;

    private DiffNetWorkCache mCacheEvent;

    public CacheEntity(String dirKey,int pageKey,DataSrcType type){
        this.mDirKey=dirKey;
        this.mPageKey=pageKey;
        this.mType=type;
    }

    public DiffNetWorkCache getmCacheEvent() {
        return mCacheEvent;
    }

    public void setmCacheEvent(DiffNetWorkCache mCacheEvent) {
        this.mCacheEvent = mCacheEvent;
    }

    abstract public boolean isCurPageCached(AjaxParams params);
}
