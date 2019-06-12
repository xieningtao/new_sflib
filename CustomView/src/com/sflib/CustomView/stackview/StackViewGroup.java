package com.sflib.CustomView.stackview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sf.utils.baseutil.UnitHelp;

/**
 * Created by g8876 on 2017/9/8.
 */

public class StackViewGroup extends ViewGroup implements ViewGestureHelper.HeadViewGestureListener{

    private ViewGestureHelper mGestureHelper;
    public StackViewGroup(Context context) {
        super(context,null);
    }

    public StackViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs,0);

    }

    public StackViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mGestureHelper=new ViewGestureHelper(getContext(),this);
        mGestureHelper.registerGuesture(this);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        for(int i=0;i<count;i++){
            final View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            maxWidth = Math.max(maxWidth,
                    child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            maxHeight = Math.max(maxHeight,
                    child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        }
        setMeasuredDimension(maxWidth,maxHeight);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount=getChildCount();
        int topOffset=getPaddingTop();
        int leftOffset=getPaddingLeft();
        int topOffsetGap= UnitHelp.dip2px(getContext(),20);
        int leftOffsetGap= UnitHelp.dip2px(getContext(),20);
        for(int i=0;i<childCount;i++){
            View child=getChildAt(i);
            MarginLayoutParams layoutParams= (MarginLayoutParams) child.getLayoutParams();
            int top=topOffset+topOffsetGap*(childCount-i)+layoutParams.topMargin;
            int left=leftOffset+leftOffsetGap*(childCount-i)+layoutParams.leftMargin;
            child.layout(left,top,left+child.getMeasuredWidth(),top+child.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onScroll(float distanceX, float distanceY, float dy, float dx, ViewGestureHelper.MovingMode mode) {
        return false;
    }

    @Override
    public boolean onFling(float velocityX, float velocityY, ViewGestureHelper.MovingMode mode) {
        return false;
    }

    @Override
    public boolean onUp(MotionEvent event, ViewGestureHelper.MovingMode mode) {
        return false;
    }

    @Override
    public boolean onSingleUp(MotionEvent event, ViewGestureHelper.MovingMode mode) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e, ViewGestureHelper.MovingMode mode) {
        return false;
    }
}
