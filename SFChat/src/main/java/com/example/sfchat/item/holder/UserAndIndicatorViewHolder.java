package com.example.sfchat.item.holder;

import android.view.View;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class UserAndIndicatorViewHolder extends BaseChatHolder {
    public final UserViewHolder mUserViewHolder;
    public final IndicatorViewHolder mIndicatorViewHolder;

    public UserAndIndicatorViewHolder(View rootView) {
        super(rootView);
        mUserViewHolder = new UserViewHolder(rootView);
        mIndicatorViewHolder = new IndicatorViewHolder(rootView);
    }
}
