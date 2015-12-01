package com.sf.yysdkdemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by xieningtao on 15-11-9.
 */
public class EmptyGLSurfaceView extends GLSurfaceView {

    public EmptyGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public EmptyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new EmptyGLRender());
    }

    class EmptyGLRender implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d("EmptyGLSurfaceView", String.valueOf(Thread.currentThread().getId()));
        }
    }
}
