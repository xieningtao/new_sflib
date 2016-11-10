package com.sflib.openGL.model;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by NetEase on 2016/11/8 0008.
 */

public class XModelSurfaceView extends GLSurfaceView {
    private XModelRender mXModelRender;
    private LoadMaxModelHelper helper;

    public XModelSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XModelSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
    }

    public void setHelper(LoadMaxModelHelper helper) {
        mXModelRender = new XModelRender(getContext(), helper);
        setRenderer(mXModelRender);
    }
}
