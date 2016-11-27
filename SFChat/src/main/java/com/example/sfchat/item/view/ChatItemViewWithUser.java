package com.example.sfchat.item.view;

import android.content.Context;

import com.example.sfchat.item.holder.BaseChatHolder;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class ChatItemViewWithUser<T> extends BaseChatItemView<T> {
    public ChatItemViewWithUser(Context context) {
        super(context);
    }

    public ChatItemViewWithUser(Context context, BaseChatItemView<T> baseChatItemView) {
        super(context, baseChatItemView);
    }

    @Override
    protected void updateContentView(T data, BaseChatHolder baseChatHolder, int position) {

    }


}
