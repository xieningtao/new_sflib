package com.sflib.sharelib.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.sf.loglib.L;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * Created by NetEase on 2016/10/12 0012.
 */
public class QQShare extends XShareInterface {


    private static Tencent mTencent;

    public static final String APP_KEY_NEW = "100341573";

    private XShareType mXShareType = XShareType.QQ;

    private IUiListener mIUiListener = new IUiListener() {
        @Override
        public void onError(UiError e) {
            L.info(TAG, "mQQShare.onError");
        }

        @Override
        public void onCancel() {
            L.info(TAG, "mQQShare.onCancel");
        }

        @Override
        public void onComplete(Object result) {
            L.info(TAG, "mQQShare.onComplete,result: " + result);
        }
    };

    public QQShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
        init();
    }

    public void setShareType(XShareType shareType) {
        if (XShareType.QQ == shareType || XShareType.QZONE == shareType) {
            mXShareType = shareType;
        }
    }

    private void init() {
        mTencent = Tencent.createInstance(APP_KEY_NEW, getContext());
    }


    public void doShare() {
        ShareContent shareContent = getShareContent();
        Bundle params = new Bundle();
        params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE, shareContent.title);
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_SUMMARY, shareContent.content);
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.url);
        params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_APP_NAME, shareContent.getAppName());
        if (mXShareType == XShareType.QQ) {
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.image_url);
            mTencent.shareToQQ((Activity) getContext(), params, mIUiListener);
        } else {
            ArrayList<String> images = new ArrayList<>();
            images.add(shareContent.image_url);
            params.putStringArrayList(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL, images);
            mTencent.shareToQzone((Activity) getContext(), params, mIUiListener);
        }

    }


}
