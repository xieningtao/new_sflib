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
import com.umeng.socialize.ShareAction;

import java.util.ArrayList;

/**
 * Created by xieningtao on 15-8-5.
 */
public class XSocialShareView extends XBaseShareView {
    private ShareAction mShareAction;

    public XSocialShareView(Context context) {
        super(context);
    }

    public XSocialShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setShareAction(ShareAction shareAction) {
        this.mShareAction = shareAction;
    }


    private ArrayList<XBaseShareItem> creatShareItems() {
        ArrayList<XBaseShareItem> shareItems = new ArrayList<XBaseShareItem>();

        WeiXinBaseShareItem weiXinShareItem = new WeiXinBaseShareItem(getContext(), mShareAction);
        shareItems.add(weiXinShareItem);

        PengYouQuanBaseShareItem pengYouQuanShareItem = new PengYouQuanBaseShareItem(getContext(), mShareAction);
        shareItems.add(pengYouQuanShareItem);

        QQBaseShareItem qqShareItem = new QQBaseShareItem(getContext(), mShareAction);
        shareItems.add(qqShareItem);

        SinaBaseShareItem sinaShareItem = new SinaBaseShareItem(getContext(), mShareAction);
        shareItems.add(sinaShareItem);

        QQZoneBaseShareItem qqZoneShareItem = new QQZoneBaseShareItem(getContext(), mShareAction);
        shareItems.add(qqZoneShareItem);

        return shareItems;
    }

    @Override
    public ArrayList<XBaseShareItem> getShareItems() {
        return creatShareItems();
    }
}
