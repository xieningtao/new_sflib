package com.sf.yysdkdemo.videoplayer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.duowan.mobile.mediaproxy.glvideo.YGLVideoView;
import com.yyproto.outlet.IMediaVideo;

/**
 * Created by xieningtao on 15-11-6.
 */
public class YYSDKVideoPlayer extends BaseVideoPlayer {
    private YGLVideoView mGLVideoView = null;

    public YYSDKVideoPlayer(Context context, ViewGroup.LayoutParams lp) {
        mGLVideoView = new YGLVideoView(context, true);
        mGLVideoView.setLayoutParams(lp);
    }

    @Override
    protected void initCallback() {

    }

    public View getVideoView() {
        return mGLVideoView;
    }

    @Override
    protected void link(long userGroupId, long streamId) {
        mGLVideoView.linkToStream(userGroupId, streamId);
    }

    @Override
    protected void unLink(long userGroupId, long streamId) {
        mGLVideoView.unLinkFromStream(userGroupId, streamId);
    }

    @Override
    protected void addToChannel(IMediaVideo channel) {
        channel.addRenderFrameBuffer(mGLVideoView.getRenderFrameBuffer());
    }

    @Override
    protected void removeFromChannel(IMediaVideo channel) {
        channel.removeRenderFrameBuffer(mGLVideoView.getRenderFrameBuffer());
    }

    @Override
    protected void addCallback() {

    }

    @Override
    protected void removeCallback() {

    }

    @Override
    protected void release() {

    }
}
