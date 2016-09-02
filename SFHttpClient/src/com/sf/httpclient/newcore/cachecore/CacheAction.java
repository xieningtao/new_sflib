package com.sf.httpclient.newcore.cachecore;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public interface CacheAction<T> {

    boolean save(T str);

    T get();
}
