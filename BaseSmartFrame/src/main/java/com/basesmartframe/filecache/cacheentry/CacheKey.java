package com.basesmartframe.filecache.cacheentry;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by xieningtao on 15-5-20.
 */
public class CacheKey {

    private final String mKeyDir;

    private final String mPageNum;

    private final String mPostfix;

    public CacheKey(String keyDir,String pageNum,String postfix) {
        this.mKeyDir = keyDir;
        this.mPageNum=pageNum;
        this.mPostfix=postfix;
    }

    public CacheKey(String keyDir,String pageNum) {
        this(keyDir,pageNum,".txt");
    }


    public String getCacheKey(){
        if(TextUtils.isEmpty(mKeyDir)||TextUtils.isEmpty(mPageNum))return "";
        return mKeyDir+ File.separator+mPageNum+mPostfix;
    }
}
