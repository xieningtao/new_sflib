//package com.basesmartframe.basevideo.util;
//
//import android.text.TextUtils;
//
//import com.duowan.ark.ArkUtils;
//import com.duowan.ark.app.BaseApp;
//import com.duowan.ark.util.Config;
//
///**
// * Created by xieningtao on 15-5-25.
// */
//public class GsonSharepreference {
//    public static final String TAG = "GsonSharepreference";
//
//    public static <T> boolean save(String key, T object) {
//        if (null == object) return false;
//        String string = toGson(object);
//        return Config.getInstance(BaseApp.gContext).setString(key, string);
//    }
//
//    public static <T> T get(String key, Class<T> classType, T defaultObject) {
//        String default_str = toGson(defaultObject);
//        String string = Config.getInstance(BaseApp.gContext).getString(key, default_str);
//        T object = formGson(string, classType);
//        return object;
//    }
//
//    public static <T> T formGson(String string, Class<T> classType) {
//        T object = null;
//        if (!TextUtils.isEmpty(string)) {
//            object = ArkUtils.parseJson(string, classType);
//        }
//        return object;
//    }
//
//    public static <T> String toGson(T object) {
//        if (null == object)
//            return "";
//        return ArkUtils.toJson(object);
//    }
//}
