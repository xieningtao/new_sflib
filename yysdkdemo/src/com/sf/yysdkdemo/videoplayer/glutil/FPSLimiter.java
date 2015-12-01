package com.sf.yysdkdemo.videoplayer.glutil;

import android.os.SystemClock;
import android.util.Log;

public class FPSLimiter {

    private long mFPSInterval = 0;
    private long mPreviousFrameTimeStamp = 0;

    public FPSLimiter(int fps) {
        mFPSInterval = 1000 / fps;
    }

    public void limitFPS() {
        long endTime = SystemClock.elapsedRealtime();
        long diffTime = endTime - mPreviousFrameTimeStamp;

        if (diffTime < mFPSInterval) {
            try {
                Thread.sleep(mFPSInterval - diffTime);
            } catch (InterruptedException e) {
                Log.e("FPSLimiter", e.getMessage());
            }
            mPreviousFrameTimeStamp += mFPSInterval;
        } else {
            mPreviousFrameTimeStamp = endTime;
        }
    }
}
