package com.sf.SFSample.ui;

import android.os.Bundle;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sflib.openGL.transform.ScaleTranslateSurfaceView;

/**
 * Created by xieningtao on 15-11-20.
 */
public class TransformActivity extends BaseActivity {

    private ScaleTranslateSurfaceView mSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        mSurfaceView = (ScaleTranslateSurfaceView) findViewById(R.id.transform_sf);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }
}