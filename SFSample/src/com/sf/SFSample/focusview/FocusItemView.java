package com.sf.SFSample.focusview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.sf.loglib.L;

/**
 * Created by xieningtao on 16-3-17.
 */
public class FocusItemView extends LinearLayout {
    private final String TAG = getClass().getName();

    public FocusItemView(Context context) {
        super(context);
    }

    public FocusItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handle = super.dispatchKeyEvent(event);
        L.info(TAG, "method->dispatchKeyEvent,FocusItemView handle: " + handle);

        return handle;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handle = super.onKeyDown(keyCode, event);
        L.info(TAG, "method->onKeyDown,FocusItemView handle: " + handle);

        
        return handle;
    }
}
