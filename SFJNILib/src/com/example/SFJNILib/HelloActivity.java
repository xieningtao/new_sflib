package com.example.SFJNILib;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by xieningtao on 15-12-4.
 */
public class HelloActivity extends Activity {

    static {
        System.loadLibrary("jni_test");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("HelloActivity", "onCreate threadId: " + Thread.currentThread().getId());
        Button button = new Button(this);
        setContentView(button);
        button.setText(getHello() + "txt");

    }

    public native int getHello();

    private String getStr() {
        String msg = "this function is called by native";
        Log.e("HelloActivity", "content: "+msg+" size: "+msg.length());
        Log.e("HelloActivity","getStr threadId: "+Thread.currentThread().getId());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        return msg;
    }

}
