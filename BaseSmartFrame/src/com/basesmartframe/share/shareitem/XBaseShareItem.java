package com.basesmartframe.share.shareitem;

import android.content.Context;

import com.basesmartframe.share.sharecore.XShareAction;
import com.basesmartframe.share.sharecore.XShareType;


/**
 * Created by xieningtao on 15-8-5.
 */
public abstract class XBaseShareItem {
    protected Context mContext;

    public XBaseShareItem(Context context) {
        this.mContext = context;
    }

    abstract public String getTitle();

    abstract public int getIconRes();

    abstract public XShareType getShareType();

    abstract public XShareAction createShareAction();


}
