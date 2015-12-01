package com.sf.yysdkdemo;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by xieningtao on 15-11-9.
 */
public class DebugHelper {
    public static void exitDelay(final int million, final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, million);
    }
}
