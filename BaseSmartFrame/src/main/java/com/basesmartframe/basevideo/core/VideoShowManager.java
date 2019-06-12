package com.basesmartframe.basevideo.core;

import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
public class VideoShowManager {


    public final VideoState mPlayingState;
    public final VideoState mPrepareState;
    public final VideoState mPauseState;
    public final VideoState mLoadingState;

    public final VideoState mErrorState;

    private VideoState mVideoState;


    public VideoShowManager(VideoViewHolder holder) {
        mPlayingState = new PlayingState(this, holder);
        mPrepareState = new PrepareState(this, holder);
        mPauseState = new PauseState(this, holder);
        mLoadingState = new LoadingState(this, holder);
        mErrorState = new ErrorState(this, holder);

        mVideoState = mPrepareState;
        mVideoState.updateViewState();
    }


    public void setVideoState(VideoState state) {
        this.mVideoState = state;
    }

    public VideoState getCurVideoState() {
        return mVideoState;
    }

    public void play(String url) {
        mVideoState.play(url);
    }

    public void pause() {
        mVideoState.pause();
    }

    public void resume() {
        mVideoState.resume();
    }

    public void seek(int millisec) {
        mVideoState.seek(millisec);
    }
}
