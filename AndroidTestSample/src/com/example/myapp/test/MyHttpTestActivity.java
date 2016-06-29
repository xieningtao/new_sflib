package com.example.myapp.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.sf.utils.baseutil.SFBus;
import com.example.myapp.HttpActivityTest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xieningtao on 16-2-22.
 */
@LargeTest
public class MyHttpTestActivity extends ActivityInstrumentationTestCase2<HttpActivityTest> {
    public MyHttpTestActivity() {
        super(HttpActivityTest.class);
    }

    HttpActivityTest mHttpTestActivity;
    Instrumentation mInstrumentation;
    CountDownLatch mDownLatch;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mHttpTestActivity = getActivity();
        mInstrumentation = getInstrumentation();
        mDownLatch = new CountDownLatch(1);
        SFBus.register(this);
    }

    public void testResult() {
        // Set up Activity Monitor
        Instrumentation.ActivityMonitor myActivityMonitor =
                mInstrumentation.addMonitor(HttpActivityTest.class.getName(),
                        null, false);

        //3+5
        mHttpTestActivity.doSumRequest(3, 5);

        try {
            mDownLatch.await();
            int result = mHttpTestActivity.getResult();
            assertEquals(9, result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mInstrumentation.removeMonitor(myActivityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        SFBus.unregister(this);
    }

    public void onEvent(Integer a) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDownLatch.countDown();
    }
}
