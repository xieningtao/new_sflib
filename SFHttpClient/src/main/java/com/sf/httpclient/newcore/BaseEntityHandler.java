package com.sf.httpclient.newcore;

import com.sf.httpclient.entity.EntityCallBack;

import org.apache.http.HttpEntity;

import java.io.IOException;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public interface BaseEntityHandler<T> {
    T handleEntity(HttpEntity entity, EntityCallBack callback)throws IOException;
}
