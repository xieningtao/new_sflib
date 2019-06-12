package com.sflib.umenglib.share.shareitem;

import android.content.Context;

import com.sflib.umenglib.R;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.sharecore.XBaseSocialBaseShareItem;
import com.sflib.umenglib.share.sharecore.XUmengSocialShareAction;
import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;


/**
 * Created by xieningtao on 15-8-5.
 */
public class PengYouQuanBaseShareItem extends XBaseSocialBaseShareItem {
    public PengYouQuanBaseShareItem(Context context, ShareContent shareContent) {
        super(context, shareContent);
    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.share_pengyouquan);
    }

    @Override
    public int getIconRes() {
        return R.drawable.icon_share_pengyouquan;
    }

    @Override
    public XShareType getShareType() {
        return XShareType.PENYOUQUAN;
    }

    @Override
    public XShareAction createShareAction() {
        return new XUmengSocialShareAction(mContext, mShareContent);
    }
}
