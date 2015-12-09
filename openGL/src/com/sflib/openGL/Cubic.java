package com.sflib.openGL;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by xieningtao on 15-10-31.
 */
public class Cubic {

    //    private final String mVertexShader =
//            "uniform mat4 vmMatrix;" +
//                    "attribute vec4 position;" +
//                    "void main() {" +
//                    " gl_Position=vmMatrix*position;" +
//                    "}";
//    private final String mFragmentShader =
//            "precision mediump float;" +
//                    "uniform vec4 color;" +
//                    "void main {" +
//                    " gl_FragColor=color;" +
//                    "}";
//
    private final String mVertexShader =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 mMatrix;" +
                    " attribute vec3 position;" +
                    " attribute vec2 aTextureCoordinate;" +
                    " varying vec2 vTextureCoordinate;" +
                    " void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = mMatrix * vec4(position,1); " +
                    "  vTextureCoordinate = aTextureCoordinate; " +
                    "}";

    private final String mFragmentShader =
            "precision highp float;" +
                    " varying vec2 vTextureCoordinate;" +
                    " uniform sampler2D sTexture;" +
                    " void main() {" +
                    "  gl_FragColor = texture2D(sTexture,vTextureCoordinate);" +
                    " }";

    private final String TAG = "Cubic";

    private int mProgram;
    private FloatBuffer mVertextFloatBuffer;
    private FloatBuffer mTextureBuffer;
    private FloatBuffer mLandscapeTextureBuffer;

    private final float mCubicVertex[] = {
            1.0f, -1.0f, 0.5f, -1.0f, 1.0f, 0.5f, 1.0f, 1.0f, 0.5f,
            -1.0f, 1.0f, 0.5f, -1.0f, -1.0f, 0.5f, 1.0f, -1.0f, 0.5f,
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


    float color[] = {0.5f, 1.0f, 1.0f, 1.0f};

    private final int floatSize = 4;

    private final Context mContext;

    private int mTextureId;

    public Cubic(Context context) {
        this.mContext = context;
        mTextureId = initTexture();
        initProgram();
        fillVertexBuffer();

        //---vertex index start
//        ByteBuffer cubicIndex = ByteBuffer.allocateDirect(mCubicIndex.length * 4);
//        cubicIndex.order(ByteOrder.nativeOrder());
//        mVertextIndexBuffer = cubicIndex.asIntBuffer();
//        mVertextIndexBuffer.put(mCubicIndex);
//        mVertextIndexBuffer.position(0);
        //---vertex index end

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private int initTexture() {
        int textureId[] = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.textrue_test);

//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        int count = bitmap.getByteCount();
//        ByteBuffer bitmapBuffer = ByteBuffer.allocateDirect(count);
//        bitmapBuffer.order(ByteOrder.nativeOrder());
//        bitmap.copyPixelsToBuffer(bitmapBuffer);
//        bitmapBuffer.position(0);
//        GLES20.glTexImage2D(GLES20.GL_TEXTURE0, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_RGBA, bitmapBuffer);
//        ShaderHelper.checkGlError("glTexImage2D");
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        ShaderHelper.checkGlError("texImage2D");
        bitmap.recycle();
        return textureId[0];
    }

    private void fillVertexBuffer() {
        //-----vertex start
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

    private void initProgram() {
        //-----prepare shader program
        mProgram = GLES20.glCreateProgram();
        int vertexShader = ShaderHelper.loadShader(GLES20.GL_VERTEX_SHADER, mVertexShader);
        ShaderHelper.checkGlError("loadVertexShader");
        int fragmentShader = ShaderHelper.loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentShader);
        String fragmentShaderLogs = GLES20.glGetShaderInfoLog(fragmentShader);
        Log.e(TAG, "fragmentShaderLogs: " + fragmentShaderLogs);
        ShaderHelper.checkGlError("loadFragmentShader");
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        String linkLogs = GLES20.glGetProgramInfoLog(mProgram);
        Log.e(TAG, "linkLogs: " + linkLogs);
        ShaderHelper.checkGlError("linkProgram");
        //-----edn shader
    }

    public void draw(float[] vmMatrix, boolean fullMode) {
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
        if (fullMode) {
            GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
        } else {
            GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
        }
        ShaderHelper.checkGlError("texture glVertexAttribPointer");

        int colorHandler = GLES20.glGetUniformLocation(mProgram, "color");
        GLES20.glUniform4fv(colorHandler, 1, color, 0);
        ShaderHelper.checkGlError("glUniform4fv");

        float resultm[] = new float[4 * 4];
//        if (fullMode) {
//            float rm[] = new float[4 * 4];
//            Matrix.setRotateM(rm, 0, 90, 0, 0, 1);
//            Matrix.multiplyMM(resultm, 0, vmMatrix, 0, rm, 0);
//        } else {
//            resultm = vmMatrix;
//        }
        resultm=vmMatrix;
        int vmMatrixHandler = GLES20.glGetUniformLocation(mProgram, "mMatrix");
        GLES20.glUniformMatrix4fv(vmMatrixHandler, 1, false, resultm, 0);
        ShaderHelper.checkGlError("glUniformMatrix4fv");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mCubicIndex.length, GLES20.GL_INT, mVertextIndexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mCubicVertex.length / 3);
        ShaderHelper.checkGlError("glDrawElements");

        GLES20.glDisableVertexAttribArray(positionHandler);
        GLES20.glDisableVertexAttribArray(textureHandler);
    }

}
