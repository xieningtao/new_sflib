package com.sflib.sharelib;

import android.content.Context;
import android.graphics.Bitmap;

import com.netease.huatian.constant.Share;
import com.netease.huatian.module.sns.ShareStatistic;
import com.netease.huatian.module.sns.share.ShareContent;
import com.netease.huatian.module.sns.share.sharecore.XShareType;
import com.netease.huatian.utils.Utils;

import im.yixin.sdk.api.IYXAPI;
import im.yixin.sdk.api.SendMessageToYX;
import im.yixin.sdk.api.YXAPIFactory;
import im.yixin.sdk.api.YXMessage;
import im.yixin.sdk.api.YXWebPageMessageData;

/**
 * Created by NetEase on 2016/10/12 0012.
 */
public class YiXinShare extends XShareInterface {
    private IYXAPI api;
    private XShareType mXShareType = XShareType.YIXIN;

    public YiXinShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
        init();
    }

    public void setXShareType(XShareType shareType) {
        if (shareType == XShareType.YIXIN || shareType == XShareType.YIXINCIRCLE) {
            mXShareType = shareType;
        }
    }

    private void init() {
        api = YXAPIFactory.createYXAPI(getContext(), Share.YX_APP_ID);
        api.registerApp();
    }

    @Override
    public void doShare() {
        if(mXShareType==XShareType.YIXIN) {
            ShareStatistic.statisticYIXINChannel(getContext());
        }else {
            ShareStatistic.statisticYIXINCIRCLEChannel(getContext());
        }
        YXMessage msg = new YXMessage();
        YXWebPageMessageData imgObj = new YXWebPageMessageData();
        ShareContent shareContent = getShareContent();
        imgObj.webPageUrl = shareContent.url;
        msg.messageData = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(shareContent.bitmap, 200, 200, true);
        msg.thumbData = Utils.bitmap2Bytes(thumbBmp);
        msg.title = shareContent.title;
        msg.description = shareContent.content;
        // msg.comment = content;
        SendMessageToYX.Req req = new SendMessageToYX.Req();
        req.transaction = System.currentTimeMillis() + "";
        req.message = msg;
        req.scene = mXShareType==XShareType.YIXIN? SendMessageToYX.Req.YXSceneSession:SendMessageToYX.Req.YXSceneTimeline;
        api.sendRequest(req);
    }
}
