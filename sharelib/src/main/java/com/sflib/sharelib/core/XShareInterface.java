package com.sflib.sharelib.core;

import android.content.Context;

import com.sflib.umenglib.share.ShareContent;

/**
 * Created by NetEase on 2016/10/12 0012.
 */
abstract public class XShareInterface {
    protected final String TAG = getClass().getName();
    private ShareContent mShareContent;
    private Context mContext;

    public XShareInterface(ShareContent shareContent, Context context) {
        mShareContent = shareContent;
        mContext = context;
    }

    protected ShareContent getShareContent() {
        return mShareContent;
    }

    protected Context getContext() {
        return mContext;
    }

    public abstract void doShare();

}
