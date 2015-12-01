package com.sf.yysdkdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by xieningtao on 15-11-9.
 */
public class EmptyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DebugHelper.exitDelay(2*1000,this);
    }
}
