package com.sf.yysdkdemo.videoplayer;

import android.util.Log;
import android.view.View;

import com.duowan.mobile.service.CallbackHandler;
import com.sf.yysdkdemo.videoplayer.util.Image;
import com.yy.hiidostatis.inner.util.L;
import com.yyproto.outlet.IMediaVideo;

public class GLVideoPlayer extends BaseVideoPlayer {
    private YGLVideoView mGLVideoView = null;
    private CallbackHandler mGLVideoStatusCallback = null;

    public GLVideoPlayer(YGLVideoView videoView) {
        this.mGLVideoView = videoView;
    }

    @Override
    public void onStart() {
        if (null != mGLVideoView) {
            Log.d(TAG, "video gl rendering resume");
            mGLVideoView.onResume();
        }

        super.onStart();
    }

    @Override
    public void onStop() {
        if (null != mGLVideoView) {
            Log.d(TAG, "video gl rendering pause");
            mGLVideoView.onPause();
        }
        super.onStop();
    }

    public void setVideoScaleType(Image.ScaleType type) {
        mGLVideoView.setVideoScaleType(type);
    }

    public View getView() {
        return mGLVideoView;
    }

    @Override
    protected void initCallback() {
        if (null != mGLVideoView) {
            mGLVideoStatusCallback = new CallbackHandler() {
                public void onRenderStart() {
                    L.debug(TAG, "YGLVideo render start");
                }

                public void onRenderStop() {
                    L.debug(TAG, "YGLVideo render stop");
                }
            };
        }
    }

    @Override
    protected void link(long userGroupId, long streamId) {
        if (null != mGLVideoView) {
            mGLVideoView.linkToStream(userGroupId, streamId);
        }
    }

    @Override
    protected void unLink(long userGroupId, long streamId) {
        if (null != mGLVideoView) {
            mGLVideoView.unLinkFromStream(userGroupId, streamId);
        }
    }

    @Override
    protected void addToChannel(IMediaVideo channel) {
        if (null != mGLVideoView) {
            channel.addRenderFrameBuffer(mGLVideoView.getRenderFrame());
        }
    }

    @Override
    protected void removeFromChannel(IMediaVideo channel) {
        if (null != mGLVideoView) {
            channel.removeRenderFrameBuffer(mGLVideoView.getRenderFrame());
        }
    }

    @Override
    protected void addCallback() {

    }

    @Override
    protected void removeCallback() {

    }


    @Override
    protected void release() {
        if (null != mGLVideoView) {
            mGLVideoView.release();
            mGLVideoView = null;
        }
    }
}
