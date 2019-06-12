package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.sf.SFSample.R;
import com.sflib.CustomView.slideview.SlidingEvent;
import com.sflib.CustomView.slideview.SlidingView;

/**
 * Created by xieningtao on 15-11-8.
 */
public class SlidingActivity extends Activity {
    private SlidingView mSlidingView;
    private FrameLayout mLeft;
    private FrameLayout mRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideview);

        mSlidingView = (SlidingView) findViewById(R.id.content_fl);
        mLeft = (FrameLayout) findViewById(R.id.left_fl);
        mRight = (FrameLayout) findViewById(R.id.right_fl);
        mSlidingView.setSlidingEvent(new SlidingEvent() {
            @Override
            public void onSliding(SlidingMode mode) {

            }

            @Override
            public void onStartSliding(SlidingMode mode) {
                if (mode == SlidingMode.LEFT_OPEN) {
                    mLeft.setVisibility(View.VISIBLE);
                    mRight.setVisibility(View.GONE);
                } else if (mode == SlidingMode.RIGHT_OPEN) {
                    mRight.setVisibility(View.VISIBLE);
                    mLeft.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinishSliding(SlidingMode mode) {

            }
        });
    }
}
