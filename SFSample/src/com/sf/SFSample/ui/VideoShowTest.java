package com.sf.SFSample.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseutil.SystemUIWHHelp;
import com.basesmartframe.basevideo.VideoViewUI;
import com.sf.loglib.L;
import com.sf.SFSample.R;

/**
 * Created by xieningtao on 15-11-16.
 */
public class VideoShowTest extends BaseActivity {

    private VideoViewUI mVideoViewUI;

    private final String TAG = "CustomVideoView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoshow);
        mVideoViewUI = (VideoViewUI) findViewById(R.id.videoui_view);

//        mVideoViewUI.setScaleType(VideoViewUI.ScaleType.FitScale);
//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getWidth(), getHeight());
                mVideoViewUI.setVideoViewLayoutParams(layoutParams);
                L.info(TAG, "invoke updateVideoPlayerScale");
            }
        }, 1000);

    }

    private int getWidth() {
        return SystemUIWHHelp.getScreenRealWidth(this);
    }

    private int getHeight() {
        return getWidth() * 9 / 16;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoViewUI.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoViewUI.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mVideoViewUI.onConfigurationChanged(true);
        } else {
            mVideoViewUI.onConfigurationChanged(false);
        }
    }
}
