package com.basesmartframe.request;

import com.basesmartframe.basevideo.util.ActionTimeGapHelp;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.sf.httpclient.newcore.BaseHttpClientManager;
import com.sf.httpclient.newcore.FileHttpClientManager;
import com.sf.httpclient.newcore.SFHttpFileCallback;
import com.sf.httpclient.newcore.SFHttpHandler;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.loglib.L;
import com.sflib.reflection.core.ThreadHelp;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpFileHandler extends SFHttpHandler<File> {
    private String mTarget;
    private SFHttpFileCallback mFileCallback;
    private Runnable mUpdateRunnable;
    private SFRequest mSFRequest;

    private final ActionTimeGapHelp mActionTimeGapHelp = new ActionTimeGapHelp();

    public SFHttpFileHandler(SFRequest request, String target, SFHttpFileCallback fileCallback) {
        this.mSFRequest = request;
        mTarget = target;
        this.mFileCallback = fileCallback;
        setUriRequest(SFHttpHelper.getHttpUriRequest(request));
        setTarget();
    }

    private void setTarget() {
        BaseHttpClientManager clientManager = getHttpClientManager();
        if (clientManager instanceof FileHttpClientManager) {
            FileHttpClientManager fileHttpClientManager = (FileHttpClientManager) clientManager;
            fileHttpClientManager.setTarget(mTarget);
        }
    }

    @Override
    public void taskDone(final File file) {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                if (mFileCallback != null) {
                    if (file != null) {
                        mFileCallback.onSuccess(mSFRequest, file);
                    } else {
                        mFileCallback.onFailed(mSFRequest, new NullPointerException("file is null"));
                    }
                }
            }
        });
    }

    @Override
    public void onCanceled() {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                if (mFileCallback != null) {
                    mFileCallback.onCanceled(mSFRequest);
                }
            }
        });
    }

    @Override
    protected BaseHttpClientManager createHttpClientManager(AbstractHttpClient client, HttpContext context) {
        return new FileHttpClientManager(client, context);
    }

    @Override
    public void callBack(final long count, final long current, boolean mustNoticeUI) {
        if (mUpdateRunnable == null) {
            mUpdateRunnable = new Runnable() {
                @Override
                public void run() {
                    if (mFileCallback != null) {
                        mFileCallback.callBack(mSFRequest, count, current);
                    }
                }
            };
        }
        if (mActionTimeGapHelp.isInActionGap(ActionTimeGapHelp.ACTION_1000)) {
            return;
        }
        ThreadHelp.runInMain(mUpdateRunnable);
    }
}
