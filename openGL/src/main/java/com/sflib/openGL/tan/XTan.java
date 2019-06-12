package com.sflib.openGL.tan;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.sflib.openGL.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by xieningtao on 16-3-27.
 */
public class XTan {

    private final String TAG = getClass().getName();

    private final float mVertex[] = {
            1.0f, -1.0f, 0.5f, -1.0f, 1.0f, 0.5f, 1.0f, 1.0f, 0.5f,
            -1.0f, 1.0f, 0.5f, -1.0f, -1.0f, 0.5f, 1.0f, -1.0f, 0.5f,
    };

    private final float mTexture[] = {
            1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f
    };

    private int mProgram;
    private int mTextureId;

    private final int mScreenWidth;
    private final int mScreenHeight;

    private final float mGlWidth;
    private final float mGlHeight;

    private float mXTanGLWidth;
    private float mXTanGLHeight;

    private float mXTanWidth;
    private float mXTanHeight;

    private FloatBuffer mVertextFloatBuffer;
    private FloatBuffer mTextureBuffer;


    public XTan(int screenWidth, int screenHeight, float glWidth, float glHeight) {

        this.mScreenWidth = screenWidth;
        this.mScreenHeight = screenHeight;

        this.mGlWidth = glWidth;
        this.mGlHeight = glHeight;

        init();


    }

    private void init() {
        Bitmap bitmap = Word2BitmapUtil.word2Bitmap("hello open gl", 0xff00ff00);
//        Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.opengl_16_9);
        mTextureId = ShaderHelper.bitmap2Texture(bitmap);
        mXTanHeight = bitmap.getHeight();
        mXTanWidth = bitmap.getWidth();
        setXTanVertex(bitmap.getWidth(), bitmap.getHeight());
        mProgram = ShaderHelper.createProgram();

    }

    private void setXTanVertex(int bitmapWidth, int bitmapHeight) {
        float tanGlWidth = mGlWidth * bitmapWidth / mScreenWidth * 1.0f;
        float tanGlHeight = mGlHeight * bitmapHeight / mScreenHeight * 1.0f;

        mXTanGLHeight = tanGlHeight;
        mXTanGLWidth = tanGlWidth;

        float tanGlX = tanGlWidth / 2.0f;
        float tanGlY = tanGlHeight / 2.0f;

        mVertex[0] = mVertex[0] * tanGlX;
        mVertex[1] = mVertex[1] * tanGlY;

        mVertex[3] = mVertex[3] * tanGlX;
        mVertex[4] = mVertex[4] * tanGlY;

        mVertex[6] = mVertex[6] * tanGlX;
        mVertex[7] = mVertex[7] * tanGlY;

        mVertex[9] = mVertex[9] * tanGlX;
        mVertex[10] = mVertex[10] * tanGlY;

        mVertex[12] = mVertex[12] * tanGlX;
        mVertex[13] = mVertex[13] * tanGlY;

        mVertex[15] = mVertex[15] * tanGlX;
        mVertex[16] = mVertex[16] * tanGlY;


        ByteBuffer cubicVertex = ByteBuffer.allocateDirect(mVertex.length * 4);
        cubicVertex.order(ByteOrder.nativeOrder());
        mVertextFloatBuffer = cubicVertex.asFloatBuffer();
        mVertextFloatBuffer.put(mVertex);
        mVertextFloatBuffer.position(0);
        //-----vertex end

        //-----texture start
        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(mTexture.length * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = textureBuffer.asFloatBuffer();
        mTextureBuffer.put(mTexture);
        mTextureBuffer.position(0);
        //-----texture end
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

        int textureHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoordinate");
        GLES20.glEnableVertexAttribArray(textureHandler);
        GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
        ShaderHelper.checkGlError("texture glVertexAttribPointer");

        int vmMatrixHandler = GLES20.glGetUniformLocation(mProgram, "mMatrix");
        GLES20.glUniformMatrix4fv(vmMatrixHandler, 1, false, vmMatrix, 0);
        ShaderHelper.checkGlError("glUniformMatrix4fv");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mCubicIndex.length, GLES20.GL_INT, mVertextIndexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertex.length / 3);
        ShaderHelper.checkGlError("glDrawElements");

        GLES20.glDisableVertexAttribArray(positionHandler);
        GLES20.glDisableVertexAttribArray(textureHandler);
    }

    public float[] getResetTranslation() {
        float resetTranslation[] = new float[16];
        Matrix.setIdentityM(resetTranslation, 0);
        float xOffset = mGlWidth / 2.0f + mXTanGLWidth / 2.0f;
        Matrix.translateM(resetTranslation, 0, xOffset, 0, 0);
        return resetTranslation;
    }

    public float[] updateXTanStep(float translation[], float step) {
        Matrix.translateM(translation, 0, step, 0, 0);
        return translation;
    }
}
