package com.sf.SFSample.ui;

import android.app.Fragment;

/**
 * Created by xieningtao on 15-12-23.
 */
public class TVHorizontalGridViewActivity extends TVBaseGridViewActivity {
    @Override
    protected Fragment createFragment() {
        return new TVHorizontalGridViewFragment();
    }
}
