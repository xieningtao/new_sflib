package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.basesmartframe.R;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sf.utils.baseutil.SFBus;
import com.basesmartframe.basevideo.core.VideoShowManager;
import com.basesmartframe.basevideo.util.GuestureControl;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.basesmartframe.basevideo.util.ToggelSystemUIHelp;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFToast;

/**
 * Created by xieningtao on 15-4-28.
 */
public class SFVideoUIController implements SFVideoLifeCycle {

    public final String TAG = "SFVideoUIController";

    private Context mContext;

    private View mRootView;

    //other action
    private ImageView videoshow_pt_share;
    private ImageView videoshow_ls_share;
    private ImageView videoshow_pt_back;
    //    private CheckBox title_subscribe_iv;

    private TextView rate_tv;
    //show view
    private TextView curTime_tv;
    private TextView totalTime_tv;

    private boolean isFullMode = false;

    //gesture
    private GestureDetector detector;

    private Runnable preRunnable = null;

    private Handler hideControlHandler = new Handler();

    private TitleBottomViewToggle titleBottomViewToggle;

    private TitleBottomViewToggleWithPause titleBottomViewToggleWithPause;

    private GuestureControl.VideoGesture videoGesture;

    private VideoViewHolder mHolder;

    private final VideoShowManager mVideoShowManager;

    private final VideoZoomHelp mZoomHelp;

//    private final VideoShareHelp mShare;

    private String mUrl = "http://w2.dwstatic.com/8/5/1546/186570-102-1447226463.mp4";

//    private final TitleBottomViewToggle mToggle;

//    private final ActionTimeGapHelp mActionTimeGapHelp = new ActionTimeGapHelp();


    public SFVideoUIController(View rootView) {
        this.mRootView = rootView;
        mContext = rootView.getContext();
        mHolder = new VideoViewHolder(rootView);
        mVideoShowManager = new VideoShowManager(mHolder);
        mZoomHelp = new VideoZoomHelp(mContext, mHolder);
//        mShare = new VideoShareHelp(mContext, rootView);
//        mToggle = new TitleBottomViewToggle(mContext, mHolder);

        initView();
        initActionListener();
        registerVideoViewListener();
        mHolder.showPrepareView();
    }

