package com.sflib.CustomView.viewgroup;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.sf.loglib.L;
import com.sf.utils.baseutil.UnitHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/14 0014.
 */
public class LivePopView extends ViewGroup {
    private final String TAG = LivePopView.class.getName();
    private BaseLivePopAdapter mLivePopAdapter;
    private List<View> mRecycledView = new ArrayList<>();
    private List<View> mRecyclingView = new ArrayList<>();
    private LayoutTransition mLayoutTransition = new LayoutTransition();

    public LivePopView(Context context) {
        this(context, null);
    }

    public LivePopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivePopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measureHeight = getMeasuredHeight();
        int measureWidth = getMeasuredWidth();
        int count = getChildCount();
        int curHeight = 0;
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int childMeasureHeight = child.getMeasuredHeight();
            int childMeasureWidth = child.getMeasuredWidth();
            curHeight += childMeasureHeight;
            int left = l;
            int top = measureHeight - curHeight;
            int right = l + childMeasureWidth;
            int bottom = top + childMeasureHeight;
            child.layout(left, top, right, bottom);
        }
    }

    private void init() {
        initTransition();
        initListener();
    }

    TimeInterpolator DECEL_INTERPOLATOR = new DecelerateInterpolator();

    private void initTransition() {

        mLayoutTransition.setAnimateParentHierarchy(true);
        //设置每个动画持续的时间
//        mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 1000);
//        mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 1000);
//        mLayoutTransition.setStagger(LayoutTransition.APPEARING, 1000);
//        mLayoutTransition.setStagger(LayoutTransition.DISAPPEARING, 1000);

        mLayoutTransition.setDuration(LayoutTransition.APPEARING, 400);
        mLayoutTransition.setStartDelay(LayoutTransition.APPEARING,0);
//        mLayoutTransition.setDuration(LayoutTransition.DISAPPEARING, 1000);
        mLayoutTransition.setDuration(LayoutTransition.CHANGE_APPEARING, 500);
        mLayoutTransition.setInterpolator(LayoutTransition.CHANGE_APPEARING,DECEL_INTERPOLATOR);
//        mLayoutTransition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, 1000);
        mLayoutTransition.setDuration(LayoutTransition.CHANGING, 500);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            mLayoutTransition.disableTransitionType(LayoutTransition.CHANGING);
            mLayoutTransition.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
            mLayoutTransition.enableTransitionType(LayoutTransition.APPEARING);
            mLayoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            mLayoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
        }
        int d = UnitHelp.dip2px(getContext(), 50);
        ObjectAnimator mAnimatorAppearing = ObjectAnimator.ofFloat(this, "translationY", d, 0);
        //为LayoutTransition设置动画及动画类型
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING,mAnimatorAppearing);

//        PropertyValuesHolder disappearingTranslateY = PropertyValuesHolder.ofFloat("translationY", 0, -d);
//        ObjectAnimator mAnimatorDisappearing = ObjectAnimator.ofPropertyValuesHolder(this, disappearingTranslateY);
//        //为LayoutTransition设置动画及动画类型
//        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, mAnimatorDisappearing);
//
//
//        ObjectAnimator mAnimatorChangeDisappearing = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f);
//        //为LayoutTransition设置动画及动画类型
//        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, mAnimatorChangeDisappearing);
//

//        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 40, 10);
//        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 30, 100);
//        PropertyValuesHolder pvhScrollX = PropertyValuesHolder.ofInt("scrollX", 0, 1);
//        PropertyValuesHolder pvhScrollY = PropertyValuesHolder.ofInt("scrollY", 0, 1);
        ObjectAnimator defaultChangeIn = ObjectAnimator.ofPropertyValuesHolder((Object)null,
//                pvhLeft, , pvhRight, pvhBottom,pvhScrollX,
                pvhTop,pvhBottom);
        defaultChangeIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               L.info(TAG,"updateValue: " +animation.getAnimatedValue("bottom"));
            }
        });

//        ObjectAnimator mAnimatorChangeAppearingTranslationY = ObjectAnimator.ofFloat(null, "translationY", 0, -d);
//        mAnimatorChangeAppearingTranslationY.setDuration(500);
        //为LayoutTransition设置动画及动画类型
        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, defaultChangeIn);

        ObjectAnimator mAnimatorChangingTranslationY = ObjectAnimator.ofFloat(this, "translationY", 0, -d);
        mLayoutTransition.setAnimator(LayoutTransition.CHANGING, mAnimatorChangingTranslationY);
        //为mImageViewGroup设置mLayoutTransition对象
        setLayoutTransition(mLayoutTransition);
    }

    private void initListener() {
        mLayoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                L.info(TAG, "startTransition transitionType: " + transitionType + " view" + view);
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                L.info(TAG, "endTransition transitionType: " + transitionType + " view" + view);
            }
        });
    }

    public void addView() {

    }

    public void setAdapter(BaseLivePopAdapter baseLivePopAdapter) {
        mLivePopAdapter = baseLivePopAdapter;

    }

    private void doRecycleView() {

    }

    public void push() {
        View rootView = null;
        if (!mRecycledView.isEmpty()) {
            rootView = mRecycledView.get(0);
        }
        rootView = mLivePopAdapter.getView(rootView);

        addView(rootView);
    }

    public void pop() {

    }
}
