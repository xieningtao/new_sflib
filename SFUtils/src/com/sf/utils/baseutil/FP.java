package com.sf.utils.baseutil;

import java.util.List;

/**
 * Created by xieningtao on 15-12-23.
 */
public class FP {
    public static boolean empty(List list) {
        return list == null || list.size() == 0 ? true : false;
    }
}
