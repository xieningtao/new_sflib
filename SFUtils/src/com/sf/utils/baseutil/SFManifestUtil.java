package com.sf.utils.baseutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * 获取android的manifest里面的一些字段的值包括 
 * 1、应用程序名称 
 * 2、包名称
 * 3、meta里面的值
 * 
 * @author xieningtao
 * 
 */
public class SFManifestUtil {

	public static final String TAG="ManifestUtil";
	/**
	 * 获取应用程序的名称
	 * 
	 * @param context
	 * @return context==null,返回""
	 */
	public static String getAppName(Context context) {
		if (context == null)
			return "";
		return context.getApplicationInfo().name;
	}

	/**
	 * 获取报名
	 * @param context
	 * @return context==null,返回""
	 */
	public static String getPackageName(Context context) {
		if (context == null)
			return "";
		return context.getApplicationInfo().packageName;

	}

	/**
	 * 
	 * @param context
	 * @param baiduKey
	 * @return 条件不成立,返回""
	 */
	public static String getMetaValue(Context context,String baiduKey) {
		if(context==null||TextUtils.isEmpty(baiduKey))return "";
		ApplicationInfo appInfo=null;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(getPackageName(context),
					PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG,"package name not exist. reason: "+e.getMessage());
			return "";
		}
		Bundle bundle = appInfo.metaData;
		if(bundle!=null){
			return bundle.getString(baiduKey);
		}
		return "";
	}
}
