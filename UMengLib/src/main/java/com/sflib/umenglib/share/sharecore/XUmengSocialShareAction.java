package com.sflib.umenglib.share.sharecore;


import android.app.Activity;
import android.content.Context;

import com.sf.loglib.L;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import static com.umeng.socialize.bean.SHARE_MEDIA.*;


/**
 * Created by xieningtao on 15-8-5.
 */
public class XUmengSocialShareAction implements XShareAction {
    protected final String TAG = getClass().getName();
    private final Context mContext;
    private ShareAction mShareAction;

    public XUmengSocialShareAction(Context context, ShareAction shareAction) {
        this.mContext = context;
        this.mShareAction = shareAction;

    }


    @Override
    public void doShareAction(XBaseShareItem shareItem, OnXShareListener onXShareListener) {
        if (null == shareItem) {
            L.error(this, "share item is null");
            return;
        }
        shareToSocial(shareItem, onXShareListener);
    }

    private void shareToSocial(XBaseShareItem item, OnXShareListener onXShareListener) {
        if (mContext != null && mContext instanceof Activity) {
            SHARE_MEDIA plartform = shareType2ShareMedia(item.getShareType());
            if (plartform != null && mShareAction != null) {
                mShareAction.setPlatform(plartform);
                mShareAction.share();
            } else {
                L.error(TAG, "plartform is null or sharecontent is null");
            }
        } else {
            L.error(TAG, "method->shareToSocial,mContext is not activity");
        }
    }

    private XShareType ShareMedia2ShareType(SHARE_MEDIA shareMedia) {
        switch (shareMedia) {
            case WEIXIN:
                return XShareType.WEIXIN;
            case WEIXIN_CIRCLE:
                return XShareType.PENYOUQUAN;
            case SINA:
                return XShareType.SINA;
            case QQ:
                return XShareType.QQ;
            case QZONE:
                return XShareType.QZONE;
        }
        return null;
    }

    private SHARE_MEDIA shareType2ShareMedia(XShareType type) {
        switch (type) {
            case WEIXIN:
                return WEIXIN;
            case PENYOUQUAN:
                return WEIXIN_CIRCLE;
            case SINA:
                return SINA;
            case QQ:
                return QQ;
            case QZONE:
                return QZONE;
        }
        return null;
    }

   class InnerUMShareListener implements UMShareListener {
        private OnXShareListener mOnXShareListener;

        public void setOnXShareListener(OnXShareListener onXShareListener) {
            this.mOnXShareListener = onXShareListener;
        }

       @Override
       public void onStart(SHARE_MEDIA share_media) {
           if(mOnXShareListener != null){
               mOnXShareListener.onStart(ShareMedia2ShareType(share_media));
           }
       }

       @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (mOnXShareListener != null) {
                mOnXShareListener.onResult(ShareMedia2ShareType(share_media));
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (mOnXShareListener != null) {
                mOnXShareListener.onError(ShareMedia2ShareType(share_media), throwable);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            if (mOnXShareListener != null) {
                mOnXShareListener.onCancel(ShareMedia2ShareType(share_media));
            }
        }
    }
}
