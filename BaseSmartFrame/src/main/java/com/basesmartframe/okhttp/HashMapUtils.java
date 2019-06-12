package com.basesmartframe.okhttp;


import com.sf.loglib.L;

import java.util.HashMap;

/**
 * 获取hashmap中的元素，处理了各种异常情况
 */
public class HashMapUtils {

    static public Object getObject(HashMap<String, Object> hashMap, String key, Object deObject) {
        if(hashMap == null){
            return deObject;
        }
        try {
            return hashMap.get(key);
        } catch (Exception e) {
            L.exception(e);
        }
        return deObject;
    }

    static public String getString(HashMap<String, Object> hashMap, String key) {
        if (hashMap == null) {
            return "";
        }
        if (!hashMap.containsKey(key)) {
            return "";
        }

        if (hashMap.get(key) == null) {
            return "";
        }
        return hashMap.get(key).toString();
    }

    static public String getString(HashMap<String, Object> hashMap, String key, String defaultString) {
        try {
            if (hashMap == null) {
                return defaultString;
            }
            Object object = hashMap.get(key);
            if(object != null){
                return object.toString();
            }
        } catch (Exception e) {
            L.exception(e);
        }
        return defaultString;
    }

    static public void putString(HashMap<String, Object> hashMap, String key, String value) {
        if(hashMap == null){
            return;
        }
        try {
            hashMap.put(key, value);
        } catch (Exception e) {
            L.exception(e);
        }
    }

    static public int getInt(HashMap<String, Object> hashMap, String key, int defaultValue) {
        if(hashMap == null){
            return defaultValue;
        }
        try {
            Object object = hashMap.get(key);
            if(object != null){
                return (Integer)object;
            }
        } catch (Exception e) {

           L.exception(e);
        }
        return defaultValue;
    }

    static public boolean getBoolean(HashMap<String, Object> hashMap, String key, boolean defaultValue) {
        if(hashMap == null){
            return defaultValue;
        }
        try {
            Object object = hashMap.get(key);
            if(object != null){
                return (Boolean)object;
            }
        } catch (Exception e) {
            L.exception(e);
        }
        return defaultValue;
    }

    public static Object get(HashMap<String, Object> hashMap, String key) {
        if(hashMap == null){
            return null;
        }
        try {
            return hashMap.get(key);
        } catch (Exception e) {
            L.exception(e);
        }
        return null;
    }

    public static HashMap<String, Object> getMap(String[] keys, Object[] values) {
        if (keys == null || values == null) {
            return null;
        }
        try {
            if (keys.length == 0 || values.length == 0) {
                return null;
            }
        } catch (Exception e) {
            L.exception(e);
        }
        int size = keys.length < values.length ? keys.length : values.length;
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

}
