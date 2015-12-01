package com.sf.yysdkdemo.videoplayer.glutil;

import android.opengl.GLES20;

public class FBO {
    public static final int TYPE_RENDER_BUFFER = 0;
    public static final int TYPE_TEXTURE = 1;

    private static final int InValidId = -1;

    private int mFrameBufferId = InValidId;
    private int mRenderBufferId = InValidId;
    private int mTextureId = InValidId;

    public static FBO createFBO(int width, int height, int type) {
        int[] maxBufferSize = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_RENDERBUFFER_SIZE, maxBufferSize, 0);

        if (maxBufferSize[0] <= width || maxBufferSize[0] <= height) {
            return null;
        }

        FBO fbo = new FBO(width, height, type);
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        if (GLES20.GL_FRAMEBUFFER_COMPLETE != status) {
            fbo.delete();
            fbo = null;
        }

        return fbo;
    }

    private FBO(int width, int height, int type) {
        if (TYPE_TEXTURE == type) {
            createFrameTexture(width, height);
        } else if (TYPE_RENDER_BUFFER == type) {
            createRenderBuffer(width, height);
        }

        createFrameBuffer(type);
    }

    public int getFrameTextureId() {
        return mTextureId;
    }

    public void bind() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);
    }

    public void unBind() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    public void delete() {
        if (InValidId != mTextureId) {
            int[] textureId = new int[1];
            textureId[0] = mTextureId;
            GLES20.glDeleteTextures(1, textureId, 0);
            mTextureId = InValidId;
        }

        if (InValidId != mRenderBufferId) {
            int[] renderBufferId = new int[1];
            renderBufferId[0] = mRenderBufferId;
            GLES20.glDeleteRenderbuffers(1, renderBufferId, 0);
            mRenderBufferId = InValidId;
        }

        if (InValidId != mFrameBufferId) {
            int[] frameBufferId = new int[1];
            frameBufferId[0] = mFrameBufferId;
            GLES20.glDeleteFramebuffers(1, frameBufferId, 0);
            mFrameBufferId = InValidId;
        }
    }

    private void createFrameBuffer(int type) {
        int[] ids = new int[1];
        GLES20.glGenFramebuffers(1, ids, 0);
        mFrameBufferId = ids[0];

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);

        if (TYPE_TEXTURE == type) {
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mTextureId, 0);
        } else if (TYPE_RENDER_BUFFER == type) {
            GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, mRenderBufferId);
        }

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    private void createFrameTexture(int width, int height) {
        int[] ids = new int[1];
        GLES20.glGenTextures(1, ids, 0);
        mTextureId = ids[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height,
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_SHORT_4_4_4_4, null);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    private void createRenderBuffer(int width, int height) {
        int[] renderIds = new int[1];
        GLES20.glGenRenderbuffers(1, renderIds, 0);
        mRenderBufferId = renderIds[0];

        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, mRenderBufferId);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0);

    }
}
