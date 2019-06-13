package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.CardActivity;
import com.sf.SFSample.ui.PaletteActivity;
import com.sf.SFSample.ui.RippleActivity;
import com.sf.SFSample.ui.SDKLAnimatorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2017/5/19 0019.
 */

public class SDKLNewFeatureLevelActivity extends BaseLevelActivity {

    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("ripple", RippleActivity.class));
        activities.add( new Pair<String, Class>("card", CardActivity.class));
        activities.add( new Pair<String, Class>("animator", SDKLAnimatorActivity.class));
        activities.add( new Pair<String, Class>("palette", PaletteActivity.class));
        return activities;
    }
}
