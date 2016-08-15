package com.sf.httpclient.newcore;

import com.sf.loglib.L;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.UnsupportedEncodingException;
import java.math.MathContext;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpStringHandler extends SFHttpHandler<byte[]> {
    private String mCharset="UTF-8";
    public SFHttpStringHandler() {
    }

    public String getCharset() {
        return mCharset;
    }

    public void setCharset(String charset) {
        mCharset = charset;
    }

    @Override
    public void taskDone(byte[] bytes) {
        try {
            if(bytes!=null&&bytes.length>0) {
                String result = new String(bytes, mCharset);
                onHandlerResult(result);
            }
        } catch (UnsupportedEncodingException e) {
            L.error(TAG,TAG+".taskDone exception: "+e);
            taskException(e);
            onHandlerResult(null);
        }
    }

    protected void onHandlerResult(String result){

    }

    @Override
    protected BaseHttpClientManager createHttpClientManager(AbstractHttpClient client, HttpContext context) {
        return  new ByteHttpClientManager(client,context);
    }
}
