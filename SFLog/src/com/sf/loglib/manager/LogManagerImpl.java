/*
 * Copyright (C) 2014 Li Cong, forlong401@163.com http://www.360qihoo.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sf.loglib.manager;

import android.content.Context;
import android.util.Log;

import com.sf.loglib.L;
import com.sf.loglib.SFLogUtils;
import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.loglib.file.SFFileHelp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sf.loglib.L.LOG_SAVE_FILE;
import static com.sf.loglib.L.LOG_SAVE_SERVER;


/**
 * Log manager implement.
 *
 * @author Li Cong
 * @date 2014-3-23
 */
public class LogManagerImpl implements ILogManager, UncaughtExceptionHandler {
    private static final String TAG = "RuntimeException";
    private final static int EXECUTOR_HANDLE_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;

    private Context mContext;
    // The available numbers of threads in executor service.
    private static UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;


    static {
        mDefaultUncaughtExceptionHandler = Thread
                .getDefaultUncaughtExceptionHandler();

    }

    LogManagerImpl(Context context) {
        mContext = context;
    }

    @Override
    public boolean registerCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        return true;
    }

    @Override
    public boolean unregisterCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(mDefaultUncaughtExceptionHandler);
        return true;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            flushException(ex);
        } catch (Exception e) {
            flushException(e);
        }
        mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
    }

    private void flushException(Throwable ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        printWriter.append(ex.getMessage());
        ex.printStackTrace(printWriter);
        Log.getStackTraceString(ex);
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause.
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String msg = buildCrashLog(result.toString());
        printWriter.close();

        if ((L.getLogSaveType() & LOG_SAVE_FILE) != 0) {
            // Save to file.
            saveCrashLog2File(msg);
        }

        if ((L.getLogSaveType() & LOG_SAVE_SERVER) != 0) {
            // Upload crash log to server.

        }
    }

    private void saveCrashLog2File(String result) {
        if (SFFileHelp.availableMemInSDcard()) {
            try {

                File file = SFFileCreationUtil.createFile(new SimpleDateFormat("yyyymmddhhmmss"), ".txt");
                FileOutputStream trace = new FileOutputStream(file, true);

                String lineSeparator = System.getProperty("line.separator");
                if (lineSeparator == null) {
                    lineSeparator = "\n";
                }

                // Encode and encrypt the message.
                OutputStreamWriter writer = new OutputStreamWriter(trace,
                        "utf-8");
                writer.write(result);
                writer.flush();

                trace.flush();
                trace.close();
            } catch (Exception e) {
                Log.e(TAG, "saveCrashLog2File, exception:　" + e);
            }
        }
    }

    private String buildCrashLog(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(SFLogUtils.getAppName(mContext)).append("]");
        sb.append("#");
        sb.append(new Date().toString());
        sb.append("\n");
        // Add system and device info.
        sb.append(SFLogUtils.buildSystemInfo(mContext));
        sb.append("\n");
        sb.append("#-------AndroidRuntime-------");
        sb.append(msg);
        sb.append("\n");
        sb.append("#-------activity_stack-------");
        sb.append("\n");
        sb.append("#end");

        return sb.toString();
    }


}
