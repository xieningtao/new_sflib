/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sf.httpclient.newcore;

import com.sf.httpclient.entity.EntityCallBack;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.protocol.HttpContext;


abstract public class BaseHttpClientManager<T> implements BaseEntityHandler<T> {

    private final AbstractHttpClient client;
    private final HttpContext context;

    private int executionCount = 0;

    private EntityCallBack mEntityCallBack;

    public BaseHttpClientManager(AbstractHttpClient client, HttpContext context) {
        this.client = client;
        this.context = context;
    }

    public EntityCallBack getEntityCallBack() {
        return mEntityCallBack;
    }

    public void setEntityCallBack(EntityCallBack entityCallBack) {
        mEntityCallBack = entityCallBack;
    }

    private T makeRequestWithRetries(HttpUriRequest request) throws IOException {

        boolean retry = true;
        IOException cause = null;
        HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
        while (retry) {
            try {
                HttpResponse response = client.execute(request, context);
                return handleEntity(response.getEntity(), mEntityCallBack);
            } catch (UnknownHostException e) {
                cause = e;
                retry = retryHandler.retryRequest(cause, ++executionCount, context);
            } catch (IOException e) {
                cause = e;
                retry = retryHandler.retryRequest(cause, ++executionCount, context);
            } catch (NullPointerException e) {
                // HttpClient 4.0.x 之前的一个bug
                // http://code.google.com/p/android/issues/detail?id=5255
                cause = new IOException("NPE in HttpClient" + e.getMessage());
                retry = retryHandler.retryRequest(cause, ++executionCount, context);
            } catch (Exception e) {
                cause = new IOException("Exception" + e.getMessage());
                retry = retryHandler.retryRequest(cause, ++executionCount, context);
            }
        }
        if (cause != null)
            throw cause;
        else
            throw new IOException("未知网络错误");
    }

    public T sendRequest(HttpUriRequest params) throws Exception {
        return makeRequestWithRetries(params);
    }

}
