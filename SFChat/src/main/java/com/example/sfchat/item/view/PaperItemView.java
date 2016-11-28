package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;

import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.chatbean.SFPic;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.PaperViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.utils.baseutil.GsonUtil;

/**
 * Created by NetEase on 2016/11/28 0028.
 */

public class PaperItemView extends BaseChatItemView<SFMsg> {
    private DisplayImageOptions mOptions;

    public PaperItemView(Context context) {
        super(context);
    }

    public PaperItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    public void setOptions(DisplayImageOptions options) {
        mOptions = options;
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if (data == null || TextUtils.isEmpty(data.getContent())) return;
        if (baseChatHolder instanceof PaperViewHolder) {
            PaperViewHolder viewHolder = (PaperViewHolder) baseChatHolder;
            setUserBgBy(viewHolder.mContainerView, data.isFromMe());
            SFPic pic = GsonUtil.parse(data.getContent(), SFPic.class);
            ImageLoader.getInstance().displayImage(pic.getUrl(), viewHolder.mPaperIv, mOptions);
        }
    }
}