    private void registerVideoViewListener() {
        mHolder.mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                L.info(TAG, "onError,what: " + what + " extra: " + extra);
                mHolder.showError(what);
                mp.reset();
                return false;
            }
        });

        mHolder.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                L.info(TAG, "onCompletion");
            }
        });

        mHolder.mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                L.info(TAG, "onInfo,what: " + what + " extra: " + extra);
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mHolder.showLoading();
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    mHolder.showPlaying();
                }
                return false;
            }
        });

        mHolder.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                L.info(TAG, "onPrepared");
                mp.start();
                mHolder.showPlaying();
            }
        });
        mHolder.mVideoView.setOnPreparingListener(new CustomVideoView.OnPreparingListener() {
            @Override
            public void onPreparing(MediaPlayer mediaPlayer) {
                L.info(TAG, "onPreparing");
                mHolder.showLoading();
            }
        });
        mHolder.mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                L.info(TAG, "onBufferingUpdate percent: " + percent);
            }
        });
    }

    private void initView() {

        //progress
        curTime_tv = (TextView) mRootView.findViewById(R.id.cur_tv);
        totalTime_tv = (TextView) mRootView.findViewById(R.id.total_tv);

        videoshow_pt_back = (ImageView) mRootView.findViewById(R.id.videoshow_pt_back);
        videoshow_ls_share = (ImageView) mRootView.findViewById(R.id.videoshow_ls_share);
        videoshow_pt_share = (ImageView) mRootView.findViewById(R.id.videoshow_pt_share);

    }

    public void setUrl(String url) {
        this.mUrl = url;
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
                    setOrientation(false);
                } else {
                    setOrientation(true);
                }
            }
        });

        //pause or resum
        mHolder.pause_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHolder.mVideoView.canPause()) {
                    mHolder.mVideoView.pause();
                    mHolder.showPause();
                } else if (mHolder.mVideoView.isPaused()) {
                    mHolder.mVideoView.start();
                    mHolder.showPlaying();
                } else {//TODO wrong state

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
                int curPosition = mHolder.mVideoView.getCurrentPosition();
                if (curPosition > seekPosition && mHolder.mVideoView.canSeekBackward()) {
                    mHolder.mVideoView.seekTo(seekPosition);
                } else if (curPosition < seekPosition && mHolder.mVideoView.canSeekForward()) {
                    mHolder.mVideoView.seekTo(seekPosition);
                } else {//TODO wrong view

                }
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
        videoshow_pt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                activity.finish();
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

    private void doPlay() {
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            play();
        } else {
            SFToast.showToast(R.string.net_unavailable);
        }
    }

    private void play() {
        if (!TextUtils.isEmpty(mUrl)) {
            Uri uri = Uri.parse(mUrl);
            mHolder.mVideoView.setVideoURI(uri);
        } else {
            //TODO show invalid url
        }
    }


    public CustomVideoView getmVideoView() {
        return mHolder.mVideoView;
    }


    public boolean updateProgress(int curMillis, int total) {
        if (curMillis < 0 || total < 0) {
            return false;
        }
        double pecentage = (curMillis * 1.0 / total) * 100;
        mHolder.seekBar.setProgress((int) pecentage);
        curTime_tv.setText(TimeUtil.getMSFormatTime(curMillis) + "/");
        return true;
    }

    private void recycleControl() {
        if (preRunnable != null) {
            hideControlHandler.removeCallbacks(preRunnable);
        }
        preRunnable = new Runnable() {//hide view
            @Override
            public void run() {
                if (isControlShow()) {
                    toggle(false);
                }
                preRunnable = null;
            }
        };
        hideControlHandler.postDelayed(preRunnable, 5000);
    }

    private void toggleTitleView(boolean show) {
        if (!show) {
            if (isFullMode) {
                videoshow_ls_share.setVisibility(View.GONE);
                mHolder.title_ll.setVisibility(View.GONE);
            } else {
                videoshow_pt_share.setVisibility(View.GONE);
            }
        } else {
            if (isFullMode) {
                videoshow_ls_share.setVisibility(View.VISIBLE);
                mHolder.title_ll.setVisibility(View.VISIBLE);
            } else {
                videoshow_pt_share.setVisibility(View.VISIBLE);
            }
        }
    }

    private void toggleBottomView(boolean show) {
        if (!show) {
            mHolder.control_ll.setVisibility(View.GONE);
        } else {
            mHolder.control_ll.setVisibility(View.VISIBLE);
        }
    }

    private void toggleTopBottomView(boolean show) {
        L.info(this, "toggle topBottomView  show: " + show);
        cancelRecycle();
        toggleTitleView(show);
        toggleBottomView(show);
    }

    private void cancelRecycle() {
        if (preRunnable != null) {
            hideControlHandler.removeCallbacks(preRunnable);
        }
    }

    private boolean isControlShow() {
        return mHolder.control_ll.getVisibility() == View.VISIBLE ? true : false;
    }

    private boolean isTitleShow() {
        return mHolder.title_ll.getVisibility() == View.VISIBLE ? true : false;
    }


    private void toggleOtherView(boolean show) {

    }

    private boolean isFull() {
        return ToggelSystemUIHelp.isFull(mContext);
    }

    private void toggle(boolean show) {
        toggleTopBottomView(show);
        if (isFullMode) {
            ToggelSystemUIHelp.toggleScreenView(mContext, show);
        }
    }

    public void onConfigurationChanged(boolean fullMode) {
        L.info(this, "onConfigurationChanged: " + fullMode);
        isFullMode = fullMode;
        recycleControl();
        mZoomHelp.changeMode(fullMode);
    }

    public void setFullMode(boolean fullMode) {
        setOrientation(fullMode);
    }

    private void changeGestureEvent(boolean isFull) {
        if (isFull) {
            videoGesture.setEvent(titleBottomViewToggle);
        } else {
            videoGesture.setEvent(titleBottomViewToggleWithPause);
        }
    }

    public void setOrientation(boolean full) {
        final Activity activity = (Activity) mContext;
        if (full) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public boolean onBackPress() {
//        if (isFullMode) {
//            if (mShare.isShareViewShow() && !mShare.isShareViewAnimating()) {
//                mShare.dimissShareView();
//                return true;
//            } else {
//                setFullMode(false);
//                return true;
//            }
//        }
        return false;
    }


    private boolean doubleAction() {
//        if (mActionTimeGapHelp.isInActionGap(ActionTimeGapHelp.ACTION_1000)) {
//            L.info(TAG, "double action is in gap");
//            return true;
//        }
//        if (isFull()) {
//            setFullMode(false);
//        } else {
//            setFullMode(true);
//        }
        return false;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestory() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {
        SFBus.register(this);
    }

    @Override
    public void onPause() {
        SFBus.unregister(this);
    }


    class TitleBottomViewToggleWithPause implements GuestureControl.GestureControlEvent {

        @Override
        public boolean onSingleEvent(MotionEvent e) {
            if (mVideoShowManager.getCurVideoState() == mVideoShowManager.mPauseState) {
                mVideoShowManager.resume();
            } else {
                mVideoShowManager.play(mUrl);
            }
//            if (VideoViewReceiver.VideoViewState.PAUSE == state) {
//                toggle(true);
//            } else {
//                toggle(!isControlShow());
//            }
//            recycleControl();
            return true;
        }

        @Override
        public boolean onDoubleEevent(MotionEvent e) {
            return doubleAction();
        }


        @Override
        public boolean onScroll(int distance, MotionEvent e1, MotionEvent e2) {
            return false;
        }
    }
}
