package com.sf.utils.baseutil;

import android.content.Context;
import android.widget.Toast;

/**
 * toast消息
 *
 * @author leeib
 */
public class FDToastUtil {
    public static void show(Context context, Object message) {
        Toast.makeText(context, parseString(context, message), Toast.LENGTH_LONG).show();
    }

    /**
     * 支持字符串或字符串资源id
     *
     * @param obj 可传字符串或字符串资源id
     * @return
     */
    public static String parseString(Context context, Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return obj.toString();
            } else if (obj instanceof Integer) {
                return context.getString((Integer) obj);
            }
        }
        return null;
    }

    public static void show(Context context, int resId) {
        show(context, resId);
    }
}
