package com.sflib.umenglib.share;

import android.content.Context;
import android.util.AttributeSet;

import com.sflib.umenglib.share.sharecore.XBaseShareView;
import com.sflib.umenglib.share.shareitem.PengYouQuanBaseShareItem;
import com.sflib.umenglib.share.shareitem.QQBaseShareItem;
import com.sflib.umenglib.share.shareitem.QQZoneBaseShareItem;
import com.sflib.umenglib.share.shareitem.SinaBaseShareItem;
import com.sflib.umenglib.share.shareitem.WeiXinBaseShareItem;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;

import java.util.ArrayList;

/**
 * Created by xieningtao on 15-8-5.
 */
public class XSocialShareView extends XBaseShareView {
    private ShareContent mShareContent;

    public XSocialShareView(Context context) {
        super(context);
    }

    public XSocialShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setShareContent(ShareContent shareContent) {
        this.mShareContent = shareContent;
    }

    private ArrayList<XBaseShareItem> creatShareItems() {
        ArrayList<XBaseShareItem> shareItems = new ArrayList<XBaseShareItem>();

        WeiXinBaseShareItem weiXinShareItem = new WeiXinBaseShareItem(getContext(), mShareContent);
        shareItems.add(weiXinShareItem);

        PengYouQuanBaseShareItem pengYouQuanShareItem = new PengYouQuanBaseShareItem(getContext(), mShareContent);
        shareItems.add(pengYouQuanShareItem);

        QQBaseShareItem qqShareItem = new QQBaseShareItem(getContext(), mShareContent);
        shareItems.add(qqShareItem);

        SinaBaseShareItem sinaShareItem = new SinaBaseShareItem(getContext(), mShareContent);
        shareItems.add(sinaShareItem);

        QQZoneBaseShareItem qqZoneShareItem = new QQZoneBaseShareItem(getContext(), mShareContent);
        shareItems.add(qqZoneShareItem);

        return shareItems;
    }

    @Override
    public ArrayList<XBaseShareItem> getShareItems() {
        return creatShareItems();
    }
}
