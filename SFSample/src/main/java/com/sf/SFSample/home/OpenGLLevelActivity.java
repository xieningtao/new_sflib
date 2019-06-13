package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.opengl.ObjModelActivity;
import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.OpenGLVideoViewActivity;
import com.sf.SFSample.ui.OpenGLTanActivity;
import com.sf.SFSample.ui.TransformActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-11-20.
 */
public class OpenGLLevelActivity extends BaseLevelActivity {
    @Override
    protected List<Pair<String, Class>> getActivities() {
        List<Pair<String, Class>> activities = new ArrayList<>();
        activities.add( new Pair<String, Class>("image scale type open gl", OpenGLVideoViewActivity.class));
        activities.add( new Pair<String, Class>("transform open gl", TransformActivity.class));
        activities.add( new Pair<String, Class>("tan open gl", OpenGLTanActivity.class));
        activities.add( new Pair<String, Class>("3d model open gl", ObjModelActivity.class));
        return activities;
    }
}
