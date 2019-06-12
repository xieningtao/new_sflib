package com.basesmartframe.basevideo.core;

import android.view.View;

import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
public class LoadingState extends VideoState {

    public LoadingState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        super(videoShowManager, holder);
    }

    @Override
    protected void updateViewState() {
        mHolder.video_loading.setVisibility(View.VISIBLE);
        mHolder.video_play.setVisibility(View.GONE);
        mHolder.error_ll.setVisibility(View.GONE);
        mHolder.videoshow_cover.setVisibility(View.GONE);
    }


}
