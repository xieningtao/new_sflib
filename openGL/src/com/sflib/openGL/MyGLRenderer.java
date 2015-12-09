/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sflib.openGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Square mSquare;
    private Cubic mCubic;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float mAngle;

    private final Context mContext;

    private AtomicBoolean fullMode = new AtomicBoolean(false);

    public MyGLRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mTriangle = new Triangle();
        mSquare = new Square();
        mCubic = new Cubic(mContext);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);


        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 2.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        float firsScaleMVMatrix[] = new float[4 * 4];
        Matrix.setIdentityM(firsScaleMVMatrix, 0);
//        Matrix.scaleM(firsScaleMVMatrix, 0, 1.5f, 1.5f, 1);

        Matrix.multiplyMM(firsScaleMVMatrix, 0, mMVPMatrix, 0, firsScaleMVMatrix, 0);
        mCubic.draw(firsScaleMVMatrix, fullMode.get());

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
        float near = 1.5f * ratio;
        if (height > width) {
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, near, 100);
        } else {
            ratio = (float) height / width;
            near = 1.5f * ratio;
            Matrix.frustumM(mProjectionMatrix, 0, -1, 1, -ratio, ratio, near, 100);
        }

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method


    }

    public void changeOritation(boolean full) {
        fullMode.set(full);
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

}