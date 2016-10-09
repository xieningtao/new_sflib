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
public class SFVideoGroupView extends FrameLayout implements SFVideoLifeCycle {

    public static enum ScaleType {
        OriginScale, FitScale
    }

    private final String TAG = getClass().getName();
    private View mRootView;
    private SFVideoUIController mVideoViewUIHelp;
    private CustomVideoView mVideoView;

    public SFVideoGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.videoviewui_layout, this);
        initView();
    }

    public SFVideoGroupView(Context context) {
        this(context, null);
    }


    public void setUrl(String url) {
        mVideoViewUIHelp.setUrl(url);
    }

    private void initView() {
        mVideoView = (CustomVideoView) mRootView.findViewById(R.id.video_view);
        mVideoViewUIHelp = new SFVideoUIController(mRootView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
