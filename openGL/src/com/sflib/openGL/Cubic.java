package com.sflib.openGL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by xieningtao on 15-10-31.
 */
public class Cubic {


    private final String TAG = "Cubic";

    private int mProgram;
    private FloatBuffer mVertextFloatBuffer;
    private FloatBuffer mTextureBuffer;
    private FloatBuffer mLandscapeTextureBuffer;

    private IntBuffer mVertextIndexBuffer;

    private final float mCubicVertex[] = {
//            1.0f, -1.0f, 0.5f, -1.0f, 1.0f, 0.5f, 1.0f, 1.0f, 0.5f,
//            -1.0f, 1.0f, 0.5f, -1.0f, -1.0f, 0.5f, 1.0f, -1.0f, 0.5f,

            1.0f, -1.0f, 0.5f,
            -1.0f, -1.0f, 0.5f,
            -1.0f, 1.0f, 0.5f,
            1.0f, 1.0f, 0.5f
//            0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f,
//            0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
    };

    private final float mTexture[] = {
            1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f
    };

    private final float mLandscapeTexture[] = {
            0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
    };

    private final int mCubicIndex[] = {
            0, 1, 2,
            2, 3, 0
    };

    float color[] = {0.5f, 1.0f, 1.0f, 1.0f};

    private final int floatSize = 4;

    private final Context mContext;

    private int mTextureId;

    private int mBitmapWidth;
    private int mBitmapHeight;

    private final float mRation;
    private final int mScreenWidth;
    private final int mScreenHeight;

    public Cubic(Context context, final int screenWidth, final int screenHeight) {
        this.mContext = context;
        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;
        this.mRation = (float) ((screenWidth * 1.0) / (screenHeight * 1.0));

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.opengl_16_9);
        mBitmapHeight = bitmap.getHeight();
        mBitmapWidth = bitmap.getWidth();
        mTextureId = ShaderHelper.bitmap2Texture(bitmap);
        mProgram = ShaderHelper.createProgram();
        fillVertexBuffer();

        //---vertex index start
        ByteBuffer cubicIndex = ByteBuffer.allocateDirect(mCubicIndex.length * 4);
        cubicIndex.order(ByteOrder.nativeOrder());
        mVertextIndexBuffer = cubicIndex.asIntBuffer();
        mVertextIndexBuffer.put(mCubicIndex);
        mVertextIndexBuffer.position(0);
        //---vertex index end

    }

    private void fillVertexBuffer() {
        //-----vertex start
        float y = 0;
        float x = 0;
        float mBitmapRation = (float) (mBitmapWidth * 1.0 / mBitmapHeight);

        Log.i(TAG, "mRation: " + mRation + " mBitmapRation: " + mBitmapRation);
        if (mRation < mBitmapRation) {
            x = 2 * mRation;
            y = 2 * mRation / mBitmapRation;
        } else {
            x = 2 * mBitmapRation;
            y = 2;
        }

        Log.i(TAG, "x: " + x + " y: " + y);

        x = x / 2;
        y = y / 2;
//        Log.i(TAG, "x_: " + x + " y_: " + y);

        mCubicVertex[0] = mCubicVertex[0] * x;
        mCubicVertex[1] = mCubicVertex[1] * y;

        mCubicVertex[3] = mCubicVertex[3] * x;
        mCubicVertex[4] = mCubicVertex[4] * y;

        mCubicVertex[6] = mCubicVertex[6] * x;
        mCubicVertex[7] = mCubicVertex[7] * y;


        mCubicVertex[9] = mCubicVertex[9] * x;
        mCubicVertex[10] = mCubicVertex[10] * y;
//
//        mCubicVertex[12] = mCubicVertex[12] * x;
//        mCubicVertex[13] = mCubicVertex[13] * y;
//
//        mCubicVertex[15] = mCubicVertex[15] * x;
//        mCubicVertex[16] = mCubicVertex[16] * y;

        ByteBuffer cubicVertex = ByteBuffer.allocateDirect(mCubicVertex.length * 4);
        cubicVertex.order(ByteOrder.nativeOrder());
        mVertextFloatBuffer = cubicVertex.asFloatBuffer();
        mVertextFloatBuffer.put(mCubicVertex);
        mVertextFloatBuffer.position(0);
        //-----vertex end

        //-----texture start
        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(mTexture.length * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = textureBuffer.asFloatBuffer();
        mTextureBuffer.put(mTexture);
        mTextureBuffer.position(0);
        //-----texture end
        //-----landscape texture start

        ByteBuffer landscapeTextureBuffer = ByteBuffer.allocateDirect(mLandscapeTexture.length * 4);
        landscapeTextureBuffer.order(ByteOrder.nativeOrder());
        mLandscapeTextureBuffer = landscapeTextureBuffer.asFloatBuffer();
        mLandscapeTextureBuffer.put(mLandscapeTexture);
        mLandscapeTextureBuffer.position(0);
        //-----landscape texture end
    }

    public void draw(float[] vmMatrix) {
        String beforelogs = GLES20.glGetProgramInfoLog(mProgram);
        Log.e(TAG, "before glUseProgram:" + beforelogs);
        GLES20.glUseProgram(mProgram);
        String logs = GLES20.glGetProgramInfoLog(mProgram);
        Log.e(TAG, "after glUseProgram:" + logs);
        ShaderHelper.checkGlError("glUseProgram");

        int positionHandler = GLES20.glGetAttribLocation(mProgram, "position");
        ShaderHelper.checkGlError("glGetAttribLocation");
        GLES20.glEnableVertexAttribArray(positionHandler);
        int vetextStride = 3 * 4;
        GLES20.glVertexAttribPointer(positionHandler, 3, GLES20.GL_FLOAT, false, vetextStride, mVertextFloatBuffer);
        ShaderHelper.checkGlError("vertex glVertexAttribPointer");

//        int textureHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoordinate");
//        GLES20.glEnableVertexAttribArray(textureHandler);
//        GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
//        ShaderHelper.checkGlError("texture glVertexAttribPointer");

        int colorHandler = GLES20.glGetUniformLocation(mProgram, "color");
        GLES20.glUniform4fv(colorHandler, 1, color, 0);
        ShaderHelper.checkGlError("glUniform4fv");

        int vmMatrixHandler = GLES20.glGetUniformLocation(mProgram, "mMatrix");
        GLES20.glUniformMatrix4fv(vmMatrixHandler, 1, false, vmMatrix, 0);
        ShaderHelper.checkGlError("glUniformMatrix4fv");

//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mCubicIndex.length, GLES20.GL_UNSIGNED_INT, mVertextIndexBuffer);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mCubicVertex.length / 3);
        ShaderHelper.checkGlError("glDrawElements");

        GLES20.glDisableVertexAttribArray(positionHandler);
//        GLES20.glDisableVertexAttribArray(textureHandler);
    }

}
