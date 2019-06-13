package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.ActivityBaiduLocation;
import com.sf.SFSample.ui.BaseLevelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
public class ActivityBaiduFamily extends BaseLevelActivity {
    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("my location", ActivityBaiduLocation.class));
        return activities;
    }
}
