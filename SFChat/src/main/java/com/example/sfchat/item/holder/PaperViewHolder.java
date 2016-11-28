package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.ImageView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/28 0028.
 */

public class PaperViewHolder extends UserAndIndicatorViewHolder{
    public final ImageView mPaperIv;
    public final View mContainerView;
    public PaperViewHolder(View rootView) {
        super(rootView);
        mPaperIv= (ImageView) rootView.findViewById(R.id.gif_tags_image);
        mContainerView=rootView.findViewById(R.id.paper_container);
    }
}
