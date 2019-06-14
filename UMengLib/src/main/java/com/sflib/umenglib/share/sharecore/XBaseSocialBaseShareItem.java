package com.sflib.umenglib.share.sharecore;

import android.content.Context;

import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;
import com.umeng.socialize.ShareAction;


/**
 * Created by xieningtao on 15-8-5.
 */
public abstract class XBaseSocialBaseShareItem extends XBaseShareItem {
    protected ShareAction mShareAction;

    public XBaseSocialBaseShareItem(Context context, ShareAction shareAction) {
        super(context);
        this.mShareAction = shareAction;
    }
}
