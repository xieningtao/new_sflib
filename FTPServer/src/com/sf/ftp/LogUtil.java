package com.sf.ftp;

/**
 * Created by xieningtao on 16-3-29.
 */
public class LogUtil {

    public static void error(String tag, String content) {
        System.err.println(tag + "---" + content);
    }

    public static void infor(String tag, String content){
        System.out.println(tag + "---" + content);
    }
}
