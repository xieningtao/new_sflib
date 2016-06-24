package com.sflib.reflection.core;

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
	public static void runInBackThreadPool(Runnable runnable) {
		excutors.execute(runnable);
	}

	public static void runInSingleBackThread(Runnable runnable, int delay) {
		delayThread.excute(runnable, delay);
	}

	public static void initThread(Context _context){
		context=_context;
	}

	public static void runInMain(Runnable runnable) {
		Looper looper = context.getMainLooper();
		Handler handler = new Handler(looper);
		handler.post(runnable);
	}

	public static void runInMain(Runnable runnable, int delay) {
		Looper looper = context.getMainLooper();
		Handler handler = new Handler(looper);
		handler.postDelayed(runnable, delay);
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
