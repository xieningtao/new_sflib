package com.sflib.sharelib.img;

import android.content.Context;

import com.example.sharelib.R;
import com.nostra13.universalimageloader.utils.L;
import com.sf.utils.baseutil.SFToast;
import com.sflib.sharelib.ShareUtils;
import com.sflib.sharelib.core.XShareInterface;
import com.sflib.umenglib.share.ShareConstant;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by NetEase on 2017/5/2 0002.
 */

public class ImgWeiXinShare extends XShareInterface {

    private XShareType mXShareType = XShareType.WEIXIN;

    public ImgWeiXinShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
    }

    public void setShareType(XShareType shareType) {
        if (shareType == XShareType.WEIXIN || shareType == XShareType.PENYOUQUAN) {
            this.mXShareType = shareType;
        }
    }

    @Override
    public void doShare() {
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), ShareConstant.WEIXIN_APPID, false);
        api.registerApp(ShareConstant.WEIXIN_APPID);
        if (!api.isWXAppInstalled()) {
            SFToast.showToast( R.string.wx_install);
            return;
        }
        if (!api.isWXAppSupportAPI()) {
            SFToast.showToast( R.string.wx_unspoort);
            return;
        }

        ShareContent shareContent = getShareContent();
        WXImageObject imageObject = new WXImageObject(shareContent.bitmap);
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = imageObject;
        message.thumbData = ShareUtils.doBitmapCompress(shareContent.bitmap, WXMediaMessage.THUMB_LENGTH_LIMIT);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "";
        req.message = message;
        req.scene = mXShareType == XShareType.WEIXIN ? req.WXSceneSession : req.WXSceneTimeline;
        boolean result = api.sendReq(req);
        L.i(TAG, "weixin send request result: " + result);
    }



}
