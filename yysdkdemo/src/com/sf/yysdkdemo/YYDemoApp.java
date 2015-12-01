package com.sf.yysdkdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by xieningtao on 15-11-9.
 */
public class YYDemoApp extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
