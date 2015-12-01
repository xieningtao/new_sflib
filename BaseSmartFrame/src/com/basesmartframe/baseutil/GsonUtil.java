package com.basesmartframe.baseutil;

import com.basesmartframe.log.L;
import com.google.gson.Gson;

/**
 * Created by xieningtao on 15-11-13.
 */
public class GsonUtil {
    private final static String TAG = "GsonUtil";

    /**
     *
     * @param json
     * @param classType
     * @param <T>
     * @return null if fail to parse json
     */
    public static <T> T parse(String json, Class<T> classType) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, classType);
        } catch (Exception e) {
            L.error(TAG, "exception: " + e.getMessage());
            return null;
        }
    }
}
