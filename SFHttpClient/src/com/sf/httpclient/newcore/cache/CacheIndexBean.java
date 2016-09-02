package com.sf.httpclient.newcore.cache;

import java.io.Serializable;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class CacheIndexBean implements Serializable{

    private String mParamsMd5;
    private String mPath;
    private int mCacheType;

    public CacheIndexBean(String paramsMd5, String path, int cacheType) {
        mParamsMd5 = paramsMd5;
        mPath = path;
        mCacheType = cacheType;
    }

    public String getParamsMd5() {
        return mParamsMd5;
    }

    public void setParamsMd5(String paramsMd5) {
        mParamsMd5 = paramsMd5;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public int getCacheType() {
        return mCacheType;
    }

    public void setCacheType(int cacheType) {
        mCacheType = cacheType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheIndexBean that = (CacheIndexBean) o;

        if (mCacheType != that.mCacheType) return false;
        if (!mParamsMd5.equals(that.mParamsMd5)) return false;
        return mPath.equals(that.mPath);

    }

    @Override
    public int hashCode() {
        int result = mParamsMd5.hashCode();
        result = 31 * result + mPath.hashCode();
        result = 31 * result + mCacheType;
        return result;
    }
}
