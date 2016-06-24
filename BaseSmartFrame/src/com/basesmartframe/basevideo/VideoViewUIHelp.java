//package com.basesmartframe.basevideo;
//
//import android.annotation.TargetApi;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.Surface;
//import android.view.View;
//import android.widget.Button;
//
//import com.basesmartframe.R;
//import com.basesmartframe.baseevent.GlobalEvent;
//import com.basesmartframe.baseutil.NetWorkManagerUtil;
//import com.basesmartframe.baseutil.SFBus;
//import com.basesmartframe.dialoglib.DialogFactory;
//import com.sf.loglib.L;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import de.greenrobot.event.EventBus;
//
///**
// * Created by xieningtao on 15-4-27.
// */
//public class VideoViewUIHelp implements VideoViewAction, VideoShowLifeCycle {
//
//    private VideoShowInteractHelp mStateHelp;
//    private VideoViewReceiver mReceiver;
//
//    private Context mContext;
//
//    private View mRootView;
//
//    //    private String uri;
////    private EnumMap<VideoShowDetailActivity.VideoShowRate, Model.VideoDefinition> uriMap;
//
//    private int curPosition;
//
//    private boolean isActivityActive = false;
//    private Timer mTimer;
//
//    private boolean isNetworkAllowed = false;
//
//    private String mVid = "";
//
//    private final static Map<String, Integer> pauseSeek = new HashMap<>();
//
//    private VideoViewReceiver.VideoViewState mPreState = VideoViewReceiver.VideoViewState.IDLE;
//
//    private Runnable mBufferringRunnable = null;
//
//    private Dialog mFeeDialog;
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Object message = msg.obj;
//            if (null != message) {
//                if (message instanceof VideoViewReceiver.CommandEventMessage) {
//                    handleCommandEvent((VideoViewReceiver.CommandEventMessage) message);
//                } else if (message instanceof VideoViewReceiver.ListenerEventMessage) {
//                    handleListenerEvent((VideoViewReceiver.ListenerEventMessage) message);
//                } else {
//                    L.error(this, "message class type is out of control");
//                }
//            } else {
//                L.error(this, "message is null");
//            }
//            super.handleMessage(msg);
//        }
//    };
//
//
//    public VideoViewUIHelp(View view) {
//        this.mRootView = view;
//        this.mContext = view.getContext();
//        init();
//    }
//
//    private void init() {
//        mStateHelp = new VideoShowInteractHelp(mRootView, this);
//        mReceiver = new VideoViewReceiver(mStateHelp.getmVideoView(), mHandler);
//
//    }
//
//
//    public void handleCommandEvent(VideoViewReceiver.CommandEventMessage message) {
//        mStateHelp.setState(message.state);
//        VideoComandEvent event = message.event;
//        switch (event) {
//            case COMMAND_ERROR:
//                mStateHelp.commandErrorState();
//                break;
//            case COMMAND_PLAY:
//                mStateHelp.commandPlayState();
//                break;
//            case COMMAND_PAUSE:
//                mStateHelp.commandPauseState();
//                break;
//            case COMMAND_RESTART:
//                mStateHelp.commandRestartState();
//                break;
//            case COMMAND_RELEASE:
//                mStateHelp.commandReleaseState();
//                break;
//            case COMMAND_SEEK:
//                if (NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//                    mStateHelp.commandSeekState();
//                    commandTimeOut(VideoViewReceiver.VideoViewState.SEEK);
//                } else {
//                    mReceiver.release();
//                    mStateHelp.noNetworkStateView();
//                }
//                break;
//            case COMMAND_START:
//                mStateHelp.commandStartState();
//                break;
//            default:
//                break;
//        }
//        L.info(this, "command: " + message.event + " state: " + message.state.name() + " extra: " + message.extra);
//    }
//
//    public void handleListenerEvent(VideoViewReceiver.ListenerEventMessage message) {
//        mStateHelp.setState(message.state);
//        VideoViewReceiver.VideoListenerEvent event = message.event;
//        switch (event) {
//            case ERROR_EVENT:
//                mStateHelp.eventErrorState();
//                mReceiver.release();
//                resetPauseSeek(mVid);
//                break;
//            case UPDATING_EVENT:
//                mStateHelp.eventUpdattingState();
//                break;
//            case COMPLETION_EVENT:
//                mStateHelp.eventCompletionState();
//                mReceiver.release();
//                resetPauseSeek(mVid);
//                break;
//            case PREPARE_EVENT:
//                if (!(VideoViewReceiver.VideoViewState.PAUSE == mPreState && mReceiver.getmState() == mPreState)) {
//                    mStateHelp.eventPreparedState();
//                    startAfterPrepare();
//                } else {
//                    mStateHelp.commandPauseState();
//                    mPreState = VideoViewReceiver.VideoViewState.IDLE;
//                }
//                break;
//            case SEEKCOMPLETION_EVENT:
//                mStateHelp.eventSeekCompletionState();
//                break;
//
//            case BUFFERING_START_EVENT:
//                if (NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//                    mStateHelp.commandSeekState();
//                    commandTimeOut(VideoViewReceiver.VideoViewState.BUFFER);
//                } else {
//                    mReceiver.release();
//                    mStateHelp.noNetworkStateView();
//                }
//                break;
//            case BUFFERING_END_EVENT:
//                mStateHelp.eventSeekCompletionState();
//                break;
//            case LAGGING_EVENT:
//            default:
//                break;
//        }
//        L.info(this, "event: " + message.event + " state: " + message.state.name() + " extra: " + message.extra);
//    }
//
//
//    private void commandTimeOut(final VideoViewReceiver.VideoViewState state) {
//        if (null != mBufferringRunnable) {
//            mHandler.removeCallbacks(mBufferringRunnable);
//        }
//        mBufferringRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (null != mReceiver && mReceiver.getmState() == state) {
//                    L.error(VideoViewUIHelp.class.getName(), "time out state: " + state.name());
//                    mReceiver.release();
//                    if (null != mStateHelp)
//                        mStateHelp.eventErrorState();
//                }
//            }
//        };
//        mHandler.postDelayed(mBufferringRunnable, 5000);
//    }
//
//
//    //    public void setUri(EnumMap<VideoShowDetailActivity.VideoShowRate, Model.VideoDefinition> uri) {
////        this.uriMap = uri;
////        setDefaultRate(uri);
////        mStateHelp.setUriMap(uri);
////    }
////
////    private void setDefaultRate(EnumMap<VideoShowDetailActivity.VideoShowRate, Model.VideoDefinition> uri) {
////        VideoShowDetailActivity.VideoShowRate rate = GsonSharepreference.get(VideoShowDetailActivity.RATE_KEY, VideoShowDetailActivity.VideoShowRate.class, VideoShowDetailActivity.VideoShowRate.MIDDLE);
////        if (isRateSupport(rate)) return;
////        if (null != uri) {
////            Set<VideoShowDetailActivity.VideoShowRate> rates = uriMap.keySet();
////            for (VideoShowDetailActivity.VideoShowRate _rate : rates) {
////                GsonSharepreference.save(VideoShowDetailActivity.RATE_KEY, _rate);
////                return;
////            }
////        }
////
////    }
////
////    private String getVideoRate(VideoShowDetailActivity.VideoShowRate rate) {
////        if (null != uriMap && uriMap.containsKey(rate)) {
////            Model.VideoDefinition definition = uriMap.get(rate);
////            if (null != definition) {
////                return definition.url;
////            }
////        }
////        return "";
////    }
////
//    private String getDefaultRate() {
////        VideoShowDetailActivity.VideoShowRate rate = GsonSharepreference.get(VideoShowDetailActivity.RATE_KEY, VideoShowDetailActivity.VideoShowRate.class, VideoShowDetailActivity.VideoShowRate.MIDDLE);
////        return getVideoRate(rate);
//        return "";
//    }
////
////    private boolean isRateSupport(VideoShowDetailActivity.VideoShowRate rate) {
////        String url = getVideoRate(rate);
////        if (TextUtils.isEmpty(url)) return false;
////        else return true;
////    }
//
//    @Override
//    public void pauseOrStart(boolean pause) {
//        if (pause) {
//            mReceiver.pause();
//            curPosition = 0;
//        } else {
//            if (curPosition == 0) {
//                mReceiver.restart();
//            } else {
//                seek(curPosition);
//                curPosition = 0;
//            }
//        }
//    }
//
//
//    @Override
//    public void seek(int position) {
//        if (NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//            mReceiver.seekTo(position);
//        } else {
//            if (mReceiver.canSeekto(position)) {
//                mReceiver.seekTo(position);
//            } else {
//                mReceiver.release();
//                mStateHelp.noNetworkStateView();
//            }
//        }
//    }
//
//    @Override
//    public void zoom(boolean zoomOut) {
//        mStateHelp.setFullMode(zoomOut);
//    }
//
//
//    private void chooseNetwork() {
//        if (!NetWorkManagerUtil.isFreeNetwork(mContext) && !isNetworkAllowed) {//收费
////              if(NetWorkManagerUtil.NetworkType.WIFI.equals(type)&&!isNetworkAllowed){//test condition
//            View view = LayoutInflater.from(mContext).inflate(R.layout.videoshownetwork_dialog, null);
//            Button sure_bt = (Button) view.findViewById(R.id.sure);
//            Button cancle = (Button) view.findViewById(R.id.cancle);
//            final Dialog dialog = DialogFactory.getNoFloatingDialog(mContext, view);
//            dialog.show();
//            sure_bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isNetworkAllowed = true;
//                    dialog.dismiss();
//                    mReceiver.setVideoUri(getDefaultRate());
//                    mReceiver.play();
//                    SFBus.post(new VideoShowEvent.VideoShowPlayEvent());
//                }
//            });
//            cancle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isNetworkAllowed = false;
//                    dialog.dismiss();
//                }
//            });
//        } else {
//            isNetworkAllowed = true;
//            mReceiver.setVideoUri(getDefaultRate());
//            mReceiver.play();
//            SFBus.post(new VideoShowEvent.VideoShowPlayEvent());
//        }
//    }
//
//    @Override
//    public void play() {
//        if (NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//            if (!TextUtils.isEmpty(getDefaultRate())) {
//                VideoViewReceiver.VideoViewState state = mReceiver.getmState();
//                if (state != VideoViewReceiver.VideoViewState.IDLE
//                        && state != VideoViewReceiver.VideoViewState.ERROR
//                        && state != VideoViewReceiver.VideoViewState.RELEASE) {
//                    mReceiver.release();
//                } else {
//                    L.info(this, "don't need release before play, state: " + state.name());
//                }
//                chooseNetwork();
//            } else {
//                //TODO handle this message
//                L.error(this, "uri is null");
//                mStateHelp.eventErrorState();
//            }
//        } else {
//            mReceiver.release();
//            mStateHelp.noNetworkStateView();
//        }
//    }
//
//    @Override
//    public void changeRate() {
//        VideoViewReceiver.VideoViewState state = mReceiver.getmState();
//        if (state == VideoViewReceiver.VideoViewState.PLAY
//                || state == VideoViewReceiver.VideoViewState.BUFFERED
//                || VideoViewReceiver.VideoViewState.BUFFER == state
//                || state == VideoViewReceiver.VideoViewState.PAUSE) {
//            curPosition = mReceiver.getCurPos();
//        }
//        if (state != VideoViewReceiver.VideoViewState.IDLE
//                && state != VideoViewReceiver.VideoViewState.ERROR
//                && state != VideoViewReceiver.VideoViewState.RELEASE) {
//            play();
//        }
//    }
//
//    @Override
//    public void release() {
//        if (null != mReceiver) {
//            mReceiver.release();
//        }
//    }
//
//    private void startAfterPrepare() {
//        int seekPos = curPosition;
//        if (seekPos == 0 && !TextUtils.isEmpty(mVid) && pauseSeek.containsKey(mVid)) {
//            seekPos = pauseSeek.get(mVid);
//        }
//        if (seekPos == 0) {
//            mReceiver.simpleStart();
//            L.info(this, "doRefresh");
//        } else {
//            mReceiver.seekTo(seekPos);
//            mReceiver.pause();
//            L.info(this, "doRefresh and seek");
//            curPosition = 0;
//        }
//    }
//
////    public void setVideoShowContent(VideoShowContent content, boolean isFirst) {
////        mStateHelp.updateVideoShowContent(content, isNetworkAllowed, isFirst);
////        if (null != content) {
////            mVid = content.v_id;
////        }
////    }
//
//    public class VideoProgressTask extends TimerTask {
//
//        @Override
//        public void run() {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != mReceiver && isActivityActive) {
//                        int curMill = mReceiver.getCurPos();
//                        if (curMill < curPosition) {
//                            curMill = curPosition;
//                        }
//                        int total = mReceiver.getDuration();
//                        mStateHelp.updateProgress(curMill, total);
//                    }
//                }
//            });
//        }
//
//    }
//
//    @Override
//    public void onCreate() {
//        mTimer = new Timer();
//        mTimer.schedule(new VideoProgressTask(), 1000, 1000);
//    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
//    public void onDestory() {
//        Surface surface = mStateHelp.getmVideoView().getHolder().getSurface();
//        if (null != surface) {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                surface.release();
//            }
//        }
//        mTimer.cancel();
//        mTimer = null;
//        mReceiver.release();
//        mReceiver = null;
//    }
//
//    @Override
//    public void onStart() {
//        isActivityActive = true;
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        isActivityActive = false;
//        EventBus.getDefault().unregister(this);
//    }
//
//    public void onNetworkChange(GlobalEvent.NetworkEvent networkEvent) {
//        if (networkEvent.hasNetwork) {
//            if (!NetWorkManagerUtil.isFreeNetwork(mContext)) {
//                if (null == mFeeDialog) {
//                    View view = LayoutInflater.from(mContext).inflate(R.layout.videoshownetwork_dialog, null);
//                    Button sure_bt = (Button) view.findViewById(R.id.sure);
//                    Button cancle = (Button) view.findViewById(R.id.cancle);
//                    mFeeDialog = DialogFactory.getNoFloatingDialog(mContext, view);
//                    sure_bt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isNetworkAllowed = true;
//                            mFeeDialog.dismiss();
//                        }
//                    });
//                    cancle.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isNetworkAllowed = false;
//                            mFeeDialog.dismiss();
//                            if (null != mReceiver) {
//                                mReceiver.release();
//                                if (null != mStateHelp)
//                                    mStateHelp.eventCompletionState();
//                            }
//                        }
//                    });
//                }
//                if (!mFeeDialog.isShowing())
//                    mFeeDialog.show();
//            }
//        }
//    }
//
//    public void onVidoeShowStop(VideoShowEvent.VideoShowStopEvent stopEvent) {
//        if (null != mReceiver) {
//            mReceiver.release();
//            if (!NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//                mStateHelp.noNetworkStateView();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        if ((VideoViewReceiver.VideoViewState.PLAY == mPreState
//                || mPreState == VideoViewReceiver.VideoViewState.BUFFERED
//                || VideoViewReceiver.VideoViewState.BUFFER == mPreState)
//                && mReceiver.getmState() == VideoViewReceiver.VideoViewState.PAUSE) {
//            mReceiver.seekTo(curPosition);
//        } else if (mReceiver.getmState() == VideoViewReceiver.VideoViewState.IDLE
//                || mReceiver.getmState() == VideoViewReceiver.VideoViewState.SEEK) {
//            if (!NetWorkManagerUtil.isNetworkAvailable(mContext)) {
//                mReceiver.release();
//                mStateHelp.noNetworkStateView();
//            }
//        }
//    }
//
//    @Override
//    public void onPause() {
//        mPreState = mReceiver.getmState();
//        if (mReceiver.getmState() == VideoViewReceiver.VideoViewState.PLAY
//                || mReceiver.getmState() == VideoViewReceiver.VideoViewState.BUFFERED
//                || VideoViewReceiver.VideoViewState.BUFFER == mReceiver.getmState()) {
//            mReceiver.pause();
//            curPosition = mReceiver.getCurPos();
//            if (!TextUtils.isEmpty(mVid)) {
//                pauseSeek.put(mVid, curPosition);
//            }
//        } else if (mReceiver.getmState() == VideoViewReceiver.VideoViewState.PAUSE) {
//            curPosition = mReceiver.getCurPos();
//        }
//
//    }
//
//    private void resetPauseSeek(String vid) {
//        if (!TextUtils.isEmpty(mVid) && pauseSeek.containsKey(vid)) {
//            pauseSeek.put(vid, 0);
//        }
//    }
//
//    public boolean onBackPress() {
//        boolean back = mStateHelp.onBackPress();
//        return back;
//    }
//
//
//    public void onConfigurationChanged(boolean fullMode) {
//        mStateHelp.onConfigurationChanged(fullMode);
//    }
//}
