package com.basesmartframe.okhttp;

import android.text.TextUtils;


import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by yuhengye g10475 on 2018/7/26.
 **/
public class HTHttpUtils {

    public static final String RESULT_CODE = "code";

    public static final String FORBIDDEN_STATUS = "status";

    public static final String ERROR_MSG = "apiErrorMessage";

    public static final String B_CODE = "bcode";

    public static HashMap<String, Object> getResultCode(String result) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (TextUtils.isEmpty(result)) {
            return map;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            map.put(RESULT_CODE, jsonObject.optInt(RESULT_CODE, 0));
            map.put(ERROR_MSG, jsonObject.optString(ERROR_MSG, ""));
            if (jsonObject.has(FORBIDDEN_STATUS)) {
                map.put(FORBIDDEN_STATUS, jsonObject.optInt(FORBIDDEN_STATUS, -1));
            } else {
                map.put(FORBIDDEN_STATUS, jsonObject.optInt(B_CODE, -1));
            }
        } catch (Exception ignore) {
        }
        return map;
    }

    public static int getResultCode(HashMap<String, Object> map) {
        Object object = HashMapUtils.get(map, RESULT_CODE);
        if (object != null && object instanceof Integer) {
            return (int) object;

        } else {
            return 0;
        }
    }

    public static boolean isSuccess(HashMap<String, Object> map) {
        Object object = HashMapUtils.get(map, RESULT_CODE);
        if (object != null && object instanceof Integer) {
            int code = (int) object;
            return code == 1;
        } else {
            return false;
        }
    }

    public static String getBasicResult(HashMap<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Integer code = (Integer) map.get(RESULT_CODE);
        String errorMsg = (String) map.get(ERROR_MSG);
        Integer status = (Integer) map.get(FORBIDDEN_STATUS);
        builder.append("code: ").append(code).append(" errorMsg:").append(errorMsg).append(" bcode: ").append(status);
        return builder.toString();
    }


}
