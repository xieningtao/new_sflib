package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class PhotoViewHolder extends UserAndIndicatorViewHolder {
    public final ImageView mBubbleIv;
    public final ProgressBar mProgressBar;
    public final TextView mProgressBarTv;
    public final View mPhotoContainer;
    public PhotoViewHolder(View rootView) {
        super(rootView);
        mBubbleIv = (ImageView) rootView.findViewById(R.id.image);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBarTv = (TextView) rootView.findViewById(R.id.progressText);
        mPhotoContainer=rootView.findViewById(R.id.photo_container);
    }
}
