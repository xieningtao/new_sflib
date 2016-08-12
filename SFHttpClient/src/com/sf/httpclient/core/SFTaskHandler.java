package com.sf.httpclient.core;

import android.os.AsyncTask;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
abstract public class SFTaskHandler<T> implements SFTask<T> {

    private static Executor executor = Executors.newFixedThreadPool(5);
    private final SFHttpCallable mSFHttpCallable;
    private final SFFutureTask mSFFutureTask;

    public SFTaskHandler() {
        mSFHttpCallable = new SFHttpCallable();
        mSFFutureTask = new SFFutureTask(mSFHttpCallable);
    }

    public void start() {
        executor.execute(mSFFutureTask);
    }

    private class SFFutureTask extends FutureTask<T> {

        public SFFutureTask(Callable<T> callable) {
            super(callable);
        }

        @Override
        protected void done() {
            try {
                taskDone(get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            taskDone(null);
        }
    }


    private class SFHttpCallable implements Callable<T> {

        @Override
        public T call() throws Exception {
            return doInBackground();
        }
    }
}
