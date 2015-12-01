package com.sflib.openGL.transform;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by xieningtao on 15-11-20.
 */
public class ScaleTranslateSurfaceView extends GLSurfaceView {
    public ScaleTranslateSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleTranslateSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        ScaleTranslateRender render=new ScaleTranslateRender(getContext());
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
