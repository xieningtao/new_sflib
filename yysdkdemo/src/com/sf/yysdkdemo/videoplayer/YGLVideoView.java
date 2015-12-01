package com.sf.yysdkdemo.videoplayer;

import android.content.Context;
import android.util.AttributeSet;

import com.duowan.mobile.mediaproxy.RenderFrameBuffer;
import com.sf.yysdkdemo.videoplayer.render.GLVideoRender;
import com.sf.yysdkdemo.videoplayer.util.Image;
import com.yy.hiidostatis.inner.util.L;

public class YGLVideoView extends YGLVideoViewBase {
    private final String TAG = YGLVideoView.class.getName();

    private GLVideoRender mVideoRender;

    public YGLVideoView(Context context) {
        super(context);
        init(true);
    }

    public YGLVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true);
    }

    public YGLVideoView(Context context, boolean GPUFormatConvert) {
        super(context);
        init(GPUFormatConvert);
    }

    public void linkToStream(long userGroupId, long streamId) {
        mVideoRender.linkToStream(userGroupId, streamId);
    }

    public void unLinkFromStream(long userGroupId, long streamId) {
        mVideoRender.unLinkFromStream(userGroupId, streamId);
    }

    @Override
    public void release() {
        mVideoRender.release();
        super.release();
    }

    public RenderFrameBuffer getRenderFrame() {
        return mVideoRender.getRenderFrameBuffer();
    }

    @Override
    protected void onVideoFPSChanged(int fps) {
        mVideoRender.setFPS(fps);
    }

    @Override
    protected void onVideoScaleTypeChanged(Image.ScaleType type) {
        mVideoRender.setScaleType(type);
    }

    private void init(boolean GPUFormatConvert) {
        setEGLContextClientVersion(2);


        int fps = 30;
        L.info("YGLVideoView", "FPS config " + fps);

        mVideoRender = new GLVideoRender(fps, GPUFormatConvert) {
            @Override
            public void renderStart() {
                L.info(TAG, "method->renderStart");
            }

            @Override
            public void renderStop() {
                L.info(TAG, "method->renderStop");
            }
        };

        setRenderer(mVideoRender);
    }
}
