package com.sflib.CustomView.viewevent.listener;

import android.view.MotionEvent;

/**
 * Created by xieningtao on 15-10-31.
 */
public interface BaseViewEventListener {
    boolean onTouchEvent(MotionEvent event);

    boolean dispatchTouchEvent(MotionEvent event);
}
