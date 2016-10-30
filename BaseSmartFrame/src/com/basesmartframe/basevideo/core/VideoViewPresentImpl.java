package com.basesmartframe.basevideo.core;

import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;

import com.basesmartframe.basevideo.CustomVideoView;
import com.sf.loglib.L;

/**
 * Created by mac on 16/10/30.
 */

public class VideoViewPresentImpl implements VideoViewAbs.VideoViewPresent {
    private final String TAG=getClass().getName();
    private final VideoViewAbs.VideoViewCallback mCallback;
    private final CustomVideoView mCustomView;

    public VideoViewPresentImpl(CustomVideoView customVideoView,VideoViewAbs.VideoViewCallback mCallback) {
        this.mCallback = mCallback;
        this.mCustomView=customVideoView;
        registerVideoViewListener();
    }

    private void registerVideoViewListener() {
        mCustomView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                L.info(TAG, "onError,what: " + what + " extra: " + extra);
                if(mCallback!=null){
                    mCallback.onVideoError(what);
                }
                mp.reset();
                return false;
            }
        });

        mCustomView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                L.info(TAG, "onCompletion");
                if(mCallback!=null){
                    mCallback.onVideoComplete();
                }
            }
        });

        mCustomView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                L.info(TAG, "onInfo,what: " + what + " extra: " + extra);
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    if(mCallback!=null){
                        mCallback.onVideoLoading(what);
                    }
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    if(mCallback!=null){
                        mCallback.onPlaying();
                    }
                }

                return false;
            }
        });

        mCustomView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                L.info(TAG, "onPrepared");
                mp.start();
                if(mCallback!=null){
                    mCallback.onPlaying();
                }
            }
        });
        mCustomView.setOnPreparingListener(new CustomVideoView.OnPreparingListener() {
            @Override
            public void onPreparing(MediaPlayer mediaPlayer) {
                L.info(TAG, "onPreparing");
                if(mCallback!=null){
                    mCallback.onVideoLoading(0);
                }
            }
        });
        mCustomView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                L.info(TAG, "onBufferingUpdate percent: " + percent);
                if(mCallback!=null){
                    mCallback.onVideoBuffered(percent);
                }
            }
        });
    }

    @Override
    public void play(String url) {
        if(TextUtils.isEmpty(url)){
            return;
        }
        Uri uri = Uri.parse(url);
        mCustomView.setVideoURI(uri);
    }

    @Override
    public void pause() {
        if (mCustomView.canPause()) {
            mCustomView.pause();
            if(mCallback!=null){
                mCallback.onPauseCallback();
            }
        } else {
            L.error(TAG,"pause is wrong");
            if(mCallback!=null){
                mCallback.onPauseOrErrorState();
            }
        }
    }

    @Override
    public void resume() {
         if (mCustomView.isPaused()) {
             mCustomView.start();
             if (mCallback != null) {
                 mCallback.onPlaying();
             }
         }else {
             L.error(TAG,"restart is wrong");
             if(mCallback!=null){
                 mCallback.onPauseOrErrorState();
             }
         }
    }

    @Override
    public void seekTo(int millionSeconds) {
        if(millionSeconds<0){
            return;
        }
        int curPosition = mCustomView.getCurrentPosition();
        if (curPosition > millionSeconds && mCustomView.canSeekBackward()) {
            mCustomView.seekTo(millionSeconds);
        } else if (curPosition < millionSeconds && mCustomView.canSeekForward()) {
            mCustomView.seekTo(millionSeconds);
        } else {
            if(mCallback!=null){
                mCallback.onSeekError();
            }
        }
    }
}
