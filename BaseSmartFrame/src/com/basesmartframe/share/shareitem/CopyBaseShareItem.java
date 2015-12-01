package com.basesmartframe.share.shareitem;

import android.content.Context;

import com.basesmartframe.R;
import com.basesmartframe.share.sharecore.XCopyShareAction;
import com.basesmartframe.share.sharecore.XShareAction;
import com.basesmartframe.share.sharecore.XShareType;


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
