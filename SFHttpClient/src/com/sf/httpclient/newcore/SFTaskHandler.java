package com.sf.httpclient.newcore;

import com.sf.loglib.L;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
abstract public class SFTaskHandler<T> implements SFTask<T> {
    protected final String TAG = getClass().getName();
    private static Executor executor = Executors.newFixedThreadPool(5);
    private final SFFutureTask mSFFutureTask;

    private AtomicBoolean isCanceled = new AtomicBoolean(false);

    public SFTaskHandler() {
        SFHttpCallable sFHttpCallable = new SFHttpCallable();
        mSFFutureTask = new SFFutureTask(sFHttpCallable);
    }

    public void start() {
        executor.execute(mSFFutureTask);
        taskStart();
    }

    public void cancel(boolean mayInterruptIfRunning) {
        isCanceled.set(mayInterruptIfRunning);
        mSFFutureTask.cancel(mayInterruptIfRunning);
    }

    public boolean isTaskCanceled() {
        return isCanceled.get();
    }

    private class SFFutureTask extends FutureTask<T> {

        public SFFutureTask(Callable<T> callable) {
            super(callable);
        }

        @Override
        protected void done() {
            try {
                if(isTaskCanceled()){
                    onCanceled();
                }else {
                    taskDone(get());
                }
                return;
            } catch (InterruptedException | ExecutionException e) {
                taskException(e);
                L.error(TAG, TAG + ".done exception: " + e);
            } catch (Exception e) {
                taskException(e);
                L.error(TAG, TAG + ".done exception: " + e);
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

    protected void taskException(Exception e) {

    }

    protected void taskStart() {

    }

}
