package com.basesmartframe.filecache.cacheentry;

/**
 * Created by xieningtao on 15-5-20.
 */
public class StringFileCache extends BaseFileCache {

    public StringFileCache(String key) {
        super(key);
    }

    @Override
    boolean isOutofDate() {
        return false;
    }

}
