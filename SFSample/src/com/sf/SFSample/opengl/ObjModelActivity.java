package com.sf.SFSample.opengl;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basesmartframe.baseui.BaseActivity;
import com.nostra13.universalimageloader.utils.L;
import com.sf.SFSample.R;
import com.sflib.openGL.model.LoadMaxModelHelper;
import com.sflib.openGL.model.XModelSurfaceView;
import com.sflib.reflection.core.ThreadHelp;

/**
 * Created by NetEase on 2016/11/8 0008.
 */

public class ObjModelActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obj_view);
        final FrameLayout viewContainer = (FrameLayout) findViewById(R.id.model_view_container);
        final LoadMaxModelHelper modelHelper = new LoadMaxModelHelper();
        ThreadHelp.runInSingleBackThread(new Runnable() {
            @Override
            public void run() {
                try {
                    modelHelper.loadObjModel(getAssets(), "cubic6.obj");
                } catch (Exception e) {
                    L.e(TAG, "ObjModelActivity.onCreate exception: " + e);
                }
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        XModelSurfaceView mXModelSurfaceView = new XModelSurfaceView(ObjModelActivity.this);
                        mXModelSurfaceView.setHelper(modelHelper);
                        viewContainer.addView(mXModelSurfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                });
            }
        }, 0);
    }
}
