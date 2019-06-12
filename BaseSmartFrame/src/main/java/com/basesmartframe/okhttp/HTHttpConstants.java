package com.basesmartframe.okhttp;

import android.os.Build;


/**
 * Created by yuhengye g10475 on 2018/5/29.
 **/
public class HTHttpConstants {


    public static final String IGNORE_TOKEN = "ignore_token";
    public static final String ACCESS_TOKEN = "access_token";

    public static final String HEADER_IGNORE_TOKEN = IGNORE_TOKEN + ": true";

    private static String sUserAgent = null;
    public static String getUserAgent() {
        if (sUserAgent == null) {
            String release = Build.VERSION.RELEASE == null ? "unknown" : Build.VERSION.RELEASE;
            String manufacturer = Build.MANUFACTURER == null ? "unknown" : Build.MANUFACTURER;
            sUserAgent = "App Android " + release + " " + manufacturer;
        }
        return sUserAgent;
    }
}
