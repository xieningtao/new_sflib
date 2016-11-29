package com.example.sfchat.item.view;

import android.content.Context;

import com.example.sfchat.item.holder.BaseChatHolder;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class ChatItemViewWithIndicator<T> extends BaseChatItemView<T> {
    public ChatItemViewWithIndicator(Context context) {
        super(context);
    }

    @Override
    protected void updateContentView(T data, BaseChatHolder chatHolder, int position) {

    }

    public ChatItemViewWithIndicator(Context context, BaseChatItemView baseChatItemView) {
        super(context, baseChatItemView);
    }


}
