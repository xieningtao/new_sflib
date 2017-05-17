package com.sflib.sharelib;

import android.content.Context;

import com.sf.loglib.L;
import com.sflib.sharelib.core.XShareInterface;
import com.sflib.sharelib.img.ImgQQShare;
import com.sflib.sharelib.img.ImgSinaShare;
import com.sflib.sharelib.img.ImgWeiXinShare;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.OnXShareListener;
import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;

/**
 * Created by NetEase on 2017/5/17 0017.
 */

public class XImgSocialShareAction implements XShareAction {
    protected final String TAG = getClass().getName();
    private final ShareContent mShareContent;
    private final Context mContext;

    /**
     * @param shareContent
     */
    public XImgSocialShareAction(Context context, ShareContent shareContent) {
        this.mContext = context;
        this.mShareContent = shareContent;

    }


    @Override
    public void doShareAction(XBaseShareItem shareItem, OnXShareListener onXShareListener) {
        if (null == shareItem) {
            L.error(TAG, "share item is null");
            return;
        }
        shareToSocial(shareItem, onXShareListener);
    }

    private void shareToSocial(XBaseShareItem shareItem, OnXShareListener onXShareListener) {
        XShareType shareType = shareItem.getShareType();
        XShareInterface xShareInterface = null;
        switch (shareType) {
            case WEIXIN:
                xShareInterface = new ImgWeiXinShare(mShareContent, mContext);
                ((ImgWeiXinShare)xShareInterface).setShareType(XShareType.WEIXIN);
                break;
            case PENYOUQUAN:
                xShareInterface = new ImgWeiXinShare(mShareContent, mContext);
                ((ImgWeiXinShare)xShareInterface).setShareType(XShareType.PENYOUQUAN);
                break;
            case QQ:
                xShareInterface = new ImgQQShare(mShareContent, mContext);
                ((ImgQQShare)xShareInterface).setShareType(XShareType.QQ);
                break;
            case QZONE:
                xShareInterface=new ImgQQShare(mShareContent,mContext);
                ((ImgQQShare)xShareInterface).setShareType(XShareType.QZONE);
                break;
            case SINA:
                xShareInterface = new ImgSinaShare(mShareContent, mContext);
                break;
        }
        if (xShareInterface != null) {
            xShareInterface.doShare();
        }
    }
}

