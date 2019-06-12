package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.ActivityMultiChoice;
import com.sf.SFSample.ui.ActivitySingleChoice;
import com.sf.SFSample.ui.BaseLevelActivity;

/**
 * Created by NetEase on 2016/7/25 0025.
 */
public class ActivitySelected extends BaseLevelActivity {
    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("ActivitySingleChoice", ActivitySingleChoice.class));
        activities.put(1, new Pair<String, Class>("ActivityMultiChoice", ActivityMultiChoice.class));
        return activities;
    }
}
