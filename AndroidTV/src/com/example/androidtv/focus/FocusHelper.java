package com.example.androidtv.focus;

import android.animation.TimeAnimator;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.support.v17.leanback.graphics.ColorOverlayDimmer;
import android.support.v17.leanback.widget.ShadowOverlayContainer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.androidtv.R;


/**
 * Created by xieningtao on 15-9-22.
 */
public class FocusHelper {
    public static void registerFocus(View view) {
        final BrowseItemFocusHighlight highlight = new BrowseItemFocusHighlight(Boolean.FALSE);
        highlight.onInitializeView(view);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                highlight.onItemFocused(v, hasFocus);
            }
        });
    }

    public static class BrowseItemFocusHighlight implements FocusHandler {
        private static final int DURATION_MS = 150;
        private final boolean mUseDimmer;

        public BrowseItemFocusHighlight( boolean useDimmer) {
            this.mUseDimmer = useDimmer;
        }


        private float getScale(Resources res) {
            return 1.2f;
        }

        public void onItemFocused(View view, boolean hasFocus) {
            view.setSelected(hasFocus);
            this.getOrCreateAnimator(view).animateFocus(hasFocus, false);
        }

        public void onInitializeView(View view) {
            this.getOrCreateAnimator(view).animateFocus(false, true);
        }

        private FocusHelper.FocusAnimator getOrCreateAnimator(View view) {
            FocusHelper.FocusAnimator animator = (FocusHelper.FocusAnimator) view.getTag(R.id.lb_focus_animator);
            if (animator == null) {
                animator = new FocusHelper.FocusAnimator(view, this.getScale(view.getResources()), this.mUseDimmer, 150);
                view.setTag(R.id.lb_focus_animator, animator);
            }

            return animator;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static class FocusAnimator implements TimeAnimator.TimeListener {
        private final View mView;
        private final int mDuration;
        private final ShadowOverlayContainer mWrapper;
        private final float mScaleDiff;
        private float mFocusLevel = 0.0F;
        private float mFocusLevelStart;
        private float mFocusLevelDelta;
        private final TimeAnimator mAnimator = new TimeAnimator();
        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
        private final ColorOverlayDimmer mDimmer;

        void animateFocus(boolean select, boolean immediate) {
            this.endAnimation();
            float end = select ? 1.0F : 0.0F;
            if (immediate) {
                this.setFocusLevel(end);
            } else if (this.mFocusLevel != end) {
                this.mFocusLevelStart = this.mFocusLevel;
                this.mFocusLevelDelta = end - this.mFocusLevelStart;
                this.mAnimator.start();
            }

        }

        FocusAnimator(View view, float scale, boolean useDimmer, int duration) {
            this.mView = view;
            this.mDuration = duration;
            this.mScaleDiff = scale - 1.0F;
            if (view instanceof ShadowOverlayContainer) {
                this.mWrapper = (ShadowOverlayContainer) view;
            } else {
                this.mWrapper = null;
            }

            this.mAnimator.setTimeListener(this);
            if (this.mWrapper != null && useDimmer) {
                this.mDimmer = ColorOverlayDimmer.createDefault(view.getContext());
            } else {
                this.mDimmer = null;
            }

        }

        void setFocusLevel(float level) {
            this.mFocusLevel = level;
            float scale = 1.0F + this.mScaleDiff * level;
            this.mView.setScaleX(scale);
            this.mView.setScaleY(scale);
            if (this.mWrapper != null) {
                this.mWrapper.setShadowFocusLevel(level);
                if (this.mDimmer != null) {
                    this.mDimmer.setActiveLevel(level);
                    this.mWrapper.setOverlayColor(this.mDimmer.getPaint().getColor());
                }
            }

        }

        float getFocusLevel() {
            return this.mFocusLevel;
        }

        void endAnimation() {
            this.mAnimator.end();
        }

        public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
            float fraction;
            if (totalTime >= (long) this.mDuration) {
                fraction = 1.0F;
                this.mAnimator.end();
            } else {
                fraction = (float) ((double) totalTime / (double) this.mDuration);
            }

            if (this.mInterpolator != null) {
                fraction = this.mInterpolator.getInterpolation(fraction);
            }

            this.setFocusLevel(this.mFocusLevelStart + fraction * this.mFocusLevelDelta);
        }
    }
}
