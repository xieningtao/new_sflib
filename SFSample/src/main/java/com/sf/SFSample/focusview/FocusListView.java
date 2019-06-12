package com.sf.SFSample.focusview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ListView;

import com.sf.loglib.L;

/**
 * Created by xieningtao on 16-3-17.
 */
public class FocusListView extends ListView {
    private final String TAG = getClass().getName();

    public FocusListView(Context context) {
        super(context);
    }

    public FocusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handle = super.dispatchKeyEvent(event);
        L.info(TAG, "method->dispatchKeyEvent,handle: " + handle);
        return handle;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 19) {
            return false;
        }else {
            boolean handle = super.onKeyDown(keyCode, event);
            L.info(TAG, "method->onKeyDown,focusListView handle: " + handle);
            return handle;
        }
    }
}
