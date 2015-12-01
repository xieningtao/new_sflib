package com.basesmartframe.share.sharecore;


import android.content.Context;

import com.basesmartframe.log.L;
import com.basesmartframe.share.ShareContent;
import com.basesmartframe.share.shareitem.XBaseShareItem;


/**
 * Created by xieningtao on 15-8-5.
 */
public class XDefaultSocialShareAction implements XShareAction {

    private final ShareContent mShareContent;
    private final Context mContext;

    /**
     * @param shareContent
     */
    public XDefaultSocialShareAction(Context context, ShareContent shareContent) {
        if (null == shareContent) {
//            shareContent = new ShareContent.ShareContentBuilder()
//                    .setContent(context.getResources().getString(R.string.share_content))
//                    .setTitle(context.getResources().getString(R.string.share_title))
//                    .setUrl(DataConst.URL_DEFAULT_SHARE)
//                    .setImage_url(DataConst.URL_PREFIX_QIAN_LONG + "/icon/share.png")
//                    .build();
        }
        this.mContext = context;
        this.mShareContent = shareContent;
    }

    @Override
    public void doShareAction(XBaseShareItem shareItem) {
        if (null == shareItem) {
            L.error(this, "share item is null");
            return;
        }
        shareToSocial(shareItem);
    }

    private void shareToSocial(XBaseShareItem item) {
//        AssertUtil.assertNotNull(item);
//        ShareHelper.Type type = ShareTypeToRealType(item.getShareType());
//        ShareHelper.ShareParams params = new ShareHelper.ShareParams(type);
//        params.title = mShareContent.title;
//        params.message = mShareContent.content;
//        params.url = mShareContent.url;
//        params.imageUrl = DataConst.URL_PREFIX_QIAN_LONG + "/icon/share.png";
//        ShareHelper.share((Activity) mContext, params, null);
//        BizKW.reportEventWithScreen(ReportConst.ShareScreenStatus);
    }

    /**
     * convert share type to ShareHelper.Type.
     * fail to convert ShareType.COPY
     *
     * @param shareType
     * @return
     */
//    private ShareHelper.Type ShareTypeToRealType(XShareType shareType) {
//        DAssertUtil.assertNotNull(shareType);
//        DAssertUtil.assertNotSame(shareType, XShareType.COPY);
//        ShareHelper.Type type = ShareHelper.Type.Unknown;
//        switch (shareType) {
//            case PENYOUQUAN:
//                type = ShareHelper.Type.Circle;
//                break;
//            case QQ:
//                type = ShareHelper.Type.QQ;
//                break;
//            case SINA:
//                type = ShareHelper.Type.SinaWeibo;
//                break;
//            case QZONE:
//                type = ShareHelper.Type.QZone;
//                break;
//            case WEIXIN:
//            default:
//                type = ShareHelper.Type.WeiXin;
//                break;
//        }
//        return type;
//    }
}
