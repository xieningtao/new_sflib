package com.example.androidtv.module;

import com.sf.utils.baseutil.SFBus;

/**
 * Created by xieningtao on 15-12-23.
 */
public class BaseModule {

    public void onStart() {
        SFBus.register(this);
    }

    public void onStop() {
        SFBus.unregister(this);
    }
}
