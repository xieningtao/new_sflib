package com.basesmartframe.basevideo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.basesmartframe.R;

/**
 * Created by xieningtao on 15-4-27.
 */
public class VideoViewUI extends FrameLayout implements VideoShowLifeCycle {

    public static enum ScaleType {
        OriginScale, FitScale
    }

    private final String TAG = getClass().getName();

    private View mRootView;
    private VideoShowInteractHelp mVideoViewUIHelp;

    private CustomVideoView mVideoView;


    public VideoViewUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.videoviewui_layout, this);
        initView();
    }

    public VideoViewUI(Context context) {
        this(context, null);
    }


    private void initView() {
        mVideoView = (CustomVideoView) mRootView.findViewById(R.id.video_view);
        mVideoViewUIHelp = new VideoShowInteractHelp(mRootView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        L.info("VideoViewUI", "onMeasure(" + MeasureSpec.toString(widthMeasureSpec) + ", "
//                + MeasureSpec.toString(heightMeasureSpec) + ")");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setVideoViewLayoutParams(FrameLayout.LayoutParams layoutParams) {
        mVideoView.setLayoutParams(layoutParams);
    }

    public void onConfigurationChanged(boolean fullMode) {
        mVideoViewUIHelp.onConfigurationChanged(fullMode);
    }

    public boolean onBackPress() {
        return mVideoViewUIHelp.onBackPress();
    }

    @Override
    public void onCreate() {
        mVideoViewUIHelp.onCreate();
    }

    @Override
    public void onDestory() {
        mVideoViewUIHelp.onDestory();
    }

    @Override
    public void onStart() {
        mVideoViewUIHelp.onStart();
    }

    @Override
    public void onStop() {
        mVideoViewUIHelp.onStop();
    }

    @Override
    public void onResume() {
        mVideoViewUIHelp.onResume();
    }

    @Override
    public void onPause() {
        mVideoViewUIHelp.onPause();
    }
}
