package com.basesmartframe.basevideo;

import android.media.MediaPlayer;

import com.basesmartframe.basevideo.core.VideoViewAbs;

/**
 * Created by mac on 16/10/30.
 */

public class DefaultVideoViewController implements VideoViewAbs.VideoViewCallback {
    private final  VideoViewHolder mVidewHolder;

    public DefaultVideoViewController(VideoViewHolder mVidewHolder) {
        this.mVidewHolder = mVidewHolder;
    }

    @Override
    public void onPlaying() {
        mVidewHolder.showPlaying();
    }

    @Override
    public void onPauseCallback() {
            mVidewHolder.showPause();
    }


    @Override
    public void onVideoLoading(int what) {
            mVidewHolder.showLoading();
    }

    @Override
    public void onVideoError(int what) {
            mVidewHolder.showError(what);
    }

    @Override
    public void onVideoComplete() {
            mVidewHolder.showComplete();
    }

    @Override
    public void onVideoBuffered(int percent) {

    }

    @Override
    public void onPauseOrErrorState() {
            mVidewHolder.showError(0);
    }

    @Override
    public void onSeekError() {
            mVidewHolder.showError(0);
    }

    @Override
    public void onPlayUrlError() {
        mVidewHolder.showError(0);
    }
}
