package com.sf.httpclient.newcore;

import android.text.TextUtils;

import com.sf.httpclient.entity.EntityCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class FileHttpClientManager extends BaseHttpClientManager<File> {

    private String target;
    private boolean isResume;
    private boolean mStop = false;

    public FileHttpClientManager(AbstractHttpClient client, HttpContext context) {
        super(client, context);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isResume() {
        return isResume;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    public boolean isStop() {
        return mStop;
    }


    public void setStop(boolean stop) {
        this.mStop = stop;
    }


    @Override
    public File handleEntity(HttpEntity entity, EntityCallBack callback) throws IOException {
        if (TextUtils.isEmpty(target) || target.trim().length() == 0)
            return null;

        File targetFile = new File(target);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }

        if (mStop) {
            return targetFile;
        }
        long current = 0;
        FileOutputStream os = null;
        if (isResume) {
            current = targetFile.length();
            os = new FileOutputStream(target, true);
        } else {
            os = new FileOutputStream(target);
        }

        if (mStop) {
            return targetFile;
        }

        InputStream input = entity.getContent();
        long count = entity.getContentLength() + current;

        if (current >= count || mStop) {
            return targetFile;
        }

        int readLen = 0;
        byte[] buffer = new byte[1024];
        while (!mStop && !(current >= count) && ((readLen = input.read(buffer, 0, 1024)) > 0)) {//未全部读取
            os.write(buffer, 0, readLen);
            current += readLen;
            callback.callBack(count, current, false);
        }
        callback.callBack(count, current, true);

        if (mStop && current < count) { //用户主动停止
            throw new IOException("user stop download thread");
        }

        return targetFile;
    }
}
