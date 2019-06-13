package com.sflib.CustomView.viewevent.view;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sflib.CustomView.viewevent.listener.ViewEventListener;
import com.sflib.CustomView.viewevent.listener.ViewLogMarkListener;


/**
 * Created by xieningtao on 15-10-31.
 */
public class MyTextView extends AppCompatTextView implements ViewLogMarkListener {

    private final String dispatchTouchLogMark = "----------MyTextView dispatchTouchEvent-----------";
    private final String onTouchLogMark = "----------MyTextView onTouchEvent-----------";
    private final String onTouchListenerLogMark = "----------MyTextView onTouchListenerEvent-----------";
    private ViewEventListener mEventListener;

    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mEventListener.onTouch(v, event);
            }
        });

    }

    public void setEventListener(ViewEventListener listener) {
        this.mEventListener = listener;
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isConsumed = false;
        if (mEventListener != null) {
            isConsumed = mEventListener.dispatchTouchEvent(ev);
        }

        return isConsumed || super.dispatchTouchEvent(ev);
    }

    @Override
    public String getOnTouchLogMark() {
        return onTouchLogMark;
    }

    @Override
    public String getDispatchTouchLogMark() {
        return dispatchTouchLogMark;
    }

    @Override
    public String getOnTouchListenerLogMak() {
        return onTouchListenerLogMark;
    }
}
