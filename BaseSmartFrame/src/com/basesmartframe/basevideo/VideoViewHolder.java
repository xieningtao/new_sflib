package com.basesmartframe.basevideo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.basesmartframe.R;

/**
 * Created by xieningtao on 15-11-15.
 */
public class VideoViewHolder {
    //control
    public final ImageView video_play;
    public final ImageView zoom_iv;
    public final ImageView pause_iv;
    public final SeekBar seekBar;

    //layout
    public final View video_loading;
    public final View error_ll;
    public final Button reload_bt;
    public final View control_ll;
    public final View title_ll;
    public final View black_view;

    public final CustomVideoView mVideoView;

    public final ImageView videoshow_cover;


    public final TextView fast_control_tv;

    public final View mRootView;

    public VideoViewHolder(View view) {
        this.mRootView = view;
        //control view
        video_play = (ImageView) view.findViewById(R.id.video_play);
        zoom_iv = (ImageView) view.findViewById(R.id.zoomout_iv);
        pause_iv = (ImageView) view.findViewById(R.id.pause_iv);
        seekBar = (SeekBar) view.findViewById(R.id.video_progress);

        //tips layout
        control_ll = view.findViewById(R.id.control_ll);
        title_ll = view.findViewById(R.id.title_ll);
        error_ll = view.findViewById(R.id.video_error_ll);
        video_loading = view.findViewById(R.id.video_loading);
        reload_bt = (Button) view.findViewById(R.id.reload_bt);
        black_view = view.findViewById(R.id.black_view);

        //video view
        mVideoView = (CustomVideoView) view.findViewById(R.id.video_view);

        videoshow_cover = (ImageView) view.findViewById(R.id.videoshow_cover);
        fast_control_tv = (TextView) view.findViewById(R.id.fast_control_tv);
    }

    public void showLoading() {
        video_loading.setVisibility(View.VISIBLE);
        video_play.setVisibility(View.GONE);
        error_ll.setVisibility(View.GONE);
        videoshow_cover.setVisibility(View.GONE);
    }

    public void showPlaying() {
        video_loading.setVisibility(View.GONE);
        video_play.setVisibility(View.GONE);
        error_ll.setVisibility(View.GONE);
        control_ll.setVisibility(View.VISIBLE);
        videoshow_cover.setVisibility(View.GONE);
        pause_iv.setImageResource(R.drawable.videoshow_pause_normal);
    }

    public void showPause() {
        video_play.setVisibility(View.VISIBLE);
        video_loading.setVisibility(View.GONE);
        pause_iv.setImageResource(R.drawable.videoshow_miniplay_normal);
    }

    public void showError(int errorType) {
        video_loading.setVisibility(View.GONE);
        video_play.setVisibility(View.GONE);
        control_ll.setVisibility(View.GONE);
        videoshow_cover.setVisibility(View.GONE);
        error_ll.setVisibility(View.VISIBLE);
    }

    public void showSeek() {

    }

    public void showComplete() {

    }

    public void showPrepareView() {
        error_ll.setVisibility(View.GONE);
        video_loading.setVisibility(View.GONE);
        video_play.setVisibility(View.VISIBLE);
        videoshow_cover.setVisibility(View.VISIBLE);
        control_ll.setVisibility(View.GONE);
        pause_iv.setImageResource(R.drawable.videoshow_pause_normal);
    }
}
