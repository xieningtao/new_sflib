package com.basesmartframe.basevideo.core;

import com.basesmartframe.basevideo.VideoViewHolder;

/**
 * Created by xieningtao on 15-11-15.
 */
abstract public class VideoState implements State {
    protected final VideoShowManager mVideoShowManager;
    protected final VideoViewHolder mHolder;
    protected final String TAG = getClass().getName();

    public VideoState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        this.mVideoShowManager = videoShowManager;
        this.mHolder = holder;
    }

    abstract protected void updateViewState();


    protected void changeToErrorState() {
        mVideoShowManager.setVideoState(mVideoShowManager.mErrorState);
        mVideoShowManager.mErrorState.updateViewState();
    }

    protected void changeToLoadingState() {
        mVideoShowManager.setVideoState(mVideoShowManager.mLoadingState);
        mVideoShowManager.mLoadingState.updateViewState();
    }

    protected void changeToPlayingState() {
        mVideoShowManager.setVideoState(mVideoShowManager.mPlayingState);
        mVideoShowManager.mPlayingState.updateViewState();
    }

    @Override
    public void play(String url) {
        throw new IllegalVideoState("illegal playing state");
    }

    @Override
    public void toError() {
        throw new IllegalVideoState("illegal error state");
    }

    @Override
    public void reset() {
        throw new IllegalVideoState("illegal prepare state");
    }

    @Override
    public void pause() {
        throw new IllegalVideoState("illegal pause state");
    }

    @Override
    public void resume() {
        throw new IllegalVideoState("illegal resume state");
    }

    @Override
    public void seek(int millisec) {
        throw new IllegalVideoState("illegal seek state");
    }

    @Override
    public void toLoading() {
        throw new IllegalVideoState("illegal loading state");
    }
}
