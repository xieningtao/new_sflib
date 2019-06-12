package com.sflib.sharelib.img;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.sharelib.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFToast;
import com.sflib.sharelib.ShareUtils;
import com.sflib.sharelib.core.XShareInterface;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;


/**
 * Created by NetEase on 2017/5/3 0003.
 */

public class ImgQQShare extends XShareInterface {

    private Tencent tencent;

    public static final String APP_KEY_NEW = "";

    private XShareType mXShareType = XShareType.QQ;

    private IUiListener mIUiListener = new IUiListener() {
        @Override
        public void onError(UiError e) {
            L.info(TAG, "mQQShare.onError");
        }

        @Override
        public void onCancel() {
            L.info(TAG, "mQQShare.onCancel");
            if (tencent.isSessionValid()) {
                //TODO
            }
        }

        @Override
        public void onComplete(Object result) {
            L.info(TAG, "mQQShare.onComplete,result: " + result);
        }
    };

    public ImgQQShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
        init();
    }

    public void setShareType(XShareType shareType) {
        if (XShareType.QQ == shareType || XShareType.QZONE == shareType) {
            mXShareType = shareType;
        }
    }

    private void init() {
        tencent = Tencent.createInstance(APP_KEY_NEW, getContext());
    }

    private void doShareHelp() {
        ShareContent shareContent = getShareContent();
        Bundle params = new Bundle();
        if (mXShareType == XShareType.QQ) {
            params.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE, shareContent.title);
            params.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.image_url);
            tencent.shareToQQ((Activity) getContext(), params, mIUiListener);
        } else {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.title);
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.url);
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,shareContent.content);
            ArrayList<String> images = new ArrayList<>();
            images.add(shareContent.image_url);
            params.putStringArrayList(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL, images);
            tencent.shareToQzone((Activity) getContext(), params, mIUiListener);
        }
    }

    private void login() {
        IUiListener listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                L.info(TAG, "login.onComplete");
                doShareHelp();
            }

            @Override
            public void onError(UiError uiError) {
                L.info(TAG, "login.onError: " + uiError);
            }

            @Override
            public void onCancel() {
                L.info(TAG, "login.onCancel");
            }
        };
        if (tencent.isSessionValid() && tencent.getOpenId() != null) {
            doShareHelp();
        } else {
            tencent.login((Activity) getContext(), "get_user_info", listener);
        }
    }

    public void doShare() {
        if (!ShareUtils.isQQClientInstalled(getContext())) {
            SFToast.showToast(R.string.uninstalled_qq);
            return;
        }
        login();
    }
}
