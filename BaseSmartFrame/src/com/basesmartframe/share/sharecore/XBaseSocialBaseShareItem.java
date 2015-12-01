package com.basesmartframe.share.sharecore;

import android.content.Context;

import com.basesmartframe.share.ShareContent;
import com.basesmartframe.share.shareitem.XBaseShareItem;


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
