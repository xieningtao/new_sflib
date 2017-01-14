package com.sflib.openGL.model;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by NetEase on 2016/11/8 0008.
 */

public class XModelRender implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private OBjModel mOBjModel;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private LoadMaxModelHelper mLoadOBJModelHelper;
    private float mAngle;

    private final Context mContext;

    private AtomicBoolean fullMode = new AtomicBoolean(false);

    public XModelRender(Context context, LoadMaxModelHelper loadOBJModelHelper) {
        this.mContext = context;
        this.mLoadOBJModelHelper = loadOBJModelHelper;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        mOBjModel = new OBjModel(mLoadOBJModelHelper);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);


        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        float firsScaleMVMatrix[] = new float[4 * 4];
        Matrix.setIdentityM(firsScaleMVMatrix, 0);
//        Matrix.scaleM(firsScaleMVMatrix, 0, 0.5f, 0.5f, 1);

        Matrix.multiplyMM(firsScaleMVMatrix, 0, mMVPMatrix, 0, firsScaleMVMatrix, 0);
        if (mOBjModel != null) {
            mOBjModel.draw(firsScaleMVMatrix);
        }

//        float simpleTranslate[] = new float[4 * 4];
//        Matrix.setIdentityM(simpleTranslate, 0);
//        Matrix.translateM(simpleTranslate, 0, 2, 0, 0);
//
//        Matrix.multiplyMM(simpleTranslate, 0, mMVPMatrix, 0, simpleTranslate, 0);
//
//        mCubic.draw(simpleTranslate, fullMode.get());
//
//
//        float scaleMVMatrix[] = new float[4 * 4];
//        Matrix.setIdentityM(scaleMVMatrix, 0);
//        Matrix.scaleM(scaleMVMatrix, 0, 1.5f, 1.5f, 1);
//        float temScale[]=scaleMVMatrix.clone();
//
//        float tempTranslate[]=new float[4*4];
//        Matrix.setIdentityM(tempTranslate,0);
//        Matrix.translateM(tempTranslate, 0, 0, -3, 0);
//        Matrix.multiplyMM(scaleMVMatrix, 0, tempTranslate, 0, scaleMVMatrix, 0);
//
//        Matrix.multiplyMM(scaleMVMatrix, 0, mMVPMatrix, 0, scaleMVMatrix, 0);
//
//        mCubic.draw(scaleMVMatrix, fullMode.get());


//        float scaleTranslate[] = new float[4 * 4];
//        Matrix.setIdentityM(scaleTranslate,0);
//        Matrix.translateM(scaleTranslate, 0, 2, 2, 0);
//        Matrix.multiplyMM(scaleTranslate, 0, scaleTranslate, 0, temScale, 0);
//        Matrix.multiplyMM(scaleTranslate,0,mMVPMatrix,0,scaleTranslate,0);
//        mCubic.draw(scaleTranslate, fullMode.get());


        // Draw square
//        mSquare.draw(mMVPMatrix);


        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
//         Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

//        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 1.0f, 0);


        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
//        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

//        mCubic.draw(scratch);
        // Draw triangle
//        mTriangle.draw(scratch);
        Log.d("drawFrame", " " + GLES20.glGetError());
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
//        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 100);
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1.4999999f, 100);

    }

}
