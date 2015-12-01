package com.sf.yysdkdemo.videoplayer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.medialib.video.MediaVideoMsg;
import com.sf.yysdkdemo.core.ChannelManger;
import com.yy.hiidostatis.inner.util.L;
import com.yyproto.outlet.IMediaVideo;

/**
 * Created by xieningtao on 15-11-6.
 */
public abstract class BaseVideoPlayer implements LifeCycle {

    protected final String TAG = BaseVideoPlayer.class.getName();

    private Handler mCallback;
    private IMediaVideo mChannel = null;

    private MediaVideoMsg.VideoStreamInfo mCurrentStream = null;

    public BaseVideoPlayer() {
        init();
    }

    private void init() {
        mChannel = ChannelManger.mediaVideo();
        mCallback = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case MediaVideoMsg.MsgType.onVideoStreamInfoNotify:
                        onVideoStreamInfoNotify((MediaVideoMsg.VideoStreamInfo) msg.obj);
                        break;
                    case MediaVideoMsg.MsgType.onVideoRenderInfoNotify:
                        onVideoRenderInfoNotify((MediaVideoMsg.VideoRenderInfo) msg.obj);
                        break;
                    case MediaVideoMsg.MsgType.onVideoliveBroadcastNotify:
                        onVideoLiveBroadcastNotify((MediaVideoMsg.VideoliveBroadcastInfo) msg.obj);
                        break;
                    case MediaVideoMsg.MsgType.onVideoLinkInfoNotity:
                        onVideoLinkInfoNotify((MediaVideoMsg.VideoLinkInfo) msg.obj);
                        break;
                    case MediaVideoMsg.MsgType.onNoVideoNotify:
                        onNoVideoNotify((MediaVideoMsg.NoVideoInfo) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void onVideoStreamInfoNotify(MediaVideoMsg.VideoStreamInfo info) {
        switch (info.state) {
            case MediaVideoMsg.VideoStreamInfo.Arrive:
                onVideoStreamArrived(info);
                break;
            case MediaVideoMsg.VideoStreamInfo.Stop:
                onVideoStreamStop(info);
                break;
            case MediaVideoMsg.VideoStreamInfo.Start:
                onVideoStreamStart(info);
                break;
            default:
                break;
        }
    }

    private void onNoVideoNotify(MediaVideoMsg.NoVideoInfo info) {
        Log.i(TAG, "no video notify streamId " + info.streamId + " reason " + info.reason);
        //setStateChanged(STATUS.NO_VIDEO);
    }

    private void onVideoRenderInfoNotify(MediaVideoMsg.VideoRenderInfo info) {
        if (MediaVideoMsg.VideoRenderInfo.Start == info.state) {
            Log.d(TAG, "video render start");
        } else if (MediaVideoMsg.VideoRenderInfo.Stop == info.state) {
            Log.d(TAG, "video render stop");
        }
    }

    private void onVideoLiveBroadcastNotify(MediaVideoMsg.VideoliveBroadcastInfo info) {
        Log.i(TAG, "video broad cast " + info.hasVideo + " appId " + info.appid);
    }

    private void onVideoLinkInfoNotify(MediaVideoMsg.VideoLinkInfo info) {
        Log.i(TAG, "video link " + info.state + " appid " + info.appId + " ip " + info.ip + " port " + info.port);
    }

    private void onVideoStreamArrived(MediaVideoMsg.VideoStreamInfo info) {
        Log.i(TAG, "Video stream arrived notify");
        if (null == info || null == mChannel) {
            L.error(TAG, "onVideoStreamArrived null pointer " +
                    (null == mChannel));
            return;
        }

        if (null != mCurrentStream && mCurrentStream.streamId == info.streamId) {
            L.warn(TAG, "video stream notify same stream " + mCurrentStream.streamId);
            return;
        }

        Log.i(TAG, "onStreamArrived userGroupId " + info.userGroupId + " streamId " + info.streamId);

        if (null != mCurrentStream && mCurrentStream.streamId != info.streamId) {
            mChannel.stopVideo(mCurrentStream.userGroupId, mCurrentStream.streamId);
            unLink(mCurrentStream.userGroupId, mCurrentStream.streamId);
            removeFromChannel(mChannel);
        }

        mCurrentStream = info;
        link(mCurrentStream.userGroupId, mCurrentStream.streamId);
        addToChannel(mChannel);

        mChannel.startVideo(mCurrentStream.userGroupId, mCurrentStream.streamId);

    }

    private void onVideoStreamStop(MediaVideoMsg.VideoStreamInfo info) {
        if (null == mCurrentStream) {
            Log.i(TAG, "onStreamArrived userGroupId " + info.userGroupId + " streamId " + info.streamId);
            Log.i(TAG, "video stop notify but current stream null");
        } else {
            Log.i(TAG, "video stop notify userGroupId " + info.userGroupId + " streamId " + info.streamId + " currentStreamId " +
                    mCurrentStream.streamId);
        }

        if (null != mCurrentStream && mCurrentStream.streamId == info.streamId) {
            unLink(info.userGroupId, info.streamId);
            clearStream();
        }
    }

    private void onVideoStreamStart(MediaVideoMsg.VideoStreamInfo info) {
        long now = System.currentTimeMillis();
        Log.i(TAG, "onStreamArrived userGroupId " + info.userGroupId + " streamId " + info.streamId);
    }

    private void clearStream() {
        Log.i(TAG, "video view clearStream");
        mCurrentStream = null;
    }

    @Override
    public void onStart() {
        addCallback();
        mChannel.addMsgHandler(mCallback);
    }

    @Override
    public void onStop() {
        mChannel.removeMsgHandler(mCallback);
        if (null != mCurrentStream) {
            mChannel.stopVideo(mCurrentStream.userGroupId, mCurrentStream.streamId);
            unLink(mCurrentStream.userGroupId, mCurrentStream.streamId);
        }
        removeFromChannel(mChannel);
        removeCallback();
        mChannel.leave();
        release();
    }


    protected abstract void initCallback();

    protected abstract void link(long userGroupId, long streamId);

    protected abstract void unLink(long userGroupId, long streamId);

    protected abstract void addToChannel(IMediaVideo channel);

    protected abstract void removeFromChannel(IMediaVideo channel);

    protected abstract void addCallback();

    protected abstract void removeCallback();

    protected abstract void release();
}
