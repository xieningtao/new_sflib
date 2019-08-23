package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.ImageView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class UserViewHolder extends BaseChatHolder {
    public final ImageView mAvatar;


    public UserViewHolder(View rootView) {
        super(rootView);
        mAvatar = rootView.findViewById(R.id.user_iv);
    }

}
