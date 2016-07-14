package com.sf.SFSample.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.nostra13.universalimageloader.utils.L;
import com.sf.SFSample.R;
import com.sflib.reflection.core.ThreadHelp;

/**
 * Created by NetEase on 2016/7/13 0013.
 */
public class BitMapSizeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_size);
        final View rootView = findViewById(R.id.root_view);
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                int with = rootView.getWidth();
                int height = rootView.getHeight();
                L.i(TAG,"onCreate with: "+with+" height: "+height);
            }
        }, 2000);
    }
}
