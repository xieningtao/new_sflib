package com.example.myapp.test;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by xieningtao on 16-2-27.
 */
public  class TestNetworkChange<T> extends ActivityInstrumentationTestCase2{

    public TestNetworkChange(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void toggleNetwork(boolean status) {
        Context context = getInstrumentation().getTargetContext();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(status);
    }
}
