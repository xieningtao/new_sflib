package com.sflib.CustomView.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xieningtao on 15-11-7.
 */
public class HotWordsView extends ViewGroup {
    private final String TAG = HotWordsView.class.getName();
    private HotWordsAdapter mAdapter;
    private int mTotalWidth;

    public HotWordsView(Context context) {
        super(context);
    }

    public HotWordsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HotWordsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAdapter != null) {
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int leftPadding = getPaddingLeft();
            int rightPadding = getPaddingRight();
            int topPadding = getPaddingTop();
            int bottomPadding = getPaddingBottom();

            int left_right_padding = leftPadding + rightPadding;
            int top_bottom_padding = topPadding + bottomPadding;
            LayoutParams params = getLayoutParams();
            if (params == null) {
                params = generateDefaultLayoutParams();
            }
            if (params.height == LayoutParams.WRAP_CONTENT) {
                parentHeight = 0;
            }

            final int count = getChildCount();

            int childrenWidth = 0;
            int totalHeight = 0;
            int totalWidth = 0;
            Log.i(TAG, "method->onMeasure,childcount: " + count);
            for (int i = 0; i < count; i++) {
                View childView = getChildAt(i);
                if (childView != null) {
                    measureItem(widthMeasureSpec, heightMeasureSpec, childView);
                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    if (totalHeight == 0) {
                        totalHeight += childHeight;
                    }
                    childrenWidth += childWidth;
                    int curWidth = childrenWidth + left_right_padding;
                    if (curWidth > parentWidth) {
                        childrenWidth = 0;
                        childrenWidth += childWidth;
                        totalHeight += childHeight;
                        totalWidth = Math.max(parentWidth, totalWidth);
                    } else {
                        totalWidth = Math.max(curWidth, totalWidth);
                    }
                }
            }
            totalHeight = Math.max(parentHeight, totalHeight);
            totalHeight += top_bottom_padding;
            mTotalWidth = totalWidth;
            setMeasuredDimension(totalWidth, totalHeight);
            Log.i(TAG, "totalWidth: " + totalWidth + " totalHeight: " + totalHeight);
        }
    }

    private void measureItem(int parentWidthMeasureSpec, int parentHeightMeasureSpec, View childView) {
        LayoutParams params = childView.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
        }
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, 0, params.width);
        int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, 0, params.height);
        childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.i(TAG, "method->onLayout childCount: " + childCount);
        if (mAdapter != null && childCount == 0) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View child = mAdapter.getView(this, i);
                ViewGroup.LayoutParams params = child.getLayoutParams();
                if (params == null) {
                    params = generateDefaultLayoutParams();
                }
                addViewInLayout(child, i, params);
            }
            requestLayout();
            Log.i(TAG, "forceLayout");
        } else if (childCount > 0) {
            Log.i(TAG, "do real layout");
            int left = getPaddingLeft() + l;
            int top = getPaddingTop() + t;
            int totalWidth = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int measureWidth = childView.getMeasuredWidth();
                int measureHeight = childView.getMeasuredHeight();
                totalWidth = left + measureWidth + getPaddingRight();
                if (totalWidth > mTotalWidth) {
                    top += measureHeight;
                    left = getPaddingLeft() + l;
                }
                int right = left + measureWidth;
                int bottom = top + measureHeight;
                Log.i(TAG, i + " left: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);
                childView.layout(left, top, right, bottom);
                left += measureWidth;
            }
        }

    }

    public void setAdapter(HotWordsAdapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            removeAllViews();
            requestLayout();
        }

    }

    public static interface HotWordsAdapter {
        View getView(ViewGroup parent, int position);

        int getCount();

        Object getItem(int position);
    }
}
