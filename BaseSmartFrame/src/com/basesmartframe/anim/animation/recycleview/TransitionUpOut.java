package com.basesmartframe.anim.animation.recycleview;

/**
 * Created by mac on 16/9/25.
 */

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;


public class TransitionUpOut extends SegmentAnimator {

    @Override
    public void animationPrepare(ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, 0);
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationX(0).translationY(-ViewUtils.getScreenHeight()).setDuration(duration).setStartDelay(mDelayCount * mDelay).setListener(new BaseItemAnimator.VpaListenerAdapter() {
            @Override
            public void onAnimationCancel(View view) {
                ViewCompat.setTranslationY(view, 0);
            }

            @Override
            public void onAnimationEnd(View view) {
                animator.dispatchAddFinished(holder);
                animator.mAddAnimations.remove(holder);
                animator.dispatchFinishedWhenDone();
            }
        }).start();

    }

}
