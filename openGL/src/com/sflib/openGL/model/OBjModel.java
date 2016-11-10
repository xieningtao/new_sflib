package com.sflib.openGL.model;

import android.opengl.GLES20;
import android.util.Log;

import com.sflib.openGL.ShaderHelper;

/**
 * Created by NetEase on 2016/11/8 0008.
 */

public class OBjModel {


    private final String TAG = "Cubic";

    private int mProgram;


    private final int floatSize = 4;


    private LoadMaxModelHelper mLoadOBJModelHelper;

    public OBjModel(LoadMaxModelHelper loadOBJModelHelper) {
        this.mLoadOBJModelHelper = loadOBJModelHelper;
        mProgram = ShaderHelper.createObjProgram();
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
        GLES20.glVertexAttribPointer(positionHandler, 3, GLES20.GL_FLOAT, false, vetextStride, mLoadOBJModelHelper.getBuffer(LoadOBJModelHelper.BUFFER_TYPE.BUFFER_TYPE_VERTEX));
        ShaderHelper.checkGlError("vertex glVertexAttribPointer");

//        int textureHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoordinate");
//        GLES20.glEnableVertexAttribArray(textureHandler);
//        GLES20.glVertexAttribPointer(textureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
//        ShaderHelper.checkGlError("texture glVertexAttribPointer");

//        int colorHandler = GLES20.glGetUniformLocation(mProgram, "color");
//        GLES20.glUniform4fv(colorHandler, 1, color, 0);
//        ShaderHelper.checkGlError("glUniform4fv");

        int vmMatrixHandler = GLES20.glGetUniformLocation(mProgram, "mMatrix");
        GLES20.glUniformMatrix4fv(vmMatrixHandler, 1, false, vmMatrix, 0);
        ShaderHelper.checkGlError("glUniformMatrix4fv");

//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mLoadOBJModelHelper.getNumObjectIndex(), GLES20.GL_INT, mLoadOBJModelHelper.getBuffer(LoadOBJModelHelper.BUFFER_TYPE.BUFFER_TYPE_INDICES));
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mLoadOBJModelHelper.getNumObjectVertex());
        ShaderHelper.checkGlError("glDrawElements");

        GLES20.glDisableVertexAttribArray(positionHandler);
//        GLES20.glDisableVertexAttribArray(textureHandler);
    }
}
