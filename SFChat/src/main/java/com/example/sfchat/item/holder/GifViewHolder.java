package com.example.sfchat.item.holder;

import android.view.View;

import com.example.sfchat.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by mac on 16/11/27.
 */

public class GifViewHolder extends UserAndIndicatorViewHolder {
    public final GifImageView mGifTextView;
    public GifViewHolder(View rootView) {
        super(rootView);
        mGifTextView= rootView.findViewById(R.id.gif_view);
    }
}
