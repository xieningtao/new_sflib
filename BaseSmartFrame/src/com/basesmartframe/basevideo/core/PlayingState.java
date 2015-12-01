package com.basesmartframe.basevideo.core;

import android.view.View;

import com.basesmartframe.basevideo.CustomVideoView;
import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
public class PlayingState extends VideoState {


    public PlayingState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        super(videoShowManager, holder);
    }

    @Override
    protected void updateViewState() {
        mHolder.video_loading.setVisibility(View.GONE);
        mHolder.video_play.setVisibility(View.GONE);
        mHolder.error_ll.setVisibility(View.GONE);
        mHolder.videoshow_cover.setVisibility(View.GONE);
    }

    @Override
    public void pause() {
        mVideoShowManager.setVideoState(mVideoShowManager.mPauseState);
        mVideoShowManager.mPauseState.updateViewState();
    }

    @Override
    public void seek(int millisec) {
        if (millisec < 0) millisec = 0;
        final CustomVideoView videoView = mHolder.mVideoView;
        int duration = videoView.getDuration();
        if (millisec > duration) millisec = duration;
        videoView.seekTo(millisec);
        changeToLoadingState();
    }
}
