package com.basesmartframe.filecache.cacheentry;

/**
 * Created by xieningtao on 15-5-20.
 */
public interface CacheAction<T> {

    boolean writeContentTo(T content);

    T readContent();
}
