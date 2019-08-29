package com.sflib.umenglib.share.sharecore;

/**
 * Created by NetEase on 2016/6/20 0020.
 */
public interface OnXShareListener {
    void onResult(XShareType shareType);
    void onStart(XShareType shareType);
    void onError(XShareType shareType,Throwable throwable);
    void onCancel(XShareType shareType);
}
