package com.basesmartframe.anim.animation.recycleview;

/**
 * Created by mac on 16/9/25.
 */

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;



public class ScaleIn extends SegmentAnimator {
    @Override
    public void animationPrepare(final RecyclerView.ViewHolder holder) {
        ViewCompat.setScaleX(holder.itemView, 0);
        ViewCompat.setScaleY(holder.itemView, 0);
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        ViewCompat.animate(holder.itemView).cancel();
        ViewCompat.animate(holder.itemView).scaleX(1f).scaleY(1).setDuration(duration).setStartDelay(mDelayCount * mDelay).setListener(new BaseItemAnimator.VpaListenerAdapter() {
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
