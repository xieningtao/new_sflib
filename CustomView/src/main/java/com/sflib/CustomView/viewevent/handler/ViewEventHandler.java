package com.sflib.CustomView.viewevent.handler;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sflib.CustomView.viewevent.EventHandlerManager;
import com.sflib.CustomView.viewevent.EventInfoUtil;
import com.sflib.CustomView.viewevent.listener.ViewEventListener;
import com.sflib.CustomView.viewevent.listener.ViewLogMarkListener;

import java.util.List;

/**
 * Created by xieningtao on 15-10-31.
 */
public class ViewEventHandler extends BaseViewEventHandler implements ViewEventListener {

    protected ViewLogMarkListener mMarkListener;
    private EventHandlerManager onTouchListenerHandler = new EventHandlerManager();

    public ViewEventHandler(ViewLogMarkListener listener) {
        this.mMarkListener = listener;
    }

    public void setOnTouchListenerHandlerEvent(List<Integer> handlerEvent) {
        onTouchListenerHandler.setHandlerEvents(handlerEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getOnTouchLogMark());
        boolean isConsumed = onTouchHandler.handleEvent(ev);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        return isConsumed;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getDispatchTouchLogMark());
        boolean isConsumed = dispatchTouchHandler.handleEvent(event);
        EventInfoUtil.printEventInfo(event, isConsumed);
        return isConsumed;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getOnTouchListenerLogMak());
        boolean isConsumed = onTouchListenerHandler.handleEvent(event);
        EventInfoUtil.printEventInfo(event, isConsumed);
        return isConsumed;
    }
}
