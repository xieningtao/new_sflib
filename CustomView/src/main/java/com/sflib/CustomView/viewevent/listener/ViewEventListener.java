package com.sflib.CustomView.viewevent.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieningtao on 15-10-31.
 */
public interface ViewEventListener extends BaseViewEventListener {

    boolean onTouch(View v, MotionEvent event);

}
