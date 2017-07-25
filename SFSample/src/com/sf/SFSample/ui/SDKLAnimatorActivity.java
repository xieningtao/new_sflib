package com.sf.SFSample.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2017/5/19 0019.
 */

public class SDKLAnimatorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_activity);
        // previously visible view
        final View myView = findViewById(R.id.txt_tv);
        findViewById(R.id.control_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimation(myView, myView.getVisibility() != View.VISIBLE);
            }
        });
    }

    private void doAnimation(final View myView, boolean show) {
// get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

// get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();
// create the animation (the final radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if(show){
                myView.setVisibility(View.VISIBLE);
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, initialRadius);
            }else {
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.INVISIBLE);
                    }
                });
            }
// start the animation
            anim.start();
        }

    }
}
