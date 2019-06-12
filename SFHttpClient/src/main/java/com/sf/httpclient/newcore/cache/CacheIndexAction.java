package com.sf.httpclient.newcore.cache;

import java.util.List;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public interface CacheIndexAction<T> {

    List<T> loadIndex();

    boolean saveIndex(List<CacheIndexBean> cacheIndexBeanList);
}
