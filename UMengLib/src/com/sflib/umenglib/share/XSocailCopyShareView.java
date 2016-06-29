package com.sflib.umenglib.share;

import android.content.Context;
import android.util.AttributeSet;

import com.sflib.umenglib.share.shareitem.CopyBaseShareItem;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;

import java.util.ArrayList;

/**
 * Created by xieningtao on 15-8-5.
 */
public class XSocailCopyShareView extends XSocialShareView {

    private String mCopyContent;

    public XSocailCopyShareView(Context context) {
        super(context);
    }

    public XSocailCopyShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCopyContent(String copyContent) {
        this.mCopyContent = copyContent;
    }

    @Override
    public ArrayList<XBaseShareItem> getShareItems() {
        ArrayList<XBaseShareItem> shareItems = super.getShareItems();
        if (null == shareItems) {
            shareItems = new ArrayList<>();
        }
        CopyBaseShareItem copyShareItem = new CopyBaseShareItem(mCopyContent, getContext());
        shareItems.add(copyShareItem);
        return shareItems;
    }
}
