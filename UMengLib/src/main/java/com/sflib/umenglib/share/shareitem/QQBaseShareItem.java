package com.sflib.umenglib.share.shareitem;

import android.content.Context;

import com.sflib.umenglib.R;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XBaseSocialBaseShareItem;
import com.sflib.umenglib.share.sharecore.XUmengSocialShareAction;
import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;
import com.umeng.socialize.ShareAction;


/**
 * Created by xieningtao on 15-8-5.
 */
public class QQBaseShareItem extends XBaseSocialBaseShareItem {
    public QQBaseShareItem(Context context, ShareAction shareAction) {
        super(context, shareAction);
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.share_qq);
    }

    @Override
    public int getIconRes() {
        return R.drawable.icon_share_qq;
    }

    @Override
    public XShareType getShareType() {
        return XShareType.QQ;
    }

    @Override
    public XShareAction createShareAction() {
        return new XUmengSocialShareAction(mContext, mShareAction);
    }
}
