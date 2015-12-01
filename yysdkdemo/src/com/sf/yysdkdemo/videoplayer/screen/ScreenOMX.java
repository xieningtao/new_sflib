package com.sf.yysdkdemo.videoplayer.screen;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.sf.yysdkdemo.videoplayer.glutil.VBO;
import com.sf.yysdkdemo.videoplayer.util.Image;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class ScreenOMX {
    private Coordinate mCoordinate = null;
    private VBO mPosVBO = null;
    private VBO mDrawListVBO = null;

    private int mStride;
    private int mTexOffset;

    private int mTextureId = -1;

    //vertex is 3 + 2 position texture coordinate
    public ScreenOMX(float[] vertex, int positionVertexHandle, int textureVertexHandle) {
        mStride = (3 + 2) * 4;
        mTexOffset = 3 * 4;

        mCoordinate = new Coordinate(vertex);

        createVBO();
        createTexture();

        GLES20.glEnableVertexAttribArray(positionVertexHandle);
        GLES20.glEnableVertexAttribArray(textureVertexHandle);
    }

    public void setSize(int width, int height) {
        mCoordinate.setSize(width, height);
        if (mCoordinate.applySizeRatio()) {
            mPosVBO.subBuffer(GLES20.GL_ARRAY_BUFFER, mCoordinate.getBufferSize(), mCoordinate.mVertexBuffer);
        }
    }

    public void setScaleType(Image.ScaleType type) {
        mCoordinate.setScaleType(type);
    }

    public void updateRenderSizeIfNeed(int width, int height) {
        if (mCoordinate.applyRatioIfNeed(width, height, width)) {
            mPosVBO.subBuffer(GLES20.GL_ARRAY_BUFFER, mCoordinate.getBufferSize(), mCoordinate.mVertexBuffer);
        }
    }

    public void draw(int positionVertexHandle, int textureVertexHandle) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mPosVBO.getId());
        GLES20.glVertexAttribPointer(positionVertexHandle, 3, GLES20.GL_FLOAT, false, mStride, 0);
        GLES20.glVertexAttribPointer(textureVertexHandle, 2, GLES20.GL_FLOAT, false, mStride, mTexOffset);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mDrawListVBO.getId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public int getTextureId() {
        return mTextureId;
    }

    public void destroy(int positionVertexHandle, int textureVertexHandle) {
        GLES20.glDisableVertexAttribArray(positionVertexHandle);
        GLES20.glDisableVertexAttribArray(textureVertexHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        int ids[] = new int[1];
        ids[0] = mTextureId;
        GLES20.glDeleteTextures(1, ids, 0);

        mPosVBO.delete();
        mDrawListVBO.delete();

        mTextureId = -1;
    }

    private void createVBO() {
        short drawOrder[] = {0, 1, 2, 2, 3, 0};
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer drawList = dlb.asShortBuffer();
        drawList.put(drawOrder);
        drawList.position(0);

        mPosVBO = new VBO(GLES20.GL_ARRAY_BUFFER, mCoordinate.getBufferSize(),
                mCoordinate.mVertexBuffer, GLES20.GL_DYNAMIC_DRAW);

        mDrawListVBO = new VBO(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrder.length * 2, drawList, GLES20.GL_STATIC_DRAW);
    }

    private void createTexture() {
        int[] texId = new int[1];

        GLES20.glGenTextures(1, texId, 0);
        mTextureId = texId[0];

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureId);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    }
}
