package com.example.sfchat.item.view;

import android.content.Context;

import com.example.sfchat.item.holder.BaseChatHolder;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

abstract public class BaseChatItemView<T> {
    private Context mContext;
    private BaseChatItemView<T> mBaseChatItemView;

    public BaseChatItemView(Context context) {
        this(context, null);
    }

    public BaseChatItemView(Context context, BaseChatItemView<T> baseChatItemView) {
        mContext = context;
        mBaseChatItemView = baseChatItemView;
    }

    protected Context getContext() {
        return mContext;
    }

    public BaseChatItemView getChatItemView() {
        return mBaseChatItemView;
    }

    public void updateItemView(T data, BaseChatHolder baseChatHolder, int position) {
        if (mBaseChatItemView != null) {
            mBaseChatItemView.updateItemView(data, baseChatHolder, position);
        }
        updateContentView(data, baseChatHolder, position);
    }

    abstract protected void updateContentView(T data, BaseChatHolder baseChatHolder, int position);

}
