package com.sf.SFSample;

import com.basesmartframe.baseapp.BaseApp;
import com.example.androidtv.module.BaseModule;
import com.example.androidtv.module.home.TVGameModule;
import com.sf.baidulib.SFBaiduLocationManager;

public class SFApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        startModule();
    }

    private void startModule() {
        BaseModule module = new TVGameModule();
        module.onStart();
    }

    private void init(){
        SFBaiduLocationManager.getInstance().init(getApplicationContext());
        SFBaiduLocationManager.getInstance().requestLocate();
    }
}