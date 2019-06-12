package com.sf.loglib.task;

import android.content.Context;

import com.sf.loglib.SFLogUtils;

/**
 * Created by NetEase on 2016/11/10 0010.
 */

public class LogFileTask implements Runnable {
    private final Context mContext;
    private final String mMsg;
    private final String mTag;

    public LogFileTask(Context context, String tag, String msg) {
        mContext = context;
        mMsg = msg;
        mTag = tag;
    }


    @Override
    public void run() {
       SFLogUtils.log2File(mContext,mTag,mMsg);
    }


}
