package com.sflib.CustomView.viewevent.listener;

import android.view.MotionEvent;

/**
 * Created by xieningtao on 15-10-31.
 */
public interface ViewGroupEventListener extends BaseViewEventListener {

    boolean onInterceptTouchEvent(MotionEvent event);

}
