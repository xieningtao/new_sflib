package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.basesmartframe.R;
import com.basesmartframe.basevideo.core.VideoViewAbs;
import com.basesmartframe.basevideo.core.VideoViewPresentImpl;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.utils.ThreadHelp;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sf.utils.baseutil.SFToast;
import com.sf.utils.baseutil.SystemUIWHHelp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 16/10/30.
 */

public class SFDefaultVideoPlayer extends FrameLayout {

    private final String TAG = getClass().getName();
    private View mRootView;
    private VideoViewAbs.VideoViewPresent mPresent;
    private CustomVideoView mVideoView;
    private VideoViewHolder mHolder;
    private VideoZoomHelp mZoomHelp;
    private String mUrl;

    private Timer mTimer;

    public SFDefaultVideoPlayer(Context context) {
        this(context, null);
    }

    public SFDefaultVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void loadCover(String url) {
        ImageLoader.getInstance().displayImage(url, mHolder.mCover);
    }

    private void init() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.videoviewui_layout, this);
        mVideoView = (CustomVideoView) mRootView.findViewById(R.id.video_view);
        mHolder = new VideoViewHolder(mRootView);
        initActionListener();
        VideoViewAbs.VideoViewCallback videoViewCallback = new DefaultVideoViewController(mHolder);
        mPresent = new VideoViewPresentImpl(mHolder.mVideoView, videoViewCallback);
        mZoomHelp = new VideoZoomHelp(getContext(), mHolder);
    }

    private void initActionListener() {
        //play
        mHolder.video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPlay();
            }
        });

        //replaly
        mHolder.reload_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPlay();
            }
        });
        //zoom in or out
        mHolder.zoom_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFull()) {
                    doOrientationRequest(false);
                } else {
                    doOrientationRequest(true);
                }
            }
        });

        //pause or resum
        mHolder.pause_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null || "resume".equals(v.getTag())) {
                    mPresent.pause();
                    v.setTag("pause");
                } else {
                    mPresent.resume();
                    v.setTag("resume");
                }
            }
        });

        //seek
        mHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                int seekPosition = progress * mHolder.mVideoView.getDuration() / 100;
                mPresent.seekTo(seekPosition);
            }
        });

//        mToggle.setSeekEvent(new TitleBottomViewToggle.ScrollSeekEvent() {
//            @Override
//            public void onFinished(int seekPosition) {
//                mVideoShowManager.seek(seekPosition);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
        //back press
        mHolder.mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFull()) {
                    doOrientationRequest(false);
                } else {
                    Activity activity = (Activity) getContext();
                    activity.finish();
                }

            }
        });

        //share
//        videoshow_pt_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mShare.showPortraitDialog();
//            }
//        });
//
//        videoshow_ls_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggle(false);
//                mShare.showLandscapeDialog();
//            }
//        });
    }

    public void doOrientationRequest(boolean full) {
        final Activity activity = (Activity) getContext();
        if (full) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private boolean isFull() {
        int width = SystemUIWHHelp.getScreenRealWidth((Activity) getContext());
        int height = SystemUIWHHelp.getScreenRealHeight((Activity) getContext());
        if (width > height) {
            return true;
        }
        return false;
    }

    public void doPlay() {
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            play();
        } else {
            SFToast.showToast(R.string.net_unavailable);
        }
    }

    private void play() {
        mPresent.play(mUrl);
    }

    public void setVideoViewWH(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mVideoView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        int curMillis = mHolder.mVideoView.getCurrentPosition();
                        int total = mHolder.mVideoView.getDuration();
                        double pecentage = (curMillis * 1.0 / total) * 100;
                        mHolder.seekBar.setProgress((int) pecentage);

                        //update text
                        mHolder.curTime_tv.setText(TimeUtil.getMSFormatTime(curMillis) + "/");
                        mHolder.totalTime_tv.setText(TimeUtil.getMSFormatTime(total));
                    }
                });

            }
        }, 0,1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimer.cancel();
        mTimer=null;
    }

    public void onConfigurationChanged(boolean fullMode) {
        mZoomHelp.changeMode(fullMode);
    }
}
