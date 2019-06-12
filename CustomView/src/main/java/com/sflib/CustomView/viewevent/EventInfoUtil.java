package com.sflib.CustomView.viewevent;

import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by xieningtao on 15-10-31.
 */
public class EventInfoUtil {
    public static final String TAG = EventInfoUtil.class.getName();

    private static final String ACTION_DOWN = "ACTION_DOWN";
    private static final String ACTION_MOVE = "ACTION_MOVE";
    private static final String ACTION_UP = "ACTION_UP";
    private static final String ACTION_CANCEL = "ACTION_CANCEL";


    public static void printEventInfo(MotionEvent event, boolean isConsumed) {
        if (event == null) return;
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                printEventLogs(ACTION_DOWN, isConsumed);
                break;
            case MotionEvent.ACTION_MOVE:
                printEventLogs(ACTION_MOVE, isConsumed);
                break;
            case MotionEvent.ACTION_UP:
                printEventLogs(ACTION_UP, isConsumed);
                break;
            case MotionEvent.ACTION_CANCEL:
                printEventLogs(ACTION_CANCEL, isConsumed);
                break;
        }
    }

    private static void printEventLogs(String event, boolean isConsumed) {
        if (isConsumed) {
            Log.i(TAG, "event: " + event + "----isConsumed: " + isConsumed + "----");
        } else {
            Log.i(TAG, "event: " + event + "%%%%isConsumed: " + isConsumed + "%%%%");
        }
    }
}
