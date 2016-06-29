package com.sflib.CustomView.viewevent;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-10-31.
 */
public class EventHandlerManager {
    private List<Integer> mHandleEvents = new ArrayList<Integer>();

    public EventHandlerManager() {

    }

    public void setHandlerEvents(List<Integer> handleEvents) {
        mHandleEvents.clear();
        if (handleEvents == null) return;
        mHandleEvents.addAll(handleEvents);
    }

    public boolean handleEvent(MotionEvent event) {
        if (event == null) return false;
        final int action = event.getAction();
        for (int i = 0; i < mHandleEvents.size(); i++) {
            if (mHandleEvents.get(i) == action) return true;
        }
        return false;

    }

}
