package com.sf.SFSample;

import com.basesmartframe.baseapp.BaseApp;
import com.example.androidtv.module.BaseModule;
import com.example.androidtv.module.home.TVGameModule;
import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;
import com.sf.baidulib.SFBaiduLocationManager;
import com.sf.loglib.L;

public class SFApp extends BaseApp {
    public static final String APP_ID = "57f9edc887d4a7e337b8c231";
    public static final String APP_ID_KEY = "MmNsUDJONjlNc2xwNzEtbVY3RE5KUQ";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        startModule();
        doTest();
    }

    private void startModule() {
        BaseModule module = new TVGameModule();
        module.onStart();
    }

    private void init() {
        SFBaiduLocationManager.getInstance().init(getApplicationContext());
        SFBaiduLocationManager.getInstance().requestLocate();
        MaxLeap.initialize(this, APP_ID, APP_ID_KEY, MaxLeap.REGION_CN);
    }

    private void doTest() {
        MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            L.debug("MaxLeap", "SDK 成功连接到你的云端应用！");
                        } else {
                            L.debug("MaxLeap", "应用访问凭证不正确，请检查。exception: " + e);
                        }
                    }
                });
    }
}