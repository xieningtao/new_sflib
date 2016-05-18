package com.example.myapp.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.myapp.SimpleTestActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.myapp.test.MyActivityTest \
 * com.example.myapp.tests/android.test.InstrumentationTestRunner
 */
public class MyActivityTest extends ActivityInstrumentationTestCase2<SimpleTestActivity> {

    public MyActivityTest() {
        super(SimpleTestActivity.class);
    }

    SimpleTestActivity myActivity;
    Instrumentation mInstrumentation;

    EditText number1_et;
    EditText number2_et;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = getActivity();
        mInstrumentation = getInstrumentation();

        number1_et = (EditText) myActivity.findViewById(com.example.myapp.R.id.number1_et);
        number2_et = (EditText) myActivity.findViewById(com.example.myapp.R.id.number2_et);
    }

    public void testSum1() {

        // Set up Activity Monitor
        Instrumentation.ActivityMonitor myActivityMonitor =
                mInstrumentation.addMonitor(SimpleTestActivity.class.getName(),
                        null, false);
        myActivityMonitor.waitForActivityWithTimeout(1000);

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                //2+2=4
                number1_et.setText("2");
                number2_et.setText("2");
            }
        });


        int number1 = Integer.valueOf(number1_et.getText().toString());
        int number2 = Integer.valueOf(number2_et.getText().toString());

        assertTrue((number1 + number2) == 4);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mInstrumentation.removeMonitor(myActivityMonitor);
    }


    public void testSum2() {

        // Set up Activity Monitor
        Instrumentation.ActivityMonitor myActivityMonitor =
                mInstrumentation.addMonitor(SimpleTestActivity.class.getName(),
                        null, false);
        myActivityMonitor.waitForActivityWithTimeout(1000);


        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                //2+2=4
                number1_et.setText("3");
                number2_et.setText("3");
            }
        });


        int number1 = Integer.valueOf(number1_et.getText().toString());
        int number2 = Integer.valueOf(number2_et.getText().toString());

        assertTrue((number1 + number2) == 6);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mInstrumentation.removeMonitor(myActivityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
