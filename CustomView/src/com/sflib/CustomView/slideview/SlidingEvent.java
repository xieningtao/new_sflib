package com.sflib.CustomView.slideview;

/**
 * Created by xieningtao on 15-11-8.
 */
public interface SlidingEvent {
    public static enum SlidingMode {
        LEFT_OPEN, LEFT_CLOSE, RIGHT_OPEN, RIGHT_CLOSE,NONE
    }

    void onSliding(SlidingMode mode);

    void onStartSliding(SlidingMode mode);

    void onFinishSliding(SlidingMode mode);
}
