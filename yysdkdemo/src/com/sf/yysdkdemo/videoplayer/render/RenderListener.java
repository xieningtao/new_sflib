package com.sf.yysdkdemo.videoplayer.render;

public interface RenderListener {
    long KRenderInterval = 2000L;

    void renderStart();

    void renderStop();
}
