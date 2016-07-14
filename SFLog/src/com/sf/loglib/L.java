package com.sf.loglib;

import android.util.Log;

/**
 * log
 *
 * @author xieningtao
 */
public class L {
    public static enum LOG_LEVEL{
        VERBO,DEBUG,INFO,WARNING,ERROR
    }
    private static LOG_LEVEL logLevel=LOG_LEVEL.DEBUG;
    private static boolean enable=true;

    public static void setLogEnable(boolean ok){
        enable=ok;
    }

    public static void setLogLevel(LOG_LEVEL level){
        logLevel=level;
    }
    public static void info(Object object, String msg) {
        if(enable&&logLevel.ordinal()>=LOG_LEVEL.INFO.ordinal()) {
            String tag = getTag(object);
            String prefix = getPrefix();
            Log.i(tag, prefix + msg);
        }
    }

    public static void debug(Object object, String msg) {
        if(enable&&logLevel.ordinal()>=LOG_LEVEL.DEBUG.ordinal()) {
            String tag = getTag(object);
            String prefix = getPrefix();
            Log.d(tag, prefix + msg);
        }
    }

    public static void warn(Object object, String msg) {
        if(enable&&logLevel.ordinal()>=LOG_LEVEL.WARNING.ordinal()) {
            String tag = getTag(object);
            String prefix = getPrefix();
            Log.w(tag, prefix + msg);
        }
    }

    public static void error(Object object, String msg) {
        if(enable&&logLevel.ordinal()>=LOG_LEVEL.ERROR.ordinal()) {
            String tag = getTag(object);
            String prefix = getPrefix();
            Log.e(tag, prefix + msg);
        }
    }

    private static String getTag(Object object) {
        if (object == null)
            return "";
        String tag = "";
        if (object instanceof String) {
            tag = (String) object;
        } else {
            tag = object.getClass().getName();
        }
        return tag;
    }

    //文件的容量，超容量的备份日志，日志打印多线程的问题
    private static void logToFile(){

    }

    private static String getPrefix() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return "";
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(L.class.getName())) {
                continue;
            }
            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    + " ]";
        }
        return "";
    }
}
