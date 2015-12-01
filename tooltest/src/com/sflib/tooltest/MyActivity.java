package com.sflib.tooltest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Debug.startMethodTracing("tooltest");
        alooper();
        blooper();
        Debug.stopMethodTracing();

    }

    private void alooper() {
        long count = 100000000;
        long i = 0;
        while (count > 0) {
            i++;
            count--;
        }
    }

    private void blooper() {
        long count = 100000000;
        long i = 0;
        while (count > 0) {
            i++;
            count--;
        }
    }
}
