package com.sf.yysdkdemo.videoplayer.render;

import android.opengl.GLES20;

import com.duowan.mobile.mediaproxy.RenderFrameBuffer;
import com.sf.yysdkdemo.videoplayer.screen.ScreenSoft;
import com.sf.yysdkdemo.videoplayer.util.Image;

import java.nio.ByteBuffer;

public class Monitor {
    private ScreenSoft mScreen;

    public Monitor(int positionVertexHandle, int textureVertexHandle, float top, float bottom, float z) {
        float vertex[] = {
                -1.0f, top, z, 0.0f, 1.0f, // top left
                -1.0f, bottom, z, 0.0f, 0.0f, // bottom left
                1.0f, bottom, z, 1.0f, 0.0f, // bottom right
                1.0f, top, z, 1.0f, 1.0f// top right
        };

        mScreen = new ScreenSoft(vertex, positionVertexHandle, textureVertexHandle);
    }

    public void setSize(int width, int height) {
        mScreen.setSize(width, height);
    }

    public void setRenderFormat(int formatHandle, int format) {
        GLES20.glUniform1i(formatHandle, format);
    }

    public void updateRenderSizeIfNeed(int width, int height, int imageWidth) {
        mScreen.updateRenderSizeIfNeed(width, height, imageWidth);
    }

    public void updateAsRGB(int width, int height, int type, ByteBuffer data) {
        mScreen.updateY(width, height, GLES20.GL_RGB, type, data);
    }

    public void updateAsYUV(RenderFrameBuffer buffer) {
        int format = RenderFrameBuffer.FORMAT_NV12 == buffer.getFrameFormat() ?
                GLES20.GL_LUMINANCE_ALPHA : GLES20.GL_LUMINANCE;

        ByteBuffer frame = buffer.getFrame();

        mScreen.updateY(buffer.getWidthY(), buffer.getHeightY(),
                GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, frame.position(buffer.getOffsetY()));

        mScreen.updateU(buffer.getWidthUV(), buffer.getHeightUV(),
                format, GLES20.GL_UNSIGNED_BYTE, frame.position(buffer.getOffsetU()));

        mScreen.updateV(buffer.getWidthUV(), buffer.getHeightUV(),
                format, GLES20.GL_UNSIGNED_BYTE, frame.position(buffer.getOffsetV()));
    }

    public void refresh(int positionVertexHandle, int textureVertexHandle, int samplerY, int samplerU, int samplerV) {
        mScreen.refresh(positionVertexHandle, textureVertexHandle, samplerY, samplerU, samplerV);
    }

    public void destroy(int positionVertexHandle, int textureVertexHandle) {
        mScreen.destroy(positionVertexHandle, textureVertexHandle);
    }

    public void setScaleType(Image.ScaleType type) {
        mScreen.setScaleType(type);
    }
}
