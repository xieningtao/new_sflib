package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.basesmartframe.log.L;
import com.sf.SFSample.R;
import com.sflib.reflection.core.SFBridgeManager;
import com.sflib.reflection.core.SFIntegerMessage;
import com.sflib.reflection.core.ThreadId;

@SFIntegerMessage(theadId = ThreadId.BackThread)
public class ReflectionActivity extends Activity {

    private final String TAG = ReflectionActivity.class.getName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        SFBridgeManager.register(this);
        SFBridgeManager.register(myObject);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doTest1();
        doTest2();
        doObjectTest1();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @SFIntegerMessage(messageId = ActivityMessageId.TEST1, theadId = ThreadId.MainThread)
    public void receiveTest1(String str) {
        Log.e(TAG, "method->receiveTest1,str: " + str);
        L.error(TAG, "method->receiveTest1,thread name: " + Thread.currentThread().getName());
    }

    @SFIntegerMessage(messageId = ActivityMessageId.TEST2)
    public void receiveTest2() {
        Log.e(TAG, "method->receiveTest1,str: ");
        L.error(TAG, "method->receiveTest2,thread name: " + Thread.currentThread().getName());
    }

    private void doTest1() {
        SFBridgeManager.send(ActivityMessageId.TEST1, "hello");
    }

    private void doTest2() {
        SFBridgeManager.send(ActivityMessageId.TEST2);
    }

    private void doObjectTest1() {
        SFBridgeManager.send(ActivityMessageId.OBJECTTEST1, "object hello", 1);
    }


    private Object myObject = new Object() {
        @SFIntegerMessage(messageId = ActivityMessageId.OBJECTTEST1,theadId = ThreadId.BackThread)
        public void objectTest1(String str, Integer num) {
            Log.e(TAG, "method->objectTest1,str: " + str + " num: " + num);
            L.error(TAG, "method->objectTest1,thread name: " + Thread.currentThread().getName());
            if (num == 4) {
                SFBridgeManager.unregister(this);
            }
            SFBridgeManager.send(ActivityMessageId.OBJECTTEST1, "object hello", ++num);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SFBridgeManager.unregister(this);
    }
}
