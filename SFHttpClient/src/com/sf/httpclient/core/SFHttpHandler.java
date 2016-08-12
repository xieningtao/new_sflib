package com.sf.httpclient.core;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
public class SFHttpHandler extends SFTaskHandler<byte[]> {

    private final AbstractHttpClient client;
    private final HttpContext context;
    private final HttpUriRequest mUriRequest;

    public SFHttpHandler(AbstractHttpClient client, HttpContext context, HttpUriRequest request) {
        this.client = client;
        this.context = context;
        this.mUriRequest = request;
    }

    @Override
    public byte[] doInBackground() {
        SFHttpClientManager clientManager = new SFHttpClientManager(client, context);
        try {
            return clientManager.sendRequest(mUriRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void taskDone(byte[] bytes) {

    }
}
