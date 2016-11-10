package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.opengl.ObjModelActivity;
import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.BasicOpenGLES20Activity;
import com.sf.SFSample.ui.OpenGLTanActivity;
import com.sf.SFSample.ui.TransformActivity;

/**
 * Created by xieningtao on 15-11-20.
 */
public class OpenGLLevelActivity extends BaseLevelActivity {
    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("basic open gl", BasicOpenGLES20Activity.class));
        activities.put(1, new Pair<String, Class>("transform open gl", TransformActivity.class));
        activities.put(2, new Pair<String, Class>("tan open gl", OpenGLTanActivity.class));
        activities.put(3, new Pair<String, Class>("3d model open gl", ObjModelActivity.class));
        return activities;
    }
}
