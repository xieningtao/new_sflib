package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.thirdlib.IdRecognizeActivity;

/**
 * Created by g8876 on 2017/9/13.
 */

public class ThirdLisLevelActivity extends BaseLevelActivity {
    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("IdReco", IdRecognizeActivity.class));
        return activities;
    }
}
