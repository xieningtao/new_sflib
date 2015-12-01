package com.sf.yysdkdemo.videoplayer.screen;

import com.sf.yysdkdemo.videoplayer.util.Image;
import com.yy.hiidostatis.inner.util.L;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Coordinate {
    private static final int FLOAT_SIZE = 4;

    private int mWidth = 0;
    private int mHeight = 0;
    private int mDefSize = 0;

    private int mCurrentWidth = 0;
    private int mCurrentHeight = 0;
    private int mCurrentImageWidth = 0;

    private boolean mUnReSized = false;

    private float[] mVertex;

    private Image.ScaleType mScaleType = Image.ScaleType.Fit;

    protected FloatBuffer mVertexBuffer;

    protected Coordinate(float[] vertex) {
        mVertex = vertex;

        ByteBuffer bb = ByteBuffer.allocateDirect(mVertex.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(mVertex);
        mVertexBuffer.position(0);
    }

    protected int getBufferSize() {
        return mVertex.length * FLOAT_SIZE;
    }

    protected void setSize(int width, int height) {
        if (width == mWidth && height == mHeight) {
            return;
        }

        mUnReSized = false;
        mWidth = width;
        mHeight = height;
        mDefSize = mHeight;
    }

    protected void setScaleType(Image.ScaleType type) {
        if (type != mScaleType) {
            mUnReSized = false;
            mScaleType = type;
        }
    }

    protected boolean applySizeRatio() {
        return applyRatioIfNeed(mCurrentWidth, mCurrentHeight, mCurrentImageWidth);
    }

    protected boolean applyRatioIfNeed(int width, int height, int imageWidth) {
        if (mUnReSized && width == mCurrentWidth && height == mCurrentHeight && imageWidth == mCurrentImageWidth) {
            return false;
        }

        if (0 == mWidth || 0 == mHeight || 0 == mDefSize) {
            L.info("GLVideo_size", "width %d height %d DefSize %d zero", mWidth, mHeight, mDefSize);
            return false;
        }

        if (0 == imageWidth || 0 == height || 0 == width) {
            L.info("GLVideo_size", "imageWidth %d width %d height %d zero", imageWidth, width, height);
            return false;
        }

        mUnReSized = true;
        mCurrentWidth = width;
        mCurrentHeight = height;
        mCurrentImageWidth = imageWidth;

        float[] renderSize = new float[]{mWidth, mHeight};
        Image.scaleToW2H2(mScaleType, mCurrentImageWidth, mCurrentHeight, mWidth, mHeight, renderSize);

        float renderWidth = renderSize[0];
        float renderHeight = renderSize[1];

        float[] newPosVer = new float[mVertex.length];
        System.arraycopy(mVertex, 0, newPosVer, 0, mVertex.length);

        float wr = 1.0f * renderWidth / mDefSize;
        float hr = 1.0f * renderHeight / mDefSize;

        newPosVer[0] *= wr;
        newPosVer[5] *= wr;
        newPosVer[10] *= wr;
        newPosVer[15] *= wr;

        newPosVer[1] *= hr;
        newPosVer[6] *= hr;
        newPosVer[11] *= hr;
        newPosVer[16] *= hr;

        if (mCurrentImageWidth != mCurrentWidth) {
            float dif = 1.0f * (mCurrentWidth - mCurrentImageWidth) / mCurrentWidth;
            newPosVer[13] -= dif;
            newPosVer[18] -= dif;
        }

        mVertexBuffer.clear();
        ByteBuffer bb = ByteBuffer.allocateDirect(newPosVer.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(newPosVer);
        mVertexBuffer.position(0);

        L.info("GLVideo_size", "renderWidth " + renderWidth + " renderHeight " + renderHeight
                + " defSize " + mDefSize + " array " + Arrays.toString(newPosVer));

        return true;
    }
}
