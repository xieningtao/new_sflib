package com.example.myapp;

import android.os.Bundle;

import com.sflib.reflection.core.ThreadHelp;
import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseutil.SFBus;

/**
 * Created by xieningtao on 16-2-22.
 */
public class HttpActivityTest extends BaseActivity {

    private final String TAG = HttpActivityTest.class.getName();
    private int mResult = 0;

    public int getResult() {
        return mResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    public void doSumRequest(final int a, final int b) {
        ThreadHelp.runInBackThreadPool(new Runnable() {
            @Override
            public void run() {
                int sum = a + b;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SFBus.post(new Integer(sum));
            }
        });
    }

    @Override
    public void onEvent(Integer a) {
        super.onEvent(a);
        mResult = a;
    }
}
