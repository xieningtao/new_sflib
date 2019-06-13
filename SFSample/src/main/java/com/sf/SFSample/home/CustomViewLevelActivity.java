package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.HotwordActivity;
import com.sf.SFSample.ui.RoundBitmapActivity;
import com.sf.SFSample.ui.SlidingActivity;
import com.sf.SFSample.ui.StackViewActivity;
import com.sf.SFSample.ui.StackViewGroupActivity;
import com.sf.SFSample.ui.StyledViewActivity;
import com.sf.SFSample.ui.SurfaceActivity;
import com.sf.SFSample.ui.ViewEventActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-11-16.
 */
public class CustomViewLevelActivity extends BaseLevelActivity {


    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("slidingactivity", SlidingActivity.class));
        activities.add( new Pair<String, Class>("surfaceactivity", SurfaceActivity.class));
        activities.add( new Pair<String, Class>("viewevent", ViewEventActivity.class));
        activities.add( new Pair<String, Class>("hotwords", HotwordActivity.class));
        activities.add( new Pair<String, Class>("roundBitmap", RoundBitmapActivity.class));
        activities.add( new Pair<String, Class>("styledView", StyledViewActivity.class));
        activities.add( new Pair<String, Class>("stackViewGroup", StackViewGroupActivity.class));
        activities.add( new Pair<String, Class>("stackView", StackViewActivity.class));
        return activities;
    }
}
