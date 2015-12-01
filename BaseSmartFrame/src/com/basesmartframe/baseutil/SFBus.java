package com.basesmartframe.baseutil;

import de.greenrobot.event.EventBus;

/**
 * Created by xieningtao on 15-11-15.
 */
public class SFBus {

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

}
