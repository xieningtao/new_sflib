package com.sflib.CustomView.indicator;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.sflib.CustomView.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by NetEase on 2016/7/8 0008.
 */
public class SFPageHeadView extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    public interface SFPageHeadAdapter{
        View getHeadView(LayoutInflater inflater,int position, View parent);
    }
    public interface OnHeadItemClick{
        void onHeadItemClick(int position);
    }
    private LinearLayout mTabLayout;

    private ViewPager mViewPager;

    private LayoutInflater mLayoutInflater;

    private SFPageHeadAdapter mHeadAdapter;

    private OnHeadItemClick mOnHeadItemClick;

    private int mSelectedTabIndex;

    private ViewPager.OnPageChangeListener mListener;

    public SFPageHeadView(Context context) {
        this(context,null,0);
    }

    public SFPageHeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SFPageHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnHeadItemClick(OnHeadItemClick headItemClick){
        this.mOnHeadItemClick=headItemClick;
    }
    public void setHeadAdapter(SFPageHeadAdapter adapter){
        this.mHeadAdapter=adapter;
    }
    private void init(){
        setHorizontalScrollBarEnabled(false);
        mLayoutInflater=LayoutInflater.from(getContext());
        View rootView =mLayoutInflater.inflate(R.layout.layout_sf_page_head,null);
        mTabLayout= rootView.findViewById(R.id.head_container);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));

    }

    private Runnable mTabSelector;

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
//        setFillViewport(lockedExpanded);
//
//        final int childCount = mTabLayout.getChildCount();
//        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
//            if (childCount > 2) {
//                mMaxTabWidth = (int)(MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
//            } else {
//                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
//            }
//        } else {
//            mMaxTabWidth = -1;
//        }
//
//        final int oldWidth = getMeasuredWidth();
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int newWidth = getMeasuredWidth();
//
//        if (lockedExpanded && oldWidth != newWidth) {
//            // Recenter the tab display if we're at a new (scrollable) size.
//            setCurrentItem(mSelectedTabIndex);
//        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    private void addHeadItemView(View itemView, final int positon){
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentItem(positon);
                if(mOnHeadItemClick!=null){
                    mOnHeadItemClick.onHeadItemClick(positon);
                }
            }
        });
        mTabLayout.addView(itemView, new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
    }
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        mHeadAdapter=null;
        SFPageHeadAdapter headAdapter = null;
        if (adapter instanceof SFPageHeadAdapter) {
            mHeadAdapter = (SFPageHeadAdapter)adapter;
        }
        if(mHeadAdapter!=null) {
            final int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                View headItemView = mHeadAdapter.getHeadView(mLayoutInflater,i,mTabLayout);
                if(headItemView!=null){
                    addHeadItemView(headItemView,i);
                }
            }
            if (mSelectedTabIndex > count) {
                mSelectedTabIndex = count - 1;
            }
            setCurrentItem(mSelectedTabIndex);
            requestLayout();
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item);

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }
}
