package com.basesmartframe.basevideo.core;

import android.view.View;

import com.basesmartframe.R;
import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
public class PauseState extends VideoState {


    public PauseState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        super(videoShowManager, holder);
    }

    @Override
    protected void updateViewState() {
        mHolder.video_play.setVisibility(View.VISIBLE);
        mHolder.video_loading.setVisibility(View.GONE);
        mHolder.pause_iv.setImageResource(R.drawable.videoshow_miniplay_normal);
    }

    @Override
    public void resume() {
        mHolder.mVideoView.start();
        changeToPlayingState();
    }
}
