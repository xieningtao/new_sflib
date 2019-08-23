package com.sflib.sharelib.core;

import android.content.Context;
import android.graphics.Bitmap;

import com.sf.loglib.L;
import com.sf.utils.baseutil.BitmapHelp;
import com.sflib.umenglib.share.ShareConstant;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by NetEase on 2016/10/12 0012.
 */
public class WeiXinShare extends XShareInterface {

    private XShareType mXShareType = XShareType.WEIXIN;

    public WeiXinShare(ShareContent shareContent, Context context) {
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
//            CustomToast.showToast(getContext(), R.string.wx_install);
            return;
        }
        if (!api.isWXAppSupportAPI()) {
//            CustomToast.showToast(getContext(), R.string.wx_unspoort);
            return;
        }
        ShareContent shareContent = getShareContent();
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = shareContent.url;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = shareContent.title;
        localWXMediaMessage.description = shareContent.content;
        WXImageObject imgObj = new WXImageObject(shareContent.bitmap);
        localWXMediaMessage.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(shareContent.bitmap, 120, 120, true);
        localWXMediaMessage.thumbData = BitmapHelp.bmpToByteArray(thumbBmp, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = mXShareType == XShareType.WEIXIN ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean result = api.sendReq(localReq);
        L.info(TAG, "weixin send request result: " + result);
    }
}
