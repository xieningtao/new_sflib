package com.sf.utils.baseutil;

import java.util.List;

/**
 * Created by xieningtao on 15-12-23.
 */
public class FP {
    public static boolean empty(List list) {
        return list == null || list.size() == 0 ? true : false;
    }

    public static boolean eq(Object a, Object b) {
        if (a == null && b == null)
            return true;
        else if (a == null)
            return false;
        else
            return a.equals(b);
    }

    public static <T> boolean empty(T[] xs) {
        return xs == null || xs.length == 0;
    }
}
