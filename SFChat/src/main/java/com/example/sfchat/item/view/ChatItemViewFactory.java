package com.example.sfchat.item.view;

import android.content.Context;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class ChatItemViewFactory {

    public static BaseChatItemView createLocationItemView(Context context) {
        LocationItemView locationItemView = new LocationItemView(context, createCommonItemview(context));
        return locationItemView;
    }

    public static BaseChatItemView createPhotoItemView(Context context) {
        PhotoItemView photoItemView = new PhotoItemView(context, createCommonItemview(context));
        return photoItemView;
    }

    public static BaseChatItemView createGifItemView(Context context) {
        GifItemView gifItemView = new GifItemView(context, createCommonItemview(context));
        return gifItemView;
    }

    public static BaseChatItemView createTxtItemView(Context context) {
        return new TxtItemView(context, createCommonItemview(context));
    }

    public static BaseChatItemView createAudioItemView(Context context) {
        return new AudioItemView(context, createCommonItemview(context));
    }

    public static BaseChatItemView createPaperItemView(Context context) {
        return new PaperItemView(context, createCommonItemview(context));
    }

    private static BaseChatItemView createCommonItemview(Context context) {
        ChatItemViewWithUser user = new ChatItemViewWithUser(context);
        ChatItemViewWithIndicator indicator = new ChatItemViewWithIndicator(context, user);
        return indicator;
    }
}
