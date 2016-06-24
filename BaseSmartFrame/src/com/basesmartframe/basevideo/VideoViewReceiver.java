package com.basesmartframe.basevideo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.VideoView;

import com.sf.loglib.L;

import java.lang.ref.WeakReference;

/**
 * Created by xieningtao on 15-4-27.
 */
public class VideoViewReceiver {
    private final static String TAG = "VideoViewReceiver";

    public enum VideoViewState {PLAY, PAUSE, SEEK, ERROR, IDLE, RELEASE, BUFFER, BUFFERED, LAGGING}

    public static enum VideoListenerEvent {ERROR_EVENT, PREPARE_EVENT, UPDATING_EVENT, COMPLETION_EVENT, SEEKCOMPLETION_EVENT, BUFFERING_START_EVENT, BUFFERING_END_EVENT, LAGGING_EVENT}//callback event


    private WeakReference<VideoView> mVideoView;
    private String mUri;
    private VideoViewState mState = VideoViewState.IDLE;
    private MediaPlayer mMediaPlayer = null;
    private int mBufferedPercentage = 0;
    private Handler mHandler;

    public VideoViewReceiver(VideoView videoView, Handler handler) {
        this.mVideoView = new WeakReference<>(videoView);
        this.mHandler = handler;
        initListener();
    }

    public VideoViewReceiver(VideoView videoView) {
        this.mVideoView = new WeakReference<VideoView>(videoView);
    }

    private void registetMediaListener(MediaPlayer mediaPlayer) {
        if (mediaPlayer != mMediaPlayer) {
            mMediaPlayer = mediaPlayer;
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    mBufferedPercentage = percent;
                    String extra = "buffering update";
//                    sendListenerEvent(VideoListenerEvent.UPDATING_EVENT,extra);
                }
            });

            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    VideoView videoView = mVideoView.get();
                    if (null != videoView) {
                        videoView.start();
                    }
                    mState = VideoViewState.PLAY;
                    String extra = "seek completion";
                    sendListenerEvent(VideoListenerEvent.SEEKCOMPLETION_EVENT, extra);
                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                    L.info(VideoViewReceiver.class.getName(), "info i: " + i + " i2: " + i2);
                    if (i == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        mState = VideoViewState.BUFFER;
                        String extra = "MEDIA_INFO_BUFFERING_START";
                        sendListenerEvent(VideoListenerEvent.BUFFERING_START_EVENT, extra);
                        return true;
                    } else if (i == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
//                        mState = VideoViewState.BUFFERED;
//                        VideoView videoView = mVideoView.get();
//                        if (null != videoView) {
//                            videoView.doRefresh();
//                        }
                        mState = VideoViewState.BUFFERED;
                        String extra = "MEDIA_INFO_BUFFERING_END";
                        sendListenerEvent(VideoListenerEvent.BUFFERING_END_EVENT, extra);
                        return true;
                    }
