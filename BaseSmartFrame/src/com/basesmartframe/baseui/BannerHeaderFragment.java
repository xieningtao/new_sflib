package com.basesmartframe.baseui;

import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;

import com.sflib.CustomView.autoviewpager.AutoScrollViewPager;
import com.sflib.CustomView.indicator.PageIndicator;
import com.sf.loglib.L;

import java.util.ArrayList;
import java.util.List;


public abstract class BannerHeaderFragment<T extends BannerHeaderFragment.BannerUrlAction>
        extends BaseFragment implements ViewPager.OnPageChangeListener {
    public static interface BannerUrlAction {
        String getBannerUrl();
    }

    public static interface AbsBannerViewFactory {
        ViewPager createViewpager(View rootView);

        PageIndicator createPageIndicatory(View rootView);
    }

    protected ViewPager mViewPager;
    protected PagerAdapter mBannerAdapter;
    protected PageIndicator mPageIndicator;
    private List<T> mData = new ArrayList<T>();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBaseBannerView(view);
    }

    private void initBaseBannerView(View view) {
        mData.clear();
        AbsBannerViewFactory factory = createBannerViewFactory();
        mViewPager = factory.createViewpager(view);
        mPageIndicator = factory.createPageIndicatory(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateData(List<T> data) {
        if (null == data && data.size() == 0)
            return;
        this.mData.clear();
        this.mData.addAll(data);
        mBannerAdapter = new WrappedBannerAdapter();
        mViewPager.setAdapter(mBannerAdapter);
        mPageIndicator.setViewPager(mViewPager);
//		mPageIndicator.setSnap(true);
        mViewPager.setOffscreenPageLimit(mData.size());
        if (mViewPager instanceof AutoScrollViewPager) {
            AutoScrollViewPager scrollViewPager = (AutoScrollViewPager) mViewPager;
            scrollViewPager.setScrollFactgor(5);
            scrollViewPager.startAutoScroll(2000);
        }
    }

    protected abstract View makeview(int position, ViewGroup container,
                                     String url);

    protected abstract AbsBannerViewFactory createBannerViewFactory();

    protected abstract void onPageSelected(int position, T bean);

    class WrappedBannerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = makeview(position, container, mData.get(position)
                    .getBannerUrl());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            ViewParent parent = view.getParent();
            if (null != parent) {
                ((ViewGroup) parent).removeView(view);
            }
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof View) {
                ((ViewPager) container).removeView((View) object);
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int state, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        if (0 == position) {
            position = mData.size();
        } else if (position == mData.size() + 1) {
            position = 1;
        }
        L.info(TAG, "position: " + position);
        onPageSelected(position - 1, mData.get(position - 1));
    }
}
