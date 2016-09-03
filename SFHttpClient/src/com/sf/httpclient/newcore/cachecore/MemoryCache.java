package com.sf.httpclient.newcore.cachecore;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class MemoryCache implements CacheAction<String> {
    private String mContent;

    @Override
    public boolean save(String str) {
        mContent = str;
        return true;
    }

    @Override
    public String get() {
        return mContent;
    }
}
