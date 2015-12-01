package com.sf.yysdkdemo.videoplayer.render;

import android.opengl.GLSurfaceView;

import com.sf.yysdkdemo.videoplayer.util.FPSLimiter;
import com.sf.yysdkdemo.videoplayer.util.Image;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class VideoRenderBase implements GLSurfaceView.Renderer {
    private FPSLimiter mFPSLimiter = null;
    private Image.ScaleType mScaleType = Image.ScaleType.Fit;

    protected VideoRenderBase(int fps) {
        setFPS(fps);
    }

    public void setFPS(int fps) {
        mFPSLimiter = null;
        mFPSLimiter = new FPSLimiter(fps);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFPSLimiter.limitFPS();
    }

    public void setScaleType(Image.ScaleType type) {
        if (type != mScaleType) {
            mScaleType = type;
            onScaleTypeChanged(type);
        }
    }

    protected Image.ScaleType getScaleType() {
        return mScaleType;
    }

    protected abstract void onScaleTypeChanged(Image.ScaleType type);
}
