package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.ActivityBaiduLocation;
import com.sf.SFSample.ui.BaseLevelActivity;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
public class ActivityBaiduFamily extends BaseLevelActivity {
    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("my location", ActivityBaiduLocation.class));
        return activities;
    }
}
