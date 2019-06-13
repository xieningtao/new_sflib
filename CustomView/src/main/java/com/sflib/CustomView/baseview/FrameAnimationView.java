package com.sflib.CustomView.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.sflib.CustomView.R;


public class FrameAnimationView extends AppCompatImageView {
    private static final int SCALE_FIT_XY = 0;
    private static final int SCALE_WRAP_CONTENT = 1;

    public FrameAnimationView(Context context) {
        super(context);
        initFrameAnimationView(context, null);
    }

    public FrameAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFrameAnimationView(context, attrs);
    }

    public FrameAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFrameAnimationView(context, attrs);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (getVisibility() == visibility) {
            return;
        }

        setAnimationVisible(VISIBLE == visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setAnimationVisible(VISIBLE == getVisibility());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        setAnimationVisible(false);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        setAnimationVisible(VISIBLE == visibility);
    }

    private void initFrameAnimationView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FrameAnimationView);
        int animationDrawable = array.getResourceId(R.styleable.FrameAnimationView_frame_animation, -1);
        int animationScale = array.getInt(R.styleable.FrameAnimationView_frame_scale, SCALE_WRAP_CONTENT);
        array.recycle();

        setContentDescription(getResources().getString(R.string.none));
        setScaleType(SCALE_FIT_XY == animationScale ? ScaleType.FIT_XY : ScaleType.CENTER_INSIDE);
        if (-1 != animationDrawable) {
            setImageResource(animationDrawable);
        }
    }

    private void setAnimationVisible(boolean visible) {
        AnimationDrawable anim = (AnimationDrawable) getDrawable();
        if (null == anim) {
            return;
        }

        if (visible) {
            if (!anim.isRunning()) {
                anim.start();
            }
        } else {
            if (anim.isRunning()) {
                anim.stop();
            }
        }
    }
}
