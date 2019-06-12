package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;

import com.example.sfchat.item.chatbean.SFGif;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.GifViewHolder;
import com.sf.loglib.L;
import com.sf.utils.baseutil.GsonUtil;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by NetEase on 2016/11/28 0028.
 */

public class GifItemView extends BaseChatItemView<SFMsg> {
//    private DisplayImageOptions mOptions;

    public GifItemView(Context context) {
        super(context);
    }

    public GifItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

//    public void setOptions(DisplayImageOptions options) {
//        mOptions = options;
//    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if (data == null || TextUtils.isEmpty(data.getContent())) return;
        if (baseChatHolder instanceof GifViewHolder) {
            final GifViewHolder viewHolder = (GifViewHolder) baseChatHolder;
            SFGif gif = GsonUtil.parse(data.getContent(), SFGif.class);
//            ImageLoader.getInstance().loadImage(gif.getUrl(), mOptions, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String imageUri, View view) {
//
//                }
//
//                @Override
//                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    try {
//                        viewHolder.mGifTextView.setImageDrawable(new GifDrawable(BitmapHelp.bmpToByteArray(loadedImage, true)));
//                    } catch (IOException e) {
//                        L.e(TAG, "exception: " + e);
//                    }
//                }
//
//                @Override
//                public void onLoadingCancelled(String imageUri, View view) {
//
//                }
//            });
            try {
                String path = gif.getUrl().replace("file://", "");
                File file = new File(path);
                if (file != null && file.exists()) {
                    viewHolder.mGifTextView.setImageDrawable(new GifDrawable(file));
                }
            } catch (IOException e) {
                L.error(TAG, "exception: " + e);
            }
        }
    }
}
