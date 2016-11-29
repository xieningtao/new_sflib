package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.chatbean.SFPic;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.PhotoViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.utils.baseutil.GsonUtil;

/**
 * Created by mac on 16/11/27.
 */

public class PhotoItemView extends BaseChatItemView<SFMsg> {
    private DisplayImageOptions mOptions;

    public PhotoItemView(Context context) {
        super(context);
    }

    public PhotoItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    private void setOptions(DisplayImageOptions options) {
        mOptions = options;
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if (data == null || TextUtils.isEmpty(data.getContent())) return;
        if (baseChatHolder instanceof PhotoViewHolder) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) baseChatHolder;
            setUserBgBy(photoViewHolder.mPhotoContainer, data.isFromMe());
            photoViewHolder.mProgressBar.setVisibility(View.GONE);
            photoViewHolder.mProgressBarTv.setVisibility(View.GONE);
            SFPic pic = GsonUtil.parse(data.getContent(), SFPic.class);
            ImageLoader.getInstance().displayImage(pic.getUrl(), photoViewHolder.mBubbleIv, mOptions);
        }
    }
}
