package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.TextView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class TxtViewHolder extends UserAndIndicatorViewHolder {
    public final TextView mContentTv;

    public TxtViewHolder(View rootView) {
        super(rootView);
        mContentTv = rootView.findViewById(R.id.content_text);
    }
}
