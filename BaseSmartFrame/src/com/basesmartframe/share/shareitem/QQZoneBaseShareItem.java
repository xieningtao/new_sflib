package com.basesmartframe.share.shareitem;

import android.content.Context;

import com.basesmartframe.R;
import com.basesmartframe.share.ShareContent;
import com.basesmartframe.share.sharecore.XBaseSocialBaseShareItem;
import com.basesmartframe.share.sharecore.XDefaultSocialShareAction;
import com.basesmartframe.share.sharecore.XShareAction;
import com.basesmartframe.share.sharecore.XShareType;


/**
 * Created by xieningtao on 15-8-5.
 */
public class QQZoneBaseShareItem extends XBaseSocialBaseShareItem {
    public QQZoneBaseShareItem(Context context, ShareContent shareContent) {
        super(context, shareContent);
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.share_qqkongjian);
    }

    @Override
    public int getIconRes() {
        return R.drawable.icon_share_qqkongjian;
    }

    @Override
    public XShareType getShareType() {
        return XShareType.QZONE;
    }

    @Override
    public XShareAction createShareAction() {
        return new XDefaultSocialShareAction(mContext, mShareContent);
    }
}
