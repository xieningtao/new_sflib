package com.basesmartframe.basevideo.core;

/**
 * Created by mac on 16/10/30.
 */

public class VideoViewAbs {

    public interface VideoViewPresent{

        void play(String url);

        void pause();

        void resume();

        void seekTo(int millionSeconds);
    }

    public interface VideoViewCallback{
        void onPlaying();
        void onPauseCallback();

        void onVideoLoading(int what);

        void onVideoError(int what);

        void onVideoComplete();

        void onVideoBuffered(int percent);

        void onPauseOrErrorState();

        void onSeekError();

        void onPlayUrlError();
    }
}
