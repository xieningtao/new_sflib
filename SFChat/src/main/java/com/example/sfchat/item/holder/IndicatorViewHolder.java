package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class IndicatorViewHolder extends BaseChatHolder {
    public final TextView mTime;
    public final ProgressBar mProgressBar;
    public final ImageView mErrorView;
    public final ImageView mUnreadView;

    public IndicatorViewHolder(View rootView) {
        super(rootView);
        mTime = (TextView) rootView.findViewById(R.id.time);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mErrorView = (ImageView) rootView.findViewById(R.id.sending_error);
        mUnreadView = (ImageView) rootView.findViewById(R.id.unread);
    }
}
