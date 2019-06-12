package com.sf.utils.baseutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by NetEase on 2016/9/29 0029.
 */
public class ActivityResolveUtils {

    public static void toGooglePlayMarket(Context context, String packageName) {
        if (context == null) {
            return;
        }
        //这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName)); //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
        if (resolveIntent(context, intent, "com.android.vending")) return;
        SFToast.showToast("请安装Google play");
    }

    private static boolean resolveIntent(Context context, Intent intent, String packageName) {
        if (intent == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(intent, 0);
        if (otherApps != null && !otherApps.isEmpty()) {
            for (ResolveInfo otherApp : otherApps) {
                if (otherApp.activityInfo.applicationInfo.packageName.equals(packageName)) {
                    ActivityInfo otherAppActivity = otherApp.activityInfo;
                    ComponentName componentName = new ComponentName(
                            otherAppActivity.applicationInfo.packageName,
                            otherAppActivity.name
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    intent.setComponent(componentName);
                    context.startActivity(intent);
                    return true;
                }
            }
        }
        return false;
    }
}
