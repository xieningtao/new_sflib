package com.sf.httpclient.newcore;

import com.sf.httpclient.entity.EntityCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class ByteHttpClientManager extends BaseHttpClientManager<byte[]> {
    public ByteHttpClientManager(AbstractHttpClient client, HttpContext context) {
        super(client, context);
    }

    @Override
    public byte[] handleEntity(HttpEntity entity, EntityCallBack callback) throws IOException {
        if (entity == null)
            return null;

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        long count = entity.getContentLength();
        long curCount = 0;
        int len = -1;
        InputStream is = entity.getContent();
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
            curCount += len;
            if(callback!=null)
                callback.callBack(count, curCount,false);
        }
        if(callback!=null)
            callback.callBack(count, curCount,true);
        byte[] data = outStream.toByteArray();
        outStream.close();
        is.close();
        return data;
    }
}
