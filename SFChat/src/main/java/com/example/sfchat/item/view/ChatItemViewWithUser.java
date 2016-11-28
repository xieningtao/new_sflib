package com.example.sfchat.item.view;

import android.content.Context;

import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.chatbean.SFUserInfo;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.UserAndIndicatorViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class ChatItemViewWithUser extends BaseChatItemView<SFMsg> {
    private DisplayImageOptions mOptions;

    public ChatItemViewWithUser(Context context) {
        super(context);
    }

    public ChatItemViewWithUser(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    public void setOptions(DisplayImageOptions options) {
        mOptions = options;
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if (data == null || data.getUserInfo() == null) return;
        if (baseChatHolder instanceof UserAndIndicatorViewHolder) {
            UserAndIndicatorViewHolder userViewHolder = (UserAndIndicatorViewHolder) baseChatHolder;
            SFUserInfo userInfo = data.getUserInfo();
            ImageLoader.getInstance().displayImage(userInfo.getUrl(), userViewHolder.mUserViewHolder.mAvatar, mOptions);
        }
    }


}
