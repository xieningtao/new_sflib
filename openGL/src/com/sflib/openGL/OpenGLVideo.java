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
public class OpenGLVideo {


    private final String TAG = "OpenGLVideo";

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
//            1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
//            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f

            1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
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

    private final float mNearPanleRation;

    private int mModelWidth;
    private int mModelHeight;

    public static final int FULL_SCREEN = 1;
    public static final int FILL_FIT = 0;
    private int mScaleType = 0;
    private float mCameraModelDistance = 0.0f;
    private float mNearDistance = 0.0f;

    public OpenGLVideo(Context context, final int screenWidth, final int screenHeight, final float nearDistance, final float eyez) {
        this.mContext = context;
        this.mNearPanleRation = (float) ((screenWidth * 1.0) / (screenHeight * 1.0));
        mModelHeight = 2;
        mModelWidth = 2;
        mCameraModelDistance = eyez - 0.5f;
        mNearDistance = nearDistance;

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

    private float getSign(float value) {
        return value / Math.abs(value);
    }

    /**
     * 主要思路如下：
     * 1、获取图片本身的高宽比例
     * 2、获取model的高宽比例
     * 3、比较他们的高宽比例，然后按照图片的高宽等比缩放model的高宽，这样可以保证纹理帖上去不失真，是等比的
     * 4、再按照摄像头，近平面，model平面的近似关系，进行等比缩放，达到充满屏幕的目的
     */
    private void fillVertexBuffer() {
        updateVertex();
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

    public void updateVideoViewScaleType(int scaleType) {
        if (scaleType == FILL_FIT || scaleType == FULL_SCREEN) {
            if (mScaleType != scaleType) {
                mScaleType = scaleType;
                updateVertex();
            }
        }
    }

    private void updateVertex() {
        //-----vertex start
        float y = 0;
        float x = 0;

        //获取图片的高宽比例
        float mBitmapRation = (float) (mBitmapWidth * 1.0 / mBitmapHeight);
        Log.i(TAG, "mNearPanleRation: " + mNearPanleRation + " mBitmapRation: " + mBitmapRation);

        //按照图片的高宽比例，求得model缩放以后的大小
        if (1 < mBitmapRation) {
            x = mModelWidth/2;
            y = mModelWidth * 1.0f / mBitmapRation;
            y = y / 2;
        } else {
            x = mModelHeight * mBitmapRation * 1.0f;
            x = x / 2;
            y = mModelHeight/2;
        }
        Log.i(TAG, "x: " + x + " y: " + y);

        mCubicVertex[0] = getSign(mCubicVertex[0]) * x;
        mCubicVertex[1] = getSign(mCubicVertex[1]) * y;

        mCubicVertex[3] = getSign(mCubicVertex[3]) * x;
        mCubicVertex[4] = getSign(mCubicVertex[4]) * y;

        mCubicVertex[6] = getSign(mCubicVertex[6]) * x;
        mCubicVertex[7] = getSign(mCubicVertex[7]) * y;

        mCubicVertex[9] = getSign(mCubicVertex[9]) * x;
        mCubicVertex[10] = getSign(mCubicVertex[10]) * y;

        float scale = getScale(x, y);

        mCubicVertex[0] = mCubicVertex[0] * scale;
        mCubicVertex[1] = mCubicVertex[1] * scale;

        mCubicVertex[3] = mCubicVertex[3] * scale;
        mCubicVertex[4] = mCubicVertex[4] * scale;

        mCubicVertex[6] = mCubicVertex[6] * scale;
        mCubicVertex[7] = mCubicVertex[7] * scale;


        mCubicVertex[9] = mCubicVertex[9] * scale;
        mCubicVertex[10] = mCubicVertex[10] * scale;

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
    }

    private float getScale(float realWidth, float realHeight) {
        //再按照摄像头，近平面，model平面的近似关系，进行等比缩放，达到充满屏幕的目的

        //首先判断是缩放的类型，1、FULLSCREEN(铺满屏幕) 2、FILL_WIDTH_OR_HEIGHT(只是)
        float realRation = realWidth / realHeight;
        float scale = 1.0f;
        if (mScaleType == FILL_FIT) {
            //判断高宽比，用来定位是缩放宽还是高
            if (realRation > mNearPanleRation) {//放大相同的倍数，real的宽度太宽，因此要求出新的宽度，下面的情况同理
                scale = (mCameraModelDistance * mNearPanleRation / mNearDistance) / Math.abs(realWidth);
            } else {
                scale = (mCameraModelDistance * 1 / mNearDistance) / Math.abs(realHeight);
            }
        } else if (mScaleType == FULL_SCREEN) {
            if (realRation > mNearPanleRation) {
                scale = (mCameraModelDistance * 1 / mNearDistance) / Math.abs(realHeight);
            } else {
                scale = (mCameraModelDistance * mNearPanleRation / mNearDistance) / Math.abs(realWidth);
            }
        }
        return scale;
    }

    public void draw(float[] vmMatrix) {
        String beforelogs = GLES20.glGetProgramInfoLog(mProgram);
        Log.e(TAG, "before glUseProgram:" + beforelogs);
        GLES20.glUseProgram(mProgram);
        String logs = GLES20.glGetProgramInfoLog(mProgram);
        Log.e(TAG, "after glUseProgram:" + logs);
        ShaderHelper.checkGlError("glUseProgram");

        //传递顶点坐标
        int positionHandler = GLES20.glGetAttribLocation(mProgram, "position");
        ShaderHelper.checkGlError("glGetAttribLocation");
        GLES20.glEnableVertexAttribArray(positionHandler);
        int vetextStride = 3 * 4;
        GLES20.glVertexAttribPointer(positionHandler, 3, GLES20.GL_FLOAT, false, vetextStride, mVertextFloatBuffer);
        ShaderHelper.checkGlError("vertex glVertexAttribPointer");

        //传递纹理坐标
        int textureHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoordinate");
        GLES20.glEnableVertexAttribArray(textureHandler);
        GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
        ShaderHelper.checkGlError("texture glVertexAttribPointer");

//        int colorHandler = GLES20.glGetUniformLocation(mProgram, "color");
//        GLES20.glUniform4fv(colorHandler, 1, color, 0);
//        ShaderHelper.checkGlError("glUniform4fv");

        //传递视图变换坐标
        int vmMatrixHandler = GLES20.glGetUniformLocation(mProgram, "mMatrix");
        GLES20.glUniformMatrix4fv(vmMatrixHandler, 1, false, vmMatrix, 0);
        ShaderHelper.checkGlError("glUniformMatrix4fv");

        //启用纹理，并默认传递纹理index给采样器
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mCubicIndex.length, GLES20.GL_UNSIGNED_INT, mVertextIndexBuffer);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mCubicVertex.length / 3);
        ShaderHelper.checkGlError("glDrawElements");

        GLES20.glDisableVertexAttribArray(positionHandler);
//        GLES20.glDisableVertexAttribArray(textureHandler);
    }

}
