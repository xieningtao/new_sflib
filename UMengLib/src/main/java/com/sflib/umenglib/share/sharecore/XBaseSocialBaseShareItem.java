package com.sflib.umenglib.share.sharecore;

import android.content.Context;

import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;


/**
 * Created by xieningtao on 15-8-5.
 */
public abstract class XBaseSocialBaseShareItem extends XBaseShareItem {
    protected ShareContent mShareContent;

    public XBaseSocialBaseShareItem(Context context, ShareContent shareContent) {
        super(context);
        this.mShareContent = shareContent;
    }
}
