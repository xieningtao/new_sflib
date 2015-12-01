package com.sflib.CustomView.viewevent.handler;


import com.sflib.CustomView.viewevent.EventHandlerManager;

import java.util.List;

/**
 * Created by xieningtao on 15-10-31.
 */
public class BaseViewEventHandler {
    protected EventHandlerManager onTouchHandler = new EventHandlerManager();
    protected EventHandlerManager dispatchTouchHandler = new EventHandlerManager();

    public BaseViewEventHandler() {

    }

    public void setOnTouchHandlerEvents(List<Integer> handlerEvents) {
        onTouchHandler.setHandlerEvents(handlerEvents);
    }

    public void setDispatchTouchHandlerEvents(List<Integer> handlerEvents) {
        dispatchTouchHandler.setHandlerEvents(handlerEvents);
    }
}
