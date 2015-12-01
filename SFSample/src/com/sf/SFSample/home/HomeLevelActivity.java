package com.sf.SFSample.home;

import android.util.Pair;
import android.util.SparseArray;

import com.sf.SFSample.ui.BaseLevelActivity;
import com.sf.SFSample.ui.CircleTest;
import com.sf.SFSample.ui.GestureListViewActiviy;
import com.sf.SFSample.ui.GestureTest;
import com.sf.SFSample.ui.PullListActivity;
import com.sf.SFSample.ui.ReflectionActivity;
import com.sf.SFSample.ui.RoundDrawableActivity;
import com.sf.SFSample.ui.VideoShowTest;
import com.sf.SFSample.ui.ViewPagerTest;

public class HomeLevelActivity extends BaseLevelActivity {


    @Override
    protected SparseArray<Pair<String, Class>> getActivities() {
        SparseArray<Pair<String, Class>> activities = new SparseArray<>();
        activities.put(0, new Pair<String, Class>("pull", PullListActivity.class));
        activities.put(1, new Pair<String, Class>("viewPager", ViewPagerTest.class));
        activities.put(2, new Pair<String, Class>("circle", CircleTest.class));
        activities.put(3, new Pair<String, Class>("roundBitmap", RoundDrawableActivity.class));
        activities.put(4, new Pair<String, Class>("gesture", GestureTest.class));
        activities.put(5, new Pair<String, Class>("gesturePullList", GestureListViewActiviy.class));
        activities.put(6, new Pair<String, Class>("videoview", VideoShowTest.class));
        activities.put(7, new Pair<String, Class>("customView", CustomViewLevelActivity.class));
        activities.put(8, new Pair<String, Class>("reflection", ReflectionActivity.class));
        activities.put(9, new Pair<String, Class>("opengl", OpenGLLevelActivity.class));
        return activities;
    }

    public void onEvent(String str) {

    }
}
