package com.basesmartframe.basevideo;

/**
 * Created by xieningtao on 15-11-15.
 */
public interface VideoViewAction {
    void play();

    void pauseOrStart(boolean pause);

    void seek(int position);

    void zoom(boolean zoomOut);

    void release();
}
