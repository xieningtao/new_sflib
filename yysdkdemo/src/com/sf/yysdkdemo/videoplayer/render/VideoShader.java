package com.sf.yysdkdemo.videoplayer.render;

import android.opengl.GLES20;

import com.duowan.mobile.mediaproxy.glutils.utils.CatchError;
import com.duowan.mobile.mediaproxy.glutils.utils.ShaderUtils;
import com.duowan.mobile.mediaproxy.glvideo.shader.soft.ShaderCode;
import com.yy.hiidostatis.inner.util.L;


public class VideoShader {
    protected int mProgram;
    protected int mPositionHandle;
    protected int mTextureHandle;
    protected int mMatrixHandle;
    protected int mFormat;
    protected int mSampleY;
    protected int mSampleU;
    protected int mSampleV;

    private int mVertexShaderHandle;
    private int mFragmentShaderHandle;

    public VideoShader() {
        init();
    }

    public void use() {
        GLES20.glUseProgram(mProgram);
        CatchError.catchError("YUV use");

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, ShaderCode.POSITION);
        mTextureHandle = GLES20.glGetAttribLocation(mProgram, ShaderCode.TEXTURE);
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, ShaderCode.MATRIX);
        mFormat = GLES20.glGetUniformLocation(mProgram, ShaderCode.FORMAT);
        mSampleY = GLES20.glGetUniformLocation(mProgram, ShaderCode.SAMPLER_Y);
        mSampleU = GLES20.glGetUniformLocation(mProgram, ShaderCode.SAMPLER_U);
        mSampleV = GLES20.glGetUniformLocation(mProgram, ShaderCode.SAMPLER_V);
        CatchError.catchError("YUV use2");
    }

    public void destroy() {
        ShaderUtils.deleteShaderProgram(mProgram, mVertexShaderHandle, mFragmentShaderHandle);
        CatchError.catchError("YUV destroy");

        mProgram = -1;
        mPositionHandle = -1;
        mTextureHandle = -1;
        mMatrixHandle = -1;
        mFormat = -1;
        mVertexShaderHandle = -1;
        mFragmentShaderHandle = -1;
        mSampleV = -1;
        mSampleY = -1;
        mSampleU = -1;
    }

    private void init() {
        CatchError.catchError("initVideoShaderYUV0");

        mVertexShaderHandle = ShaderUtils.compileShader(GLES20.GL_VERTEX_SHADER, ShaderCode.VSH_CODE);
        CatchError.catchError("initVideoShaderYUV1");

        mFragmentShaderHandle = ShaderUtils.compileShader(GLES20.GL_FRAGMENT_SHADER, ShaderCode.FSH_CODE);
        CatchError.catchError("initVideoShaderYUV2");

        mProgram = ShaderUtils.linkShader(mVertexShaderHandle, mFragmentShaderHandle);
        CatchError.catchError("initVideoShaderYUV3");

        ShaderUtils.validateShaderProgram(mProgram);
        CatchError.catchError("initVideoShaderYUV4");

        if (GLES20.GL_FALSE == mVertexShaderHandle || GLES20.GL_FALSE == mFragmentShaderHandle
                || GLES20.GL_FALSE == mProgram) {
            L.error("CatchError", "soft video shader init error vsh %d fsh %d program %d",
                    mVertexShaderHandle, mFragmentShaderHandle, mProgram);
            mVertexShaderHandle = mFragmentShaderHandle = mProgram = -1;
        }
    }
}
