package com.sflib.umenglib.share.shareitem;

import android.content.Context;

import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;


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
