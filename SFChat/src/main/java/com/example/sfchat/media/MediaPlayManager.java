package com.example.sfchat.media;

import android.media.MediaPlayer;
import android.text.TextUtils;

import com.sf.loglib.L;

import java.io.IOException;

/**
 * Created by NetEase on 2016/8/30 0030.
 */
public class MediaPlayManager {
    private final String TAG = getClass().getName();
    //语音操作对象
    private MediaPlayer mPlayer;

    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;
    private MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;

    private static MediaPlayManager mediaPlayManager = new MediaPlayManager();


    public static MediaPlayManager getInstance() {
        return mediaPlayManager;
    }

    private MediaPlayManager() {
        mPlayer = new MediaPlayer();
        initListener();
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        mOnPreparedListener = onPreparedListener;
    }

    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener onInfoListener) {
        mOnInfoListener = onInfoListener;
    }

    public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        mOnSeekCompleteListener = onSeekCompleteListener;
    }

    private void initListener() {
        mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                L.info(TAG, TAG + ".initListener.OnBufferingUpdateListener percent: " + percent);
                if (mOnBufferingUpdateListener != null) {
                    mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                L.info(TAG, TAG + ".initListener.onCompletion");
                if (mOnCompletionListener != null) {
                    mOnCompletionListener.onCompletion(mp);
                }
            }
        });

        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                L.info(TAG, TAG + ".initListener.onError what: " + what + " extra: " + extra);
                if (mOnErrorListener != null) {
                    return mOnErrorListener.onError(mp, what, extra);
                }
                stopPlay();
                return false;
            }
        });

        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                L.info(TAG, TAG + ".initListener.onSeekComplete");
                if (mOnSeekCompleteListener != null) {
                    mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        });

        mPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                L.info(TAG, TAG + ".initListener.onInfo what: " + what + " extra: " + extra);
                if (mOnInfoListener != null) {
                    return mOnInfoListener.onInfo(mp, what, extra);
                }
                return false;
            }
        });
    }

    //暂停播放
    public void pause() {
        mPlayer.pause();
    }

    //停止播放
    private void stopPlay() {
        mPlayer.stop();
        mPlayer.release();
    }

    public MediaPlayer getPlayer() {
        return mPlayer;
    }

    //开始播放
    private boolean startPlay(String path) {
        L.info(TAG, TAG + ".startPlay,path: " + path);
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    L.info(TAG, TAG + ".startPlay.onPrepared");
                    mp.start();
                    if (mOnPreparedListener != null) {
                        mOnPreparedListener.onPrepared(mp);
                    }
                }
            });
        } catch (IOException e) {
            L.error(TAG, TAG + ".startPlay exception: " + e);
            return false;
        }
        return true;
    }
}
