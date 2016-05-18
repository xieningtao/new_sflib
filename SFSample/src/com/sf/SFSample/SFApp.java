package com.sf.SFSample;

import com.basesmartframe.baseapp.BaseApp;
import com.example.androidtv.module.BaseModule;
import com.example.androidtv.module.home.TVGameModule;

public class SFApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        startModule();
    }

    private void startModule() {
        BaseModule module = new TVGameModule();
        module.onStart();
    }
}