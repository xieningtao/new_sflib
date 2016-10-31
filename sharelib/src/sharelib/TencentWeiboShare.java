package com.sflib.sharelib;

import android.content.Context;

import com.netease.huatian.module.sns.ShareStatistic;
import com.netease.huatian.module.sns.share.ShareContent;
import com.nostra13.universalimageloader.utils.L;
import com.tencent.t.Weibo;
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
        ShareStatistic.statisticTencentChannel(getContext());
        ShareContent shareContent = getShareContent();
        mWeibo.sendPicText(shareContent.content, shareContent.image_url, new IUiListener() {
            @Override
            public void onError(UiError e) {
                L.i(TAG, "mTencentWeibo.onError");
            }

            @Override
            public void onCancel() {
                L.i(TAG, "mTencentWeibo.onCancel");
            }

            @Override
            public void onComplete(Object result) {
                L.i(TAG, "mTencentWeibo.onComplete,result: " + result);
            }
        });
    }


}
