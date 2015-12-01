package com.sf.yysdkdemo.videoplayer.render;

import android.opengl.GLES20;
import android.os.Build;
import android.os.SystemClock;

import com.duowan.mobile.mediaproxy.RenderFrameBuffer;
import com.sf.yysdkdemo.videoplayer.glutil.Camera;
import com.sf.yysdkdemo.videoplayer.util.Image;
import com.yy.hiidostatis.inner.util.L;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLVideoRender extends VideoRenderBase implements RenderListener {
    private Camera mCamera;
    private Monitor mMonitor;
    private VideoShader mVideoShader;

    private RenderFrameBuffer mFrameBuffer;

    private boolean mNeedRepaint = false;

    private boolean mRenderStopped = true;
    private long mLastFrameTime = 0;

//    private RenderSlowStatistics mRenderSlowStatistics = null;

    public GLVideoRender(int fps, boolean GPUFormatConvert) {
        super(fps);

        mFrameBuffer = new RenderFrameBuffer(GPUFormatConvert);

//        if (AppConfig.getInstance().isRenderSlowStatistics()) {
//            mRenderSlowStatistics = new RenderSlowStatistics(fps, AppConfig.getInstance().getRenderSlowMinInterval());
//        }
    }

    public void linkToStream(long userGroupId, long streamId) {
        mFrameBuffer.linkToStream(userGroupId, streamId);
    }

    public void unLinkFromStream(long userGroupId, long streamId) {
        mFrameBuffer.unLinkFromStream(userGroupId, streamId);
    }

    public RenderFrameBuffer getRenderFrameBuffer() {
        return mFrameBuffer;
    }

    public void release() {
        mFrameBuffer.release();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        L.info("GLVideoRender", "surface created");

        destroy();
        init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        L.info("GLVideoRender", "surface changed width height " + width + " " + height);

        GLES20.glViewport(0, 0, width, height);

        float ratio = 1.0f * width / height;
        mCamera.sharpFocusing(-ratio, ratio);

        mMonitor.setSize(width, height);
        mMonitor.setScaleType(getScaleType());

        mNeedRepaint = true;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        super.onDrawFrame(gl);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
//        Ln.notifyVideoStatus(MediaVideoMsg.VideoFrameStatus.kVideoRendered);
//        if (null != mRenderSlowStatistics) {
//            mRenderSlowStatistics.start();
//        }

//        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//
//        boolean frameChanged = mFrameBuffer.render();
//        ByteBuffer frame = mFrameBuffer.getFrame();
//        if (null != frame && (frameChanged || mNeedRepaint)) {
//            mNeedRepaint = false;
//
//            mCamera.pressShutter(mVideoShader.mMatrixHandle);
//
//            int frameFormat = mFrameBuffer.getFrameFormat();
//
//            mMonitor.setRenderFormat(mVideoShader.mFormat, frameFormat);
//
//            mMonitor.updateRenderSizeIfNeed(mFrameBuffer.getWidth(), mFrameBuffer.getHeight(), mFrameBuffer.getPixWidth());
//
//            if (RenderFrameBuffer.FORMAT_RGB == frameFormat) {
//                mMonitor.updateAsRGB(mFrameBuffer.getWidthY(), mFrameBuffer.getHeightY(), GLES20.GL_UNSIGNED_BYTE, frame);
//            } else if (RenderFrameBuffer.FORMAT_RGB565 == frameFormat) {
//                mMonitor.updateAsRGB(mFrameBuffer.getWidth(), mFrameBuffer.getHeight(), GLES20.GL_UNSIGNED_SHORT_5_6_5, frame);
//            } else if (RenderFrameBuffer.FORMAT_UNKNOWN != frameFormat) {
//                mMonitor.updateAsYUV(mFrameBuffer);
//            } else {
//                L.error("GLVideoRender", "UNKNOWN FRAME FORMAT");
//            }
//        }
//
//        if(mMonitor == null || mVideoShader == null){
//            L.error("GLVideoRender", "Monitor is null:%b ; VideoShader is null:%b",
//                    mMonitor == null, mVideoShader == null);
//            return;
//        }
//        mMonitor.refresh(mVideoShader.mPositionHandle, mVideoShader.mTextureHandle,
//                mVideoShader.mSampleY, mVideoShader.mSampleU, mVideoShader.mSampleV);
//        CatchError.catchError("onDrawFrame");

//        if (null != mRenderSlowStatistics) {
//            mRenderSlowStatistics.statistics();
//        }

//        super.onDrawFrame(gl);
//
//        notifyRenderStatusIfNeed(frameChanged);
    }

    @Override
    protected void onScaleTypeChanged(Image.ScaleType type) {
        if (null != mMonitor) {
            mMonitor.setScaleType(type);
        }
    }

    private void init() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mVideoShader = new VideoShader();
        mVideoShader.use();

        float top = 1.0f;
        float bottom = -1.0f;
        float near = 1.0f;
        float eyeZ = -near;
        float nearZ = near + eyeZ;
        mCamera = new Camera(top, bottom, near, 2 * near, eyeZ);
        mMonitor = new Monitor(mVideoShader.mPositionHandle, mVideoShader.mTextureHandle, top, bottom, nearZ);

        mCamera.setUp();

        mRenderStopped = true;
        mLastFrameTime = 0;

        GLOESReport();
    }

    private void notifyRenderStatusIfNeed(boolean frameChanged) {
        if (mRenderStopped && frameChanged) {
            mRenderStopped = false;
            renderStart();
            mLastFrameTime = 0;
            return;
        }

        if (!frameChanged && 0 == mLastFrameTime) {
            mLastFrameTime = SystemClock.elapsedRealtime();
            return;
        }

        if (!frameChanged && !mRenderStopped) {
            long msc = SystemClock.elapsedRealtime();
            if (msc - mLastFrameTime > KRenderInterval) {
                renderStop();
                mRenderStopped = true;
            }
        } else {
            mLastFrameTime = 0;
        }
    }

    //android doc said when the EGL context is lost,
    //all OpenGL resources associated with that context will be automatically deleted
    @SuppressWarnings("unused")
    private void destroy() {
        if (null != mMonitor) {
            mMonitor.destroy(mVideoShader.mPositionHandle, mVideoShader.mTextureHandle);
            mMonitor = null;
        }

        if (null != mVideoShader) {
            mVideoShader.destroy();
            mVideoShader = null;
        }
    }

    private void GLOESReport() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }

//        boolean reported = Config.getInstance(BaseApp.gContext).getBoolean("GL_OES_REPORTED", false);
//        if (reported) {
//            return;
//        }

        String extension = GLES20.glGetString(GLES20.GL_EXTENSIONS);
        boolean support = extension.contains("OES_EGL_image_external");
//        Ln.reportEvent(ReportConst.OES_EGL_image_external, String.valueOf(support));

//        Config.getInstance(BaseApp.gContext).setBoolean("GL_OES_REPORTED", true);
    }

    private class RenderSlowStatistics {
        private int mCount = 0;
        private int mCurrent = 0;
        private long mInterval = 0;
        private long mStart = 0;
        private long mMin = 50L;

        public RenderSlowStatistics(int fps, long minInterval) {
            mInterval = 1000 / fps;
            mCount = fps * 2;
            mMin = minInterval;
        }

        public void start() {
            mStart = SystemClock.elapsedRealtime();
        }

        public void statistics() {
            long diff = SystemClock.elapsedRealtime() - mStart;

            if (diff <= mInterval || diff < mMin) {
                mCurrent = 0;
                return;
            }

            mCurrent++;
            if (mCurrent > mCount) {
                mCurrent = 0;
            }
        }
    }
}
