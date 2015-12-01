package com.sf.yysdkdemo.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by xieningtao on 15-11-6.
 */
public class YYVideoView extends FrameLayout implements LifeCycle {
    private GLVideoPlayer mVideoPlayer;

    public YYVideoView(Context context) {
        super(context);
        init(context);
    }

    public YYVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YYVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mVideoPlayer = new GLVideoPlayer(context, params);
//        mVideoPlayer.setVideoScaleType(Image.ScaleType.Fit);
//        addView(mVideoPlayer.getView());


//        YGLVideoView videoView = (YGLVideoView) findViewById(R.id.yglvideoview);
//        mVideoPlayer = new GLVideoPlayer(videoView);
//        mVideoPlayer.setVideoScaleType(Image.ScaleType.Fit);
    }

    @Override
    public void onStart() {
//        mVideoPlayer.onStart();
    }

    @Override
    public void onStop() {
//        mVideoPlayer.onStop();
    }
}
