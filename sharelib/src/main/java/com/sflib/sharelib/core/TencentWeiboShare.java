package com.sflib.sharelib.core;

import android.content.Context;

import com.sf.loglib.L;
import com.sflib.umenglib.share.ShareContent;
import com.tencent.open.t.Weibo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by NetEase on 2016/10/12 0012.
 */
public class TencentWeiboShare extends XShareInterface {

    private Tencent mTencent;
    private Weibo mWeibo;

    public TencentWeiboShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
        init();
    }

    private void init() {
        mTencent = Tencent.createInstance(QQShare.APP_KEY_NEW, getContext());
    }

    @Override
    public void doShare() {
        mWeibo = new Weibo(getContext(), mTencent.getQQToken());
        ShareContent shareContent = getShareContent();
        mWeibo.sendPicText(shareContent.content, shareContent.image_url, new IUiListener() {
            @Override
            public void onError(UiError e) {
                L.info(TAG, "mTencentWeibo.onError");
            }

            @Override
            public void onCancel() {
                L.info(TAG, "mTencentWeibo.onCancel");
            }

            @Override
            public void onComplete(Object result) {
                L.info(TAG, "mTencentWeibo.onComplete,result: " + result);
            }
        });
    }


}
