package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.CardActivity;
import com.sf.SFSample.ui.PaletteActivity;
import com.sf.SFSample.ui.RippleActivity;
import com.sf.SFSample.ui.SDKLAnimatorActivity;

/**
 * Created by NetEase on 2017/5/19 0019.
 */

public class SDKLNewFeatureLevelActivity extends BaseLevelActivity {

    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("ripple", RippleActivity.class));
        activities.put(1, new Pair<String, Class>("card", CardActivity.class));
        activities.put(2, new Pair<String, Class>("animator", SDKLAnimatorActivity.class));
        activities.put(3, new Pair<String, Class>("palette", PaletteActivity.class));
        return activities;
    }
}
