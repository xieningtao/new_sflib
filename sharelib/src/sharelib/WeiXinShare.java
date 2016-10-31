package com.sflib.sharelib;

import android.content.Context;
import android.graphics.Bitmap;

import com.netease.huatian.R;
import com.netease.huatian.constant.Share;
import com.netease.huatian.module.sns.ShareStatistic;
import com.netease.huatian.module.sns.share.ShareContent;
import com.netease.huatian.module.sns.share.sharecore.XShareType;
import com.netease.huatian.utils.Utils;
import com.netease.huatian.view.CustomToast;
import com.nostra13.universalimageloader.utils.L;
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
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), Share.WX_APP_ID, false);
        api.registerApp(Share.WX_APP_ID);
        if (!api.isWXAppInstalled()) {
            CustomToast.showToast(getContext(), R.string.wx_install);
            return;
        }
        if (!api.isWXAppSupportAPI()) {
            CustomToast.showToast(getContext(), R.string.wx_unspoort);
            return;
        }
        if (mXShareType == XShareType.WEIXIN) {
            ShareStatistic.statisticWeiXinChannel(getContext());
        } else {
            ShareStatistic.statisticWxCircleChannel(getContext());
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
        localWXMediaMessage.thumbData = Utils.bmpToByteArray(thumbBmp, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = mXShareType == XShareType.WEIXIN ? localReq.WXSceneSession : localReq.WXSceneTimeline;
        boolean result = api.sendReq(localReq);
        L.i(TAG, "weixin send request result: " + result);
    }
}
