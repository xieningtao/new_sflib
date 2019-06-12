package com.sflib.umenglib.share.shareitem;

import android.content.Context;

import com.sflib.umenglib.R;
import com.sflib.umenglib.share.sharecore.XCopyShareAction;
import com.sflib.umenglib.share.sharecore.XShareAction;
import com.sflib.umenglib.share.sharecore.XShareType;


/**
 * Created by xieningtao on 15-8-5.
 */
public class CopyBaseShareItem extends XBaseShareItem {
    private String mCopyContent;

    public CopyBaseShareItem(String copyContent, Context context) {
        super(context);
        this.mCopyContent = copyContent;

    }

    @Override
    public String getTitle() {
        return mContext.getResources().getString(R.string.living_copy_share);
    }

    @Override
    public int getIconRes() {
        return R.drawable.living_copy_normal;
    }

    @Override
    public XShareType getShareType() {
        return XShareType.COPY;
    }

    @Override
    public XShareAction createShareAction() {
        return new XCopyShareAction(mCopyContent, mContext);
    }
}
