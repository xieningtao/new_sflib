package com.basesmartframe.baseutil;

import android.text.TextUtils;

/**
 * Created by xieningtao on 15-11-13.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String text) {
        return TextUtils.isEmpty(text);
    }
}
