package com.basesmartframe.baseutil;

import com.sflib.reflection.core.SFBridgeManager;

import de.greenrobot.event.EventBus;

/**
 * Created by xieningtao on 15-11-15.
 */
public class SFBus {
    public static void send(int messageId,Object... objects) {
        SFBridgeManager.send(messageId,objects);
    }

    public static void register(Object subscriber) {
        SFBridgeManager.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        SFBridgeManager.unregister(subscriber);
    }

}
