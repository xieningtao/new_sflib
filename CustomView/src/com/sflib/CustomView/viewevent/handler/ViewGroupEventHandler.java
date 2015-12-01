package com.sflib.CustomView.viewevent.handler;

import android.util.Log;
import android.view.MotionEvent;

import com.sflib.CustomView.viewevent.EventHandlerManager;
import com.sflib.CustomView.viewevent.EventInfoUtil;
import com.sflib.CustomView.viewevent.listener.ViewGroupEventListener;
import com.sflib.CustomView.viewevent.listener.ViewGroupLogMarkListener;

import java.util.List;

/**
 * Created by xieningtao on 15-10-31.
 */
public class ViewGroupEventHandler extends BaseViewEventHandler implements ViewGroupEventListener {

    protected ViewGroupLogMarkListener mMarkListener;
    private EventHandlerManager onInterceptHandler = new EventHandlerManager();

    public ViewGroupEventHandler(ViewGroupLogMarkListener listener) {
        if (listener == null) throw new NullPointerException("listener is null");
        this.mMarkListener = listener;
    }

    public void setOnInterceptHandlerEvents(List<Integer> handlerEvents) {
        onInterceptHandler.setHandlerEvents(handlerEvents);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getDispatchTouchLogMark());
        boolean isConsumed = dispatchTouchHandler.handleEvent(event);
        EventInfoUtil.printEventInfo(event, isConsumed);
        return isConsumed;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getOnInterceptLogMark());
        boolean isConsumed = onInterceptHandler.handleEvent(ev);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        return isConsumed;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(EventInfoUtil.TAG, mMarkListener.getOnTouchLogMark());
        boolean isConsumed = onTouchHandler.handleEvent(ev);
        EventInfoUtil.printEventInfo(ev, isConsumed);
        return isConsumed;
    }
}
