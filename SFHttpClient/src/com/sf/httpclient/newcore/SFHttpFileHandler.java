package com.sf.httpclient.newcore;

import com.sf.loglib.L;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpFileHandler extends SFHttpHandler<File> {
    private File mSaveFile;
    private String mTarget;

    public SFHttpFileHandler(String target) {
        mTarget = target;
    }

    @Override
    public void taskDone(File file) {

    }

    protected void onHandlerResult(String result) {

    }

    @Override
    protected BaseHttpClientManager createHttpClientManager(AbstractHttpClient client, HttpContext context) {
        return new FileHttpClientManager(client, context);
    }

    @Override
    protected void onClientManagerCreated(BaseHttpClientManager clientManager) {
        if (clientManager instanceof FileHttpClientManager) {
            FileHttpClientManager fileHttpClientManager= (FileHttpClientManager) clientManager;
            fileHttpClientManager.setTarget(mTarget);
        }
    }
}
