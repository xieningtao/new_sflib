package com.sflib.CustomView.viewevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.sflib.CustomView.viewevent.listener.ViewGroupEventListener;
import com.sflib.CustomView.viewevent.listener.ViewGroupLogMarkListener;


/**
 * Created by xieningtao on 15-10-31.
 */
public class MyLinearLayout extends LinearLayout implements ViewGroupLogMarkListener {

    private final String dispatchTouchLogMark = "----------MyLinearLayout dispatchTouchEvent-----------";
    private final String onTouchLogMark = "----------MyLinearLayout onTouchEvent-----------";
    private final String onInterceptLogMark = "----------MyLinearLayout onInterceptEvent-----------";

    private ViewGroupEventListener mEventListener;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEventListener(ViewGroupEventListener listener) {
        this.mEventListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isConsumed = false;
        if (mEventListener != null) {
            isConsumed = mEventListener.dispatchTouchEvent(ev);
        }

        return isConsumed || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isConsumed = false;
        if (mEventListener != null) {
            isConsumed = mEventListener.onInterceptTouchEvent(ev);
        }
        return isConsumed || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isConsumed = false;
        if (mEventListener != null) {
            isConsumed = mEventListener.onTouchEvent(ev);
        }
        return isConsumed || super.onTouchEvent(ev);
    }

    @Override
    public String getDispatchTouchLogMark() {
        return dispatchTouchLogMark;
    }

    @Override
    public String getOnTouchLogMark() {
        return onTouchLogMark;
    }

    @Override
    public String getOnInterceptLogMark() {
        return onInterceptLogMark;
    }
}
