package com.basesmartframe.gesture;

import android.view.View;

/**
 * Created by NetEase on 2016/12/8 0008.
 */

public class SlidingLeftViewHelper {

    private static SlidingLeftViewHelper mSlidingView = new SlidingLeftViewHelper();
    private SlidingLeftViewLayout mOpenView;

    private SlidingLeftViewHelper() {

    }

    public static SlidingLeftViewHelper getSlidingViewInstance() {
        return mSlidingView;
    }

    public View getOpenView() {
        return mOpenView;
    }

    public void setOpenView(SlidingLeftViewLayout openView) {
        mOpenView = openView;
    }

    public boolean hasOpenView() {
        return mOpenView != null;
    }

    public void closeOpenView() {
        if (mOpenView != null) {
            mOpenView.smoothScrollTo(0, mOpenView.getScrollY(), 0);
        }
    }
}
