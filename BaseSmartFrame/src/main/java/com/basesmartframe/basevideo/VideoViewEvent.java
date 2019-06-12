package com.basesmartframe.basevideo;

/**
 * Created by xieningtao on 15-3-26.
 */
public interface VideoViewEvent {

    void onPrePare();
    void onComplete();
    void onError(int what, int error);
    void onSeekComplete();
}
