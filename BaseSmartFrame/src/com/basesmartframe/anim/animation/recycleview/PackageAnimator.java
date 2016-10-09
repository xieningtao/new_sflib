package com.basesmartframe.anim.animation.recycleview;

/**
 * Created by mac on 16/9/25.
 */


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

public class PackageAnimator extends BaseItemAnimator {
    private SegmentAnimator mInAnimator;
    private SegmentAnimator mOutAnimator;

    public PackageAnimator(SegmentAnimator in, SegmentAnimator out) {
        super();
        mInAnimator = in;
        mOutAnimator = out;
        setAddDuration(800);
        setMoveDuration(800);
        setRemoveDuration(800);
    }

    @Override
    protected void prepareAnimateAdd(ViewHolder holder) {
        if (mInAnimator != null) {
            mInAnimator.resetDelayCount();
            mInAnimator.animationPrepare(holder);
        }
    }

    @Override
    protected void animateAddImpl(ViewHolder holder) {
        if (mInAnimator != null) {
            mInAnimator.nextDelay();
            mInAnimator.startAnimation(holder, getAddDuration(), this);
        }
    }

    @Override
    protected void animateRemoveImpl(ViewHolder holder) {
        if (mOutAnimator != null) {
            mOutAnimator.nextDelay();
            mOutAnimator.startAnimation(holder, getRemoveDuration(), this);
        }
    }

    @Override
    protected void prepareAnimateRemove(ViewHolder holder) {
        if (mOutAnimator != null) {
            mOutAnimator.resetDelayCount();
            mOutAnimator.animationPrepare(holder);
        }
    }

    @Override
    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }
}