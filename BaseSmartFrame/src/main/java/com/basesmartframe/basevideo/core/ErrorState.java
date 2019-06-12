package com.basesmartframe.basevideo.core;

import android.view.View;

import com.basesmartframe.basevideo.CustomVideoView;
import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
public class ErrorState extends VideoState {
    public ErrorState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        super(videoShowManager, holder);
    }

    @Override
    protected void updateViewState() {
        mHolder.video_loading.setVisibility(View.GONE);
        mHolder.video_play.setVisibility(View.GONE);
        mHolder.control_ll.setVisibility(View.GONE);
        mHolder.videoshow_cover.setVisibility(View.GONE);
        mHolder.error_ll.setVisibility(View.VISIBLE);

        release();
    }

    private void release() {
        CustomVideoView videoView = mHolder.mVideoView;
        videoView.stopPlayback();
    }

    @Override
    public void play(String url) {
        mVideoShowManager.mPrepareState.play(url);
    }
}
