package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.ActivityMultiChoice;
import com.sf.SFSample.ui.ActivitySingleChoice;
import com.sf.SFSample.ui.BaseLevelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/7/25 0025.
 */
public class ActivitySelected extends BaseLevelActivity {
    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("ActivitySingleChoice", ActivitySingleChoice.class));
        activities.add( new Pair<String, Class>("ActivityMultiChoice", ActivityMultiChoice.class));
        return activities;
    }
}
