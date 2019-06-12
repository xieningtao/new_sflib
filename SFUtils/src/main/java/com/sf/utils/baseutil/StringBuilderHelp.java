package com.sf.utils.baseutil;

import android.text.TextUtils;

import java.util.List;

public class StringBuilderHelp {

    // 通过重写对象的toString来获取到相应的值
    public static <T> String strWithToken(List<T> list, String token) {
        if (list == null || TextUtils.isEmpty(token))
            return "";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            T bean = list.get(i);
            builder.append(bean.toString()).append(token);
        }
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - token.length());
        }
        return "";
    }

    // 通过重写对象的toString来获取到相应的值
    public static <T> String strWithLastToken(List<T> list, String token) {
        if (list == null || TextUtils.isEmpty(token))
            return "";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            T bean = list.get(i);
            builder.append(bean.toString()).append(token);
        }
        return builder.toString();
    }
}
