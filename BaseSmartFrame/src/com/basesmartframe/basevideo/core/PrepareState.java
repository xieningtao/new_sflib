package com.basesmartframe.basevideo.core;

import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.basesmartframe.R;
import com.basesmartframe.basevideo.CustomVideoView;
import com.basesmartframe.basevideo.VideoViewHolder;
import com.sf.loglib.L;

/**
 * Created by xieningtao on 15-11-15.
 */
public class PrepareState extends VideoState {


    public PrepareState(VideoShowManager videoShowManager, VideoViewHolder holder) {
        super(videoShowManager, holder);
    }

    /**
     * next state is loading then playing state
     */
    @Override
    public void reset() {

    }

    @Override
    public void toError() {

    }

    @Override
    protected void updateViewState() {
        mHolder.error_ll.setVisibility(View.GONE);
        mHolder.video_loading.setVisibility(View.GONE);
        mHolder.video_play.setVisibility(View.VISIBLE);
        mHolder.videoshow_cover.setVisibility(View.VISIBLE);
        mHolder.control_ll.setVisibility(View.VISIBLE);
        mHolder.pause_iv.setImageResource(R.drawable.videoshow_pause_normal);
    }

    @Override
    public void play(final String url) {
        if (!TextUtils.isEmpty(url)) {
            CustomVideoView videoView = mHolder.mVideoView;
            initListener();
            Uri curUri = Uri.parse(url);
            videoView.setVideoURI(curUri);
        } else {
            //TODO error
            changeToErrorState();
        }
    }

    private void initListener() {
        final CustomVideoView videoView = mHolder.mVideoView;
        if (null != videoView) {

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    L.info(TAG, "method->onPrepared");
                    registetMediaListener(mediaPlayer);
                    videoView.start();
                    changeToPlayingState();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    L.info(TAG, "method->onCompletion");
                }
            });

            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    L.error(TAG, "method->onError params->i:" + i + " i2: " + i2);
                    changeToErrorState();
                    return true;
                }
            });

            videoView.setOnPreparingListener(new CustomVideoView.OnPreparingListener() {
                @Override
                public void onPreparing(MediaPlayer mediaPlayer) {
                    changeToLoadingState();
                }
            });
        }
    }

    private void registetMediaListener(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    L.info(TAG, "method->onBufferingUpdate,params->percent: " + percent);

                }
            });

            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    L.info(TAG, "method->onSeekComplete");
                    changeToPlayingState();
                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                    L.info(TAG, "method->onInfo,params-> i: " + i + " i2: " + i2);
                    if (i == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        changeToLoadingState();
                        return true;
                    } else if (i == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        changeToPlayingState();
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
