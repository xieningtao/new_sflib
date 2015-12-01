package com.basesmartframe.baseutil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.basesmartframe.baseapp.BaseApp;

/**
 * 网络状态的检测，目前只是包括是否联网，接下来应该包括
 * 1、联网的类型：wifi or 3g
 * 2、网速
 * 3、网络的质量
 *
 * @author xieningtao
 */
public class NetWorkManagerUtil {
    /**
     * 是否连接网络
     *
     * @deprecated please use {@link #isNetworkAvailable()}
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isAvailable = false;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                isAvailable = mNetworkInfo.isAvailable();
            }
        }
        return isAvailable;
    }

    public static boolean isNetworkAvailable() {
        return isNetworkAvailable(BaseApp.gContext);
    }

    /**
     * 描述:网络类型
     *
     * @author xiaoming.yuan
     * @ClassName: NetworkType
     * @date 2013-12-23 上午11:09:53
     */
    public static class NetworkType {

        public static final String UNKNOWN = "unknown";

        public static final String NET_2G = "2G";

        public static final String NET_3G = "3G";

        public static final String WIFI = "wifi";

        public static final String NET_CMNET = "cmnet";

        public static final String NET_CMWAP = "cmwap";
    }

    /**
     * @param context
     * @return String 返回类型
     * @Title: getNetworkType(获取当前的网络类型)
     * @author xiaoming.yuan
     * @data 2013-12-23 上午11:10:45
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            return NetworkType.UNKNOWN;
        }
        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.WIFI;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int netType = tm.getNetworkType();

        // 已知3G类型
        // NETWORK_TYPE_UMTS 3
        // NETWORK_TYPE_EVDO_0 5
        // NETWORK_TYPE_EVDO_A 6
        // NETWORK_TYPE_HSDPA 8
        // NETWORK_TYPE_HSUPA 9
        // NETWORK_TYPE_HSPA 10
        // NETWORK_TYPE_EVDO_B 12
        // NETWORK_TYPE_LTE 13
        // NETWORK_TYPE_EHRPD 14
        // NETWORK_TYPE_HSPAP 15

        // 已知2G类型
        // NETWORK_TYPE_GPRS 1
        // NETWORK_TYPE_EDGE 2
        // NETWORK_TYPE_CDMA 4
        // NETWORK_TYPE_1xRTT 7
        // NETWORK_TYPE_IDEN 11

        if (netType == TelephonyManager.NETWORK_TYPE_GPRS || netType == TelephonyManager.NETWORK_TYPE_EDGE || netType == TelephonyManager.NETWORK_TYPE_CDMA || netType == TelephonyManager.NETWORK_TYPE_1xRTT || netType == 11) {
            return NetworkType.NET_2G;
        }
        return NetworkType.NET_3G;
    }

    public static boolean isFreeNetwork(Context context) {
        String type = getNetworkType(context);
        if (NetworkType.NET_2G.equals(type) || NetworkType.NET_3G.equals(type)) {
            return false;
        } else {
            return true;
        }
    }


    public static void openNetworkConfig(Context c) {
        Intent i = null;
        if (android.os.Build.VERSION.SDK_INT > 10) {
            i = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            i = new Intent();
            i.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
            i.setAction(Intent.ACTION_MAIN);
        }
        try {
            c.startActivity(i);
        } catch (Exception e) {
        }
    }


}