//                    else if (i == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
//                        mState = VideoViewState.ERROR;
//                        String extra = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
//                        sendListenerEvent(VideoListenerEvent.ERROR_EVENT, extra);
//                        return true;
//                    }
                    return false;
                }
            });
        }
    }

    private void initListener() {
        VideoView videoView = mVideoView.get();
        if (null != videoView) {

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    L.info(TAG, "onPrepared");
                    registetMediaListener(mediaPlayer);
//                    mState = VideoViewState.PLAY;
                    String extra = "prepared";
                    sendListenerEvent(VideoListenerEvent.PREPARE_EVENT, extra);

                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    L.info(TAG, "onCompletion");
                    mState = VideoViewState.IDLE;
                    String extra = "completion";
                    sendListenerEvent(VideoListenerEvent.COMPLETION_EVENT, extra);
                }
            });

            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    L.error(TAG, "onError-> i:" + i + " i2: " + i2);
                    mState = VideoViewState.ERROR;
                    String extra = "error i1: " + i + " i2: " + i2;
                    sendListenerEvent(VideoListenerEvent.ERROR_EVENT, extra);
                    return true;
                }
            });
        }
    }

    //api
    public void setVideoUri(String uri) {
        L.info(TAG, "setVideoUri: " + uri);
        this.mUri = uri;
    }


    public VideoViewState getmState() {
        return mState;
    }

    public void play() {
        start();
    }

    public void pause() {
        if (VideoViewState.RELEASE == mState) {
            L.error(this, "state is release");
            String extra = "cur action: pause;state is : " + mState.name();
            sendCommandEvent(VideoComandEvent.COMMAND_PAUSE, extra);
            return;
        }
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            if (VideoViewState.PLAY == mState || VideoViewState.BUFFERED == mState) {
                videoView.pause();
                mState = VideoViewState.PAUSE;
                String extra = "pause action";
                sendCommandEvent(VideoComandEvent.COMMAND_PAUSE, extra);
            } else {
                L.error(this, "cur action: pause,mState is wrong,mState: " + mState.name());
                String extra = "cur action: pause;state is : " + mState.name();
                sendCommandEvent(VideoComandEvent.COMMAND_ERROR, extra);
            }
        } else {
            L.error(this, "videoView is null");
//            mState = VideoViewState.ERROR;
//            String extra = "pause action:videoView is null";
//            sendListenerEvent(VideoListenerEvent.ERROR_EVENT, extra);
        }
    }


    public void release() {
        VideoView videoView = mVideoView.get();
        if (null != videoView && mState != VideoViewState.RELEASE) {
            videoView.stopPlayback();
            try {
                videoView.setVideoURI(null);
            } catch (Exception e) {
                L.error(this, "can not set null");
            }
            mState = VideoViewState.RELEASE;
            String extra = "release action";
            sendCommandEvent(VideoComandEvent.COMMAND_RELEASE, extra);
        } else if (mState == VideoViewState.RELEASE) {
            L.error(this, "videoView has released");
        } else {
            L.error(this, "videoView is null");
//                    mState = VideoViewState.ERROR;
            String extra = "release action:videoView is null";
            sendListenerEvent(VideoListenerEvent.ERROR_EVENT, extra);
        }
    }

    public void seekTo(int millisec) {
        if (VideoViewState.RELEASE == mState) {
            L.error(this, "state is release");
            String extra = "cur action is seek,state is release";
            sendCommandEvent(VideoComandEvent.COMMAND_SEEK, extra);
            return;
        }
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            videoView.seekTo(millisec);
            mState = VideoViewState.SEEK;
            String extra = "seek action";
            sendCommandEvent(VideoComandEvent.COMMAND_SEEK, extra);
        } else {
            L.error(this, "videoView is null");
        }
    }

