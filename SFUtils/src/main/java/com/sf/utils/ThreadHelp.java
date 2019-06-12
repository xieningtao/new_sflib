package com.sf.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHelp {
    private static final int nThreads = 5;
    private static ExecutorService excutors = Executors
            .newFixedThreadPool(nThreads);
    private static DelayThread delayThread = new DelayThread("delay_thread");
    private static Context context;
    private static Handler mHandler;

    public static void runInBackThreadPool(Runnable runnable) {
        excutors.execute(runnable);
    }

    public static void runInSingleBackThread(Runnable runnable, int delay) {
        delayThread.excute(runnable, delay);
    }

    public static void initThread(Context _context) {
        context = _context;
        Looper looper = context.getMainLooper();
        mHandler = new Handler(looper);
    }

    public static void runLatestInMain(Runnable runnable) {
        mHandler.removeCallbacks(runnable);
        runInMain(runnable);
    }

    public static void runLatestInMain(Runnable runnable, int delay) {
        mHandler.removeCallbacks(runnable);
        runInMain(runnable, delay);
    }

    public static void runInMain(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static void runInMain(Runnable runnable, int delay) {
        mHandler.postDelayed(runnable, delay);
    }

    static class DelayThread extends HandlerThread {
        private Handler mHandler;

        public DelayThread(String name) {
            super(name);
            init();
        }

        public DelayThread(String name, int priority) {
            super(name, priority);
            init();
        }

        private void init() {
            start();
            mHandler = new Handler(getLooper());
        }

        public void excute(Runnable runnable, int delay) {
            mHandler.postDelayed(runnable, delay);
        }

    }
}
