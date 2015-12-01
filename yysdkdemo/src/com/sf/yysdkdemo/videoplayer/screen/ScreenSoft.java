package com.sf.yysdkdemo.videoplayer.screen;

import android.opengl.GLES20;

import com.sf.yysdkdemo.videoplayer.glutil.VBO;
import com.sf.yysdkdemo.videoplayer.util.Image;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class ScreenSoft {
    private int mTexIdY = -1;
    private int mTexIdU = -1;
    private int mTexIdV = -1;

    private Coordinate mCoordinate = null;
    private VBO mPosVBO = null;
    private VBO mDrawListVBO = null;

    private int mStride;
    private int mTexOffset;

    private TextureRecord mTexY = null;
    private TextureRecord mTexU = null;
    private TextureRecord mTexV = null;

    //vertex is 3 + 2 position texture coordinate
    public ScreenSoft(float[] vertex, int positionVertexHandle, int textureVertexHandle) {
        mStride = (3 + 2) * 4;
        mTexOffset = 3 * 4;

        mCoordinate = new Coordinate(vertex);

        short drawOrder[] = {0, 1, 2, 2, 3, 0};
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer drawList = dlb.asShortBuffer();
        drawList.put(drawOrder);
        drawList.position(0);

        mPosVBO = new VBO(GLES20.GL_ARRAY_BUFFER, mCoordinate.getBufferSize(),
                mCoordinate.mVertexBuffer, GLES20.GL_DYNAMIC_DRAW);

        mDrawListVBO = new VBO(GLES20.GL_ELEMENT_ARRAY_BUFFER, drawOrder.length * 2, drawList, GLES20.GL_STATIC_DRAW);

        GLES20.glEnableVertexAttribArray(positionVertexHandle);
        GLES20.glEnableVertexAttribArray(textureVertexHandle);

        createTextures();

        mTexY = new TextureRecord(0, 0, GLES20.GL_LUMINANCE);
        mTexU = new TextureRecord(0, 0, GLES20.GL_LUMINANCE);
        mTexV = new TextureRecord(0, 0, GLES20.GL_LUMINANCE);
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

    public void updateRenderSizeIfNeed(int width, int height, int imageWidth) {
        if (mCoordinate.applyRatioIfNeed(width, height, imageWidth)) {
            mPosVBO.subBuffer(GLES20.GL_ARRAY_BUFFER, mCoordinate.getBufferSize(), mCoordinate.mVertexBuffer);
        }
    }

    public void updateY(int width, int height, int format, int type, Buffer data) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        mTexY.update(width, height, format, type, data);
    }

    public void updateU(int width, int height, int format, int type, Buffer data) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        mTexU.update(width, height, format, type, data);
    }

    public void updateV(int width, int height, int format, int type, Buffer data) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        mTexV.update(width, height, format, type, data);
    }

    public void refresh(int positionVertexHandle, int textureVertexHandle, int samplerY, int samplerU, int samplerV) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mPosVBO.getId());

        GLES20.glVertexAttribPointer(positionVertexHandle, 3, GLES20.GL_FLOAT, false, mStride, 0);
        GLES20.glVertexAttribPointer(textureVertexHandle, 2, GLES20.GL_FLOAT, false, mStride, mTexOffset);

        GLES20.glUniform1i(samplerY, 0);
        GLES20.glUniform1i(samplerU, 1);
        GLES20.glUniform1i(samplerV, 2);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mDrawListVBO.getId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void destroy(int positionVertexHandle, int textureVertexHandle) {
        GLES20.glDisableVertexAttribArray(positionVertexHandle);
        GLES20.glDisableVertexAttribArray(textureVertexHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        deleteTexture(mTexIdY);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        deleteTexture(mTexIdU);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
        deleteTexture(mTexIdV);

        mPosVBO.delete();
        mDrawListVBO.delete();

        mTexIdY = mTexIdU = mTexIdV = -1;
    }

    private void createTextures() {
        int ids[] = new int[3];

        GLES20.glGenTextures(3, ids, 0);

        mTexIdY = ids[0];
        mTexIdU = ids[1];
        mTexIdV = ids[2];

        int GL_TEXTURES[] = {GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE1, GLES20.GL_TEXTURE2};
        for (int i = 0; i < 3; ++i) {
            GLES20.glActiveTexture(GL_TEXTURES[i]);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, ids[i]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        }
    }

    private void deleteTexture(int id) {
        if (-1 != id) {
            int ids[] = new int[1];
            ids[0] = id;
            GLES20.glDeleteTextures(1, ids, 0);
        }
    }

    private class TextureRecord {
        public TextureRecord(int width, int height, int format) {
            mWidth = width;
            mHeight = height;
            mFormat = format;
        }

        public int mWidth;
        public int mHeight;
        public int mFormat;

        public void update(int width, int height, int format, int type, Buffer data) {
            if (width == mWidth && height == mHeight && format == mFormat) {
                GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, width, height, format, type, data);
                return;
            }

            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, format, width, height, 0, format, type, data);

            mWidth = width;
            mHeight = height;
            mFormat = format;
        }
    }
}
