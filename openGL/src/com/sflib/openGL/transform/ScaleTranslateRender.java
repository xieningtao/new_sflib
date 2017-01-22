package com.sflib.openGL.transform;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sflib.openGL.OpenGLVideo;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by xieningtao on 15-11-20.
 */
public class ScaleTranslateRender implements GLSurfaceView.Renderer {
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private AtomicBoolean fullMode = new AtomicBoolean(false);

    private OpenGLVideo mCubic;

    private Context mContext;


    public ScaleTranslateRender(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        mCubic = new OpenGLVideo(mContext,width,height,1,4.0f);
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 4);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);


        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 4.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //draw origin
        mCubic.draw(mMVPMatrix);

        //-------scale first then translate-----------
        //scale down to 0.5f
        float halfScaleMVMatrix[] = new float[4 * 4];
        Matrix.setIdentityM(halfScaleMVMatrix, 0);
        Matrix.scaleM(halfScaleMVMatrix, 0, 0.5f, 0.5f, 0.5f);
        float copyHalfScale[] = halfScaleMVMatrix.clone();
        Matrix.multiplyMM(halfScaleMVMatrix, 0, mMVPMatrix, 0, halfScaleMVMatrix, 0);
        mCubic.draw(halfScaleMVMatrix);

        //scale down to 0.5f then translate to (2,0,0)
        float simpleTranslate[] = new float[4 * 4];
        Matrix.setIdentityM(simpleTranslate, 0);
        Matrix.translateM(simpleTranslate, 0, 2, 0, 0);
        float scaleTranslate[] = new float[4 * 4];
        Matrix.multiplyMM(scaleTranslate, 0, simpleTranslate, 0, copyHalfScale, 0);
        Matrix.multiplyMM(scaleTranslate, 0, mMVPMatrix, 0, scaleTranslate, 0);
        mCubic.draw(scaleTranslate);
        //----------end---------------

        //------------translate then scale-----------
        //origin copy translate (0,-2,0)
        float originTanslate[] = new float[4 * 4];
        Matrix.setIdentityM(originTanslate, 0);
        Matrix.translateM(originTanslate, 0, 0, -2, 0);
        Matrix.multiplyMM(originTanslate, 0, mMVPMatrix, 0, originTanslate, 0);
        mCubic.draw(originTanslate);

        //basic translate (2,0,0)
        float basicTranslate[] = new float[4 * 4];
        Matrix.setIdentityM(basicTranslate, 0);
        Matrix.translateM(basicTranslate, 0, 2, 0, 0);
        float copyBasicTranslate[] = basicTranslate.clone();
        Matrix.multiplyMM(basicTranslate, 0, mMVPMatrix, 0, basicTranslate, 0);
//        mCubic.draw(basicTranslate, fullMode.get());

        //translate then scale down to 0.5f
        float basiHalfScale[] = new float[4 * 4];
        Matrix.setIdentityM(basiHalfScale, 0);
        Matrix.scaleM(basiHalfScale, 0, 0.5f, 0.5f, 0.5f);
        float translateScale[] = new float[4 * 4];
        Matrix.multiplyMM(translateScale, 0, basiHalfScale, 0, copyBasicTranslate, 0);
        Matrix.multiplyMM(translateScale, 0, mMVPMatrix, 0, translateScale, 0);
        mCubic.draw(translateScale);
    }
}
