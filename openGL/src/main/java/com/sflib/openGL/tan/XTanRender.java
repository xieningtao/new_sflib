package com.sflib.openGL.tan;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by xieningtao on 16-3-27.
 */
public class XTanRender implements GLSurfaceView.Renderer {

    private float mProjectionMatrix[] = new float[16];
    private float mViewMatrix[] = new float[16];
    private float mMVPMatrix[] = new float[16];

    private float mTranslation[] = new float[16];
    private boolean mIsFirstTime = true;

    private XTan mXTan;

    public XTanRender() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        mXTan = new XTan(width, height, 2 * ratio, 2);

        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1.499999f, 100);
    }

    private long mPreTime = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        long curTime = System.currentTimeMillis();
        long det = curTime - mPreTime;
        if (det < 16) {
            try {
                Thread.sleep(det);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            mPreTime = curTime;
        }
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 2.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        if (mIsFirstTime) {
            mTranslation = mXTan.getResetTranslation();
            mIsFirstTime = false;
        }
        mTranslation = mXTan.updateXTanStep(mTranslation, -0.02f);
        float result[] = new float[16];
        Matrix.multiplyMM(result, 0, mMVPMatrix, 0, mTranslation, 0);


        mXTan.draw(result);

        Log.d("drawFrame", " " + GLES20.glGetError());

    }
}
