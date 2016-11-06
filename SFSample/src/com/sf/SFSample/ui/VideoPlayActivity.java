package com.sf.SFSample.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.basevideo.SFDefaultVideoPlayer;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.NYFragmentRecommendVideo;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SystemUIWHHelp;

/**
 * Created by xieningtao on 15-11-16.
 */
public class VideoPlayActivity extends BaseActivity implements NYFragmentRecommendVideo.OnVideoPlayItemClick{

    @Override
    public void onVideoPlayClick(String videoUrl, String coverUrl) {
        if(mVideoViewUI!=null){
            mVideoViewUI.loadCover(coverUrl);
            mVideoViewUI.setUrl(videoUrl);
            mVideoViewUI.doPlay();
        }
    }


    private SFDefaultVideoPlayer mVideoViewUI;

    private final String TAG = "CustomVideoView";
    public final static String VIDEO_URL = "video_url";
    public final static String VIDEO_COVER="video_cover";
    private String mUrl;

    public static void jump2VideoPlay(Context context, String url,String coverUrl) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO_URL, url);
        intent.putExtra(VIDEO_COVER,coverUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoshow);
        mVideoViewUI = (SFDefaultVideoPlayer) findViewById(R.id.videoui_view);
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(VIDEO_URL);
            String video_cover=intent.getStringExtra(VIDEO_COVER);
            mVideoViewUI.loadCover(video_cover);
            mVideoViewUI.setUrl(mUrl);
        }
        mVideoViewUI.setVideoViewWH(getWidth(),getHeight());
        L.info(TAG, "invoke updateVideoPlayerScale");
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

    }

    @Override
    protected void onPause() {
        super.onPause();

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
