package com.example.sfchat.item.view;

import android.content.Context;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class ChatItemViewFactory {

    public static BaseChatItemView createLocationItemView(Context context) {
        ChatItemViewWithUser user = new ChatItemViewWithUser(context);
        ChatItemViewWithIndicator indicator = new ChatItemViewWithIndicator(context, user);
        LocationItemView locationItemView = new LocationItemView(context, indicator);
        return locationItemView;
    }
}
