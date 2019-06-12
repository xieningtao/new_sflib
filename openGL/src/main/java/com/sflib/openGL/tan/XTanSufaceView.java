package com.sflib.openGL.tan;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by xieningtao on 16-3-27.
 */
public class XTanSufaceView extends GLSurfaceView {

    public XTanSufaceView(Context context) {
        super(context);
        init();
    }

    public XTanSufaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        setRenderer(new XTanRender());
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
