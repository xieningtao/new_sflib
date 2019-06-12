package com.sflib.sharelib;

import android.content.Context;

import com.sf.loglib.L;
import com.sflib.sharelib.core.QQShare;
import com.sflib.sharelib.core.SinaShare;
import com.sflib.sharelib.core.TencentWeiboShare;
import com.sflib.sharelib.core.WeiXinShare;
import com.sflib.sharelib.core.XShareInterface;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.OnXShareListener;
import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;

/**
 * Created by NetEase on 2016/10/13 0013.
 */
public class XSFSocialShareAction implements XShareAction {

    protected final String TAG = getClass().getName();
    private final ShareContent mShareContent;
    private final Context mContext;

    /**
     * @param shareContent
     */
    public XSFSocialShareAction(Context context, ShareContent shareContent) {
        this.mContext = context;
        this.mShareContent = shareContent;

    }


    @Override
    public void doShareAction(XBaseShareItem shareItem, OnXShareListener onXShareListener) {
        if (null == shareItem) {
            L.error(this, "share item is null");
            return;
        }
        shareToSocial(shareItem, onXShareListener);
    }

    private void shareToSocial(XBaseShareItem shareItem, OnXShareListener onXShareListener) {
        XShareType shareType = shareItem.getShareType();
        XShareInterface xShareInterface = null;
        switch (shareType) {
            case WEIXIN:
                xShareInterface = new WeiXinShare(mShareContent, mContext);
                ((WeiXinShare)xShareInterface).setShareType(XShareType.WEIXIN);
                break;
            case PENYOUQUAN:
                xShareInterface = new WeiXinShare(mShareContent, mContext);
                ((WeiXinShare)xShareInterface).setShareType(XShareType.PENYOUQUAN);
                break;
            case QQ:
                xShareInterface = new QQShare(mShareContent, mContext);
                ((QQShare)xShareInterface).setShareType(XShareType.QQ);
                break;
            case QZONE:
                xShareInterface=new QQShare(mShareContent,mContext);
                ((QQShare)xShareInterface).setShareType(XShareType.QZONE);
                break;
            case SINA:
                xShareInterface = new SinaShare(mShareContent, mContext);
                break;
            case TENCENTWEIBO:
                xShareInterface = new TencentWeiboShare(mShareContent, mContext);
                break;
        }
        if (xShareInterface != null) {
            xShareInterface.doShare();
        }
    }

}
