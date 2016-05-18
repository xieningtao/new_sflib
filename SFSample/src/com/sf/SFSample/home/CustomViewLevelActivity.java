package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.HotwordActivity;
import com.sf.SFSample.ui.RoundBitmapActivity;
import com.sf.SFSample.ui.SlidingActivity;
import com.sf.SFSample.ui.StyledViewActivity;
import com.sf.SFSample.ui.SurfaceActivity;
import com.sf.SFSample.ui.ViewEventActivity;

/**
 * Created by xieningtao on 15-11-16.
 */
public class CustomViewLevelActivity extends BaseLevelActivity {


    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("slidingactivity", SlidingActivity.class));
        activities.put(1, new Pair<String, Class>("surfaceactivity", SurfaceActivity.class));
        activities.put(2, new Pair<String, Class>("viewevent", ViewEventActivity.class));
        activities.put(3, new Pair<String, Class>("hotwords", HotwordActivity.class));
        activities.put(4, new Pair<String, Class>("roundBitmap", RoundBitmapActivity.class));
        activities.put(5, new Pair<String, Class>("styledView", StyledViewActivity.class));
        return activities;
    }
}
