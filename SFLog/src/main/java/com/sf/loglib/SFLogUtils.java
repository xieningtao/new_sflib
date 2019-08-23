/*
 * Copyright (C) 2014 Li Cong, forlong401@163.com http://www.360qihoo.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sf.loglib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.loglib.file.SFFileHelp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility.
 *
 * @author Li Cong
 * @date 2014-3-23
 */
public final class SFLogUtils {
    private static String sAppName = "";

    public static void log2File(Context context, String tag, String msg) {
        if (SFFileHelp.availableMemInSDcard()) {
            try {
                String systemInfo = "\n";
                File file = SFFileCreationUtil.createFile(new SimpleDateFormat("yyyymmddhhmmss"), ".txt");
                if (!file.exists() || file.isDirectory()) {
                    SFFileHelp.removeDirOrFile(file);
                    file.createNewFile();
                    systemInfo = SFLogUtils.buildSystemInfo(context);
                }
                String lineSeparator = System.getProperty("line.separator");
                if (lineSeparator == null) {
                    lineSeparator = "\n";
                }
                // Encode and encrypt the message.
                FileOutputStream fos = new FileOutputStream(file, true);
                OutputStreamWriter writer = new OutputStreamWriter(fos,
                        "utf-8");
                writer.write(buildLog(tag, msg, systemInfo));
                writer.flush();
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Log.e("LogFileTask", "log2File exception: " + e);
            }
        }
    }

    private static String buildLog(String tag, String msg, String systemInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date().toString());
        sb.append(systemInfo);
        sb.append(tag);
        sb.append(msg);
        sb.append("\n");
        return sb.toString();
    }

    public static File getAppCacheDir(Context context, String subName) {
        if (!sdAvailible()) {
            return null;
        }
        File sd = Environment.getExternalStorageDirectory();
        File dir = new File(sd, getAppName(context));
        File sub = new File(dir, subName);
        sub.mkdirs();
        return sub;
    }

    public static boolean sdAvailible() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static String encrypt(String str) {
        // TODO: encrypt data.
        return str;
    }

    public static String buildSystemInfo(Context context) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");
        buffer.append("#-------system info-------");
        buffer.append("\n");
        buffer.append("version-name:");
        buffer.append(SFLogUtils.getVersionName(context));
        buffer.append("\n");
        buffer.append("version-code:");
        buffer.append(SFLogUtils.getVersionCode(context));
        buffer.append("\n");
        buffer.append("system-version:");
        buffer.append(SFLogUtils.getSystemVersion(context));
        buffer.append("\n");
        buffer.append("model:");
        buffer.append(SFLogUtils.getModel(context));
        buffer.append("\n");
        buffer.append("density:");
        buffer.append(SFLogUtils.getDensity(context));
        buffer.append("\n");
        buffer.append("imei:");
        buffer.append(SFLogUtils.getIMEI(context));
        buffer.append("\n");
        buffer.append("screen-height:");
        buffer.append(SFLogUtils.getScreenHeight(context));
        buffer.append("\n");
        buffer.append("screen-width:");
        buffer.append(SFLogUtils.getScreenWidth(context));
        buffer.append("\n");
        buffer.append("unique-code:");
        buffer.append(SFLogUtils.getUniqueCode(context));
        buffer.append("\n");
        buffer.append("mobile:");
        buffer.append(SFLogUtils.getMobile(context));
        buffer.append("\n");
        buffer.append("imsi:");
        buffer.append(SFLogUtils.getProvider(context));
        buffer.append("\n");
        buffer.append("isWifi:");
        buffer.append(SFLogUtils.isWifi(context));
        buffer.append("\n");
        return buffer.toString();
    }

    public static String getUniqueCode(Context context) {
        if (context == null)
            return null;
        String imei = getIMEI(context);
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mUniqueCode = imei + "_" + info.getMacAddress();
        return mUniqueCode;
    }

    public static boolean isWifi(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @SuppressLint("MissingPermission")
    public static String getMobile(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    @SuppressLint("MissingPermission")
    public static String getProvider(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    @SuppressLint("MissingPermission")
    public static final String getIMEI(final Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static String getSystemVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getModel(Context context) {
        return android.os.Build.MODEL != null ? android.os.Build.MODEL.replace(
                " ", "") : "unknown";
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionName;
        } catch (NameNotFoundException e) {
        }

        return "";
    }

    public static String getAppName(Context context) {
        if (TextUtils.isEmpty(sAppName)) {
            sAppName = "com_forlong401_log";
            try {
                PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(),
                        PackageManager.GET_CONFIGURATIONS);
                String packageName = pinfo.packageName;
                if (!TextUtils.isEmpty(packageName)) {
                    sAppName = packageName.replaceAll("\\.", "_");
                }
            } catch (NameNotFoundException e) {
            }
        }

        return sAppName;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionCode;
        } catch (NameNotFoundException e) {
        }

        return 1;
    }

}
