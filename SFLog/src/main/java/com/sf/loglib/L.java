package com.sf.loglib;

import android.content.Context;
import android.util.Log;

import com.sf.loglib.task.LogFileTask;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * log
 *
 * @author xieningtao
 */
public class L {
    public static enum LOG_LEVEL {
        VERBO, DEBUG, INFO, WARNING, ERROR
    }

    private static Executor mExecutor;
    public final static int LOG_SAVE_LOCAL = 0x00000001;
    public final static int LOG_SAVE_FILE = 0x00000010;
    public final static int LOG_SAVE_SERVER = 0x00000100;

    public final static int LOG_SAVE_TYPE = LOG_SAVE_LOCAL;

    private static LOG_LEVEL logLevel = LOG_LEVEL.DEBUG;
    private static boolean enable = true;

    public static void setLogEnable(boolean ok) {
        enable = ok;
    }

    public static void setLogLevel(LOG_LEVEL level) {
        logLevel = level;
    }

    public static int getLogSaveType() {
        return LOG_SAVE_TYPE;
    }

    static {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    private static Context context;

    public void configLog(Context _context, boolean _enable, LOG_LEVEL log_level) {
        context = _context;
        enable = _enable;
        logLevel = log_level;
    }

    public static void info(Object object, String msg) {
        doLog(object, msg, LOG_LEVEL.INFO);
    }

    private static void doLog(Object object, String msg, LOG_LEVEL log_level) {
        if (!enable) {
            return;
        }
        if (logLevel.ordinal() > log_level.INFO.ordinal()) {
            return;
        }
        String tag = getTag(object);
        String prefix = getPrefix();
        Log.i(tag, prefix + msg);
        if ((LOG_SAVE_TYPE & LOG_SAVE_FILE) != 0) {
            mExecutor.execute(new LogFileTask(context, tag, prefix + msg));
        }
    }

    public static void debug(Object object, String msg) {
        doLog(object, msg, LOG_LEVEL.DEBUG);
    }

    public static void warn(Object object, String msg) {
        doLog(object, msg, LOG_LEVEL.WARNING);
    }

    public static void error(Object object, String msg) {
        doLog(object, msg, LOG_LEVEL.ERROR);
    }

    public static void exception(Throwable throwable) {
            String message = Throwable2String(throwable);
            error("EXCEPTION", message);
    }

    public static String Throwable2String(Throwable throwable) {
        if(throwable == null){
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(bos);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        return new String(bos.toByteArray());
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
