package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.thirdlib.IdRecognizeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by g8876 on 2017/9/13.
 */

public class ThirdLisLevelActivity extends BaseLevelActivity {
    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("IdReco", IdRecognizeActivity.class));
        return activities;
    }
}
