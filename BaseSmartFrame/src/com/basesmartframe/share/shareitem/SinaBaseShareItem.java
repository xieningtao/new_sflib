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
public class SinaBaseShareItem extends XBaseSocialBaseShareItem {
    public SinaBaseShareItem(Context context, ShareContent shareContent) {
        super(context, shareContent);
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.share_xinlangweibo);
    }

    @Override
    public int getIconRes() {
        return R.drawable.icon_share_xinlangweibo;
    }

    @Override
    public XShareType getShareType() {
        return XShareType.SINA;
    }

    @Override
    public XShareAction createShareAction() {
        return new XDefaultSocialShareAction(mContext, mShareContent);
    }
}
