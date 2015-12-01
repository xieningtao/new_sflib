package com.sflib.reflection.core;

/**
 * Created by xieningtao on 15-10-24.
 */
public class SFBridgeManager {
    private static final SFBridge mBridge = new SFBridge();

    public static void register(final Object object) {
        mBridge.registerMethod(object);
    }

    public static void unregister(final Object object) {
        mBridge.unregisterMethod(object);
    }

    public static void send(final Integer messageId, Object ...params) {
        mBridge.sendMessage(messageId, params);
    }
}
