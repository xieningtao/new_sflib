package com.example.sfchat.item;

import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.view.BaseChatItemView;

/**
 * Created by mac on 16/11/27.
 */

public class HolderAndItemView {

    public final BaseChatItemView chatItemView;
    public final BaseChatHolder chatHolder;
    public final boolean isRight;
    public HolderAndItemView(BaseChatItemView chatItemView, BaseChatHolder chatHolder,boolean isRight) {
        this.chatItemView = chatItemView;
        this.chatHolder = chatHolder;
        this.isRight=isRight;
    }
}
