package com.sf.yysdkdemo.videoplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import com.medialib.video.MediaVideoMsg;
import com.sf.yysdkdemo.videoplayer.util.Image;
import com.yy.hiidostatis.inner.util.L;
import com.yyproto.outlet.IMediaVideo;
import com.yyproto.outlet.IProtoMgr;

public abstract class YGLVideoViewBase extends GLSurfaceView {
    private static final String TAG = YGLVideoViewBase.class.getName();
    private static final int SpeedUp = 2;//render --> thread sleep may cost more time

    private final Handler mCallback = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (MediaVideoMsg.MsgType.onFpsNotify == msg.what) {
                MediaVideoMsg.FpsInfo info = (MediaVideoMsg.FpsInfo) msg.obj;
                L.info(TAG, "streamId %d bitRate %d fps %d", info.streamId, info.bitRate, info.frameRate);
                onFPSChanged(info.frameRate);
            }
        }
    };

    public YGLVideoViewBase(Context context) {
        super(context);
    }

    public YGLVideoViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPause() {
        IMediaVideo channel = IProtoMgr.instance().getMedia();
        if (null != channel) {
            channel.removeMsgHandler(mCallback);
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        IMediaVideo channel = IProtoMgr.instance().getMedia();
        if (null != channel) {
            channel.addMsgHandler(mCallback);
        }

        super.onResume();
    }

    public void release() {
        IMediaVideo channel = IProtoMgr.instance().getMedia();
        if (null != channel) {
            channel.removeMsgHandler(mCallback);
        }
    }

    public void setVideoScaleType(final Image.ScaleType type) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                onVideoScaleTypeChanged(type);
            }
        });
    }

    private void onFPSChanged(final int fps) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                onVideoFPSChanged(fps + SpeedUp);
            }
        });
    }

    protected abstract void onVideoFPSChanged(int fps);

    protected abstract void onVideoScaleTypeChanged(Image.ScaleType type);
}
