package com.sf.utils.baseutil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;


/**
 * 只是用一个preference
 * @author xieningtao
 *
 */
public class SpUtil {

	/**
	 * 根据名字得到SharePreference
	 *
	 * @param context
	 * @param prefName
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context, String prefName) {
//		if (context == null) {
//			context = BaseApp.gContext;
//		}
		if (TextUtils.isEmpty(prefName)) {
			return PreferenceManager.getDefaultSharedPreferences(context);
		} else {
			return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		}
	}
	private static Editor getSp(Activity context){
		SharedPreferences sp=context.getSharedPreferences(BaseUtilConfig.SharePreferenceName, Context.MODE_PRIVATE);
		return sp.edit();
	}
	public static void save(Activity context,String key,String value){
		Editor ed=getSp(context);
		ed.putString(key, value);
		ed.commit();
	}
	public static void save(Activity context,String key,boolean value){
		Editor ed=getSp(context);
		ed.putBoolean(key, value);
		ed.commit();
	}
	public static void save(Activity context,String key,int value){
		Editor ed=getSp(context);
		ed.putInt(key, value);
		ed.commit();
	}
	public static String getString(Activity context,String key){
		SharedPreferences sp=context.getSharedPreferences(BaseUtilConfig.SharePreferenceName, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
	public static int getInt(Activity context,String key){
		SharedPreferences sp=context.getSharedPreferences(BaseUtilConfig.SharePreferenceName, Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	public static boolean getBoolean(Context context,String key){
		SharedPreferences sp=context.getSharedPreferences(BaseUtilConfig.SharePreferenceName, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
}
