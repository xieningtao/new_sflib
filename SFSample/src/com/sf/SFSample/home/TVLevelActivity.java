package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.TVFocusActivity;
import com.sf.SFSample.ui.TVHomeActivity;
import com.sf.SFSample.ui.TVHorizontalGridViewActivity;
import com.sf.SFSample.ui.TVVerticalGridViewActivity;

/**
 * Created by xieningtao on 15-12-23.
 */
public class TVLevelActivity extends BaseLevelActivity {

    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("tv vertical gridview", TVVerticalGridViewActivity.class));
        activities.put(1, new Pair<String, Class>("tv horizontal gridview", TVHorizontalGridViewActivity.class));
        activities.put(2, new Pair<String, Class>("tv home", TVHomeActivity.class));
        activities.put(3, new Pair<String, Class>("tv focus", TVFocusActivity.class));
        return activities;
    }
}
