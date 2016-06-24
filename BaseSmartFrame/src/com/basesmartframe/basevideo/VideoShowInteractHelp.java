package com.basesmartframe.basevideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basesmartframe.R;
import com.basesmartframe.baseutil.NetWorkManagerUtil;
import com.basesmartframe.baseutil.SFBus;
import com.basesmartframe.basevideo.core.VideoShowManager;
import com.basesmartframe.basevideo.util.ActionTimeGapHelp;
import com.basesmartframe.basevideo.util.GuestureControl;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.basesmartframe.basevideo.util.ToggelSystemUIHelp;
import com.sf.loglib.L;

/**
 * Created by xieningtao on 15-4-28.
 */
public class VideoShowInteractHelp implements VideoShowLifeCycle {

    public final String TAG = "VideoShowInteractHelp";

    private VideoViewReceiver.VideoViewState state;

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
    private GestureDetectorCompat detector;

    private Runnable preRunnable = null;

    private Handler hideControlHandler = new Handler();

    private TitleBottomViewToggle titleBottomViewToggle;

    private TitleBottomViewToggleWithPause titleBottomViewToggleWithPause;

    private GuestureControl.VideoGesture videoGesture;

    private VideoViewHolder mHolder;

    private final VideoShowManager mVideoShowManager;

    private final VideoZoomHelp mZoomHelp;

    private final VideoShareHelp mShare;

//    private String mUrl = "http://w2.dwstatic.com/2/8/1519/116567-99-1430820950.mp4";
    private String mUrl = "http://w2.dwstatic.com/8/5/1546/186570-102-1447226463.mp4";

    private final TitleBottomViewToggle mToggle;


    public VideoShowInteractHelp(View rootView) {
        this.mRootView = rootView;
        mContext = rootView.getContext();
        mHolder = new VideoViewHolder(rootView);
        mVideoShowManager = new VideoShowManager(mHolder);
        mZoomHelp = new VideoZoomHelp(mContext, mHolder);
        mShare = new VideoShareHelp(mContext, rootView);

        mToggle = new TitleBottomViewToggle(mContext, mHolder);

        initView();
        initListener();
    }

    private void initView() {

        //progress
        curTime_tv = (TextView) mRootView.findViewById(R.id.cur_tv);
        totalTime_tv = (TextView) mRootView.findViewById(R.id.total_tv);

        videoshow_pt_back = (ImageView) mRootView.findViewById(R.id.videoshow_pt_back);
        videoshow_ls_share = (ImageView) mRootView.findViewById(R.id.videoshow_ls_share);
        videoshow_pt_share = (ImageView) mRootView.findViewById(R.id.videoshow_pt_share);

    }

    private void initListener() {
        mHolder.video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoShowManager.getCurVideoState() == mVideoShowManager.mPauseState) {
                    mVideoShowManager.resume();
                } else {
                    mVideoShowManager.play(mUrl);
                }
            }
        });

        mHolder.reload_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkManagerUtil.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
                } else {
                    mVideoShowManager.play(mUrl);
                }
            }
        });
        mHolder.zoom_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFull()) {
                    mZoomHelp.changeMode(false);
                } else {
                    mZoomHelp.changeMode(true);
                }
            }
        });
        mHolder.pause_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoShowManager.getCurVideoState() == mVideoShowManager.mPlayingState) {
                    mVideoShowManager.pause();
                } else if (mVideoShowManager.getCurVideoState() == mVideoShowManager.mPauseState) {
                    mVideoShowManager.resume();
                }
            }
        });


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
                mVideoShowManager.seek(seekPosition);
//                else {
//                    int cur = mHolder.mVideoView.getCurrentPosition();
//                    int duration = mHolder.mVideoView.getDuration();
//                    if (cur < 0) {
//                        seekBar.setProgress(0);
//                    } else {
//                        int percentage = (int) ((cur * 1.0 / duration) * 100);
//                        if (percentage < 0) {
//                            percentage = 0;
//                        }
//                        seekBar.setProgress(percentage);
//                    }
//                }
            }
        });

        mToggle.setSeekEvent(new TitleBottomViewToggle.ScrollSeekEvent() {
            @Override
            public void onFinished(int seekPosition) {
                mVideoShowManager.seek(seekPosition);
            }

            @Override
            public void onStart() {

            }
        });

        videoshow_pt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                activity.finish();
            }
        });

        videoshow_pt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShare.showPortraitDialog();
            }
        });

        videoshow_ls_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(false);
                mShare.showLandscapeDialog();
            }
        });
    }

    private boolean canPause() {
        return VideoViewReceiver.VideoViewState.PLAY == state || VideoViewReceiver.VideoViewState.BUFFERED == state;
    }


    public void setState(VideoViewReceiver.VideoViewState state) {
        this.state = state;
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

    private boolean isPlayingState() {
        return state == VideoViewReceiver.VideoViewState.PLAY
                || state == VideoViewReceiver.VideoViewState.PAUSE
                || state == VideoViewReceiver.VideoViewState.SEEK
                || state == VideoViewReceiver.VideoViewState.BUFFERED;
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
        setOritation(fullMode);
    }

    private void changeGestureEvent(boolean isFull) {
        if (isFull) {
            videoGesture.setEvent(titleBottomViewToggle);
        } else {
            videoGesture.setEvent(titleBottomViewToggleWithPause);
        }
    }

    public void setOritation(boolean full) {
        final Activity activity = (Activity) mContext;
        if (full) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public boolean onBackPress() {
        if (isFullMode) {
            if (mShare.isShareViewShow() && !mShare.isShareViewAnimating()) {
                mShare.dimissShareView();
                return true;
            } else {
                setFullMode(false);
                return true;
            }
        }
        return false;
    }


    private boolean doubleAction() {
        if (ActionTimeGapHelp.getInstance().isInActionGap(ActionTimeGapHelp.ACTION_1000)) {
            L.info(TAG, "double action is in gap");
            return true;
        }
        if (isFull()) {
            setFullMode(false);
        } else {
            setFullMode(true);
        }
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

    public void onEvent(Integer a) {

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