//    public boolean seekTo(int millisec){
//        if(canSeekto(millisec)){
//            VideoView videoView=mVideoView.get();
//            if(null!=videoView){
//                videoView.seekTo(millisec);
//                return true;
//            }else{
//                L.error(this,"videoView is null");
//            }
//        }else{
//            L.error(this,"videoView can't seek to : "+millisec);
//        }
//        return false;
//    }

    /**
     * pause 之后才能restart
     */
    public void restart() {
        if (VideoViewState.RELEASE == mState) {
            L.error(this, "state is release");
            String extra = "cur action: restart,state is release";
            sendCommandEvent(VideoComandEvent.COMMAND_RESTART, extra);
            return;
        }
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            if (VideoViewState.PAUSE == mState) {
                videoView.start();
                mState = VideoViewState.PLAY;
                String extra = "restart action";
                sendCommandEvent(VideoComandEvent.COMMAND_RESTART, extra);
            } else {
                L.error(this, "cur action: restart state is wrong,mState: " + mState.name());
                String extra = "state is wrong,mState: " + mState.name();
                sendCommandEvent(VideoComandEvent.COMMAND_ERROR, extra);
            }
        } else {
            L.error(this, "videoView is null");
        }
    }

    public void simpleStart() {
        if (VideoViewState.RELEASE == mState) {
            L.error(this, "state is release");
            String extra = "cur action: restart,state is release";
            sendCommandEvent(VideoComandEvent.COMMAND_RESTART, extra);
            return;
        }
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            if (VideoViewState.IDLE == mState || VideoViewState.BUFFERED == mState || VideoViewState.BUFFER == mState) {
                videoView.start();
                mState = VideoViewState.PLAY;
                String extra = "simple doRefresh";
                sendCommandEvent(VideoComandEvent.COMMAND_START, extra);
                L.info(this, "simple doRefresh");
            } else {
                L.error(this, "cur action: simpleStart state is wrong,mState: " + mState.name());
                String extra = "state is wrong,mState: " + mState.name();
                sendCommandEvent(VideoComandEvent.COMMAND_ERROR, extra);
            }
        } else {
            L.error(this, "videoView is null");
        }

    }


    //implementation
    private boolean start() {
        if (TextUtils.isEmpty(mUri)) {
            L.error(this, "mUri is null");
            String extra = "mUri is null";
            sendCommandEvent(VideoComandEvent.COMMAND_PLAY, extra);
            return false;
        }
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            Uri curUri = Uri.parse(mUri);
            if (null != curUri) {
                mState = VideoViewState.IDLE;
                videoView.setVideoURI(curUri);
                String extra = "play action";
                sendCommandEvent(VideoComandEvent.COMMAND_PLAY, extra);
                return true;
            } else {
                L.error(this, "curUri is null,fail to parse mUri: " + mUri);
                String extra = "curUri is null,fail to parse mUri: " + mUri;
                sendCommandEvent(VideoComandEvent.COMMAND_ERROR, extra);
            }
        }
        return false;
    }


    public int getCurPos() {
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            return videoView.getCurrentPosition();
        } else {
            return -1;
        }
    }

    public boolean bufferedEqualCurPos() {
        int curPos = getCurPos();
        int buffered = getBufferedMilli();
        if (curPos != -1 && buffered != -1 && curPos == buffered) {
            return true;
        }
        return false;
    }

    public int getDuration() {
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            return videoView.getDuration();
        } else {
            return -1;
        }
    }

    public int getBuffedPercentage() {
        if (VideoViewState.RELEASE == mState || VideoViewState.ERROR == mState || VideoViewState.IDLE == mState) {
            return 0;
        } else {
            return mBufferedPercentage;
        }
    }

    public int getBufferedMilli() {
        VideoView videoView = mVideoView.get();
        if (null != videoView) {
            return getBuffedPercentage() * videoView.getDuration() / 100;
        }
        return -1;
    }

    public boolean canSeekto(int millisec) {
        int buffer = getBufferedMilli();
        L.info(this, "cur buffer : " + buffer + " seek to: " + millisec);
        if (buffer == -1) {
            return false;
        } else {
            return millisec < buffer && millisec >= 0;
        }
    }


    private void sendListenerEvent(VideoListenerEvent event, String extra) {
        Message message = mHandler.obtainMessage();
        ListenerEventMessage ms = new ListenerEventMessage(event, mState, extra);
        message.obj = ms;
        mHandler.sendMessage(message);
    }

    private void sendCommandEvent(VideoComandEvent event, String extra) {
        Message message = mHandler.obtainMessage();
        CommandEventMessage ms = new CommandEventMessage(event, mState, extra);
        message.obj = ms;
        mHandler.sendMessage(message);
    }

    public static class ListenerEventMessage {
        public final VideoListenerEvent event;
        public final VideoViewState state;
        public final String extra;

        public ListenerEventMessage(VideoListenerEvent event, VideoViewState state, String extra) {
            this.event = event;
            this.state = state;
            this.extra = extra;
        }
    }

    public static class CommandEventMessage {
        public final VideoComandEvent event;
        public final VideoViewState state;
        public final String extra;

        public CommandEventMessage(VideoComandEvent event, VideoViewState state, String extra) {
            this.event = event;
            this.state = state;
            this.extra = extra;
        }
    }
}
