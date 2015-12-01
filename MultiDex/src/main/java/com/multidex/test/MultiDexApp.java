package com.multidex.test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import de.greenrobot.event.EventBus;

/**
 * Created by xieningtao on 15-9-8.
 */
public class MultiDexApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        new Runnable() {
//            public void run() {
//
//            }
//
//
//        }.run();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onEvent(String event) {

    }

}
