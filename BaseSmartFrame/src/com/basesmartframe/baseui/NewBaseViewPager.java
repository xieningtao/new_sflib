package com.basesmartframe.baseui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.basesmartframe.baseview.indicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-12-28.
 */
public abstract class NewBaseViewPager<T extends BaseViewPager.ViewPagerTitle>
        extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static interface ViewPagerTitle {
        String composeTitle();
    }

    protected PageIndicator mPageIndicator;
    protected ViewPager mViewPager;
    protected VideoShowListAdapter mAdapter;

    private final List<T> data = new ArrayList<T>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void setUpIndicatorViewPager(PageIndicator indicator, ViewPager viewPager) {
        mPageIndicator = indicator;
        mViewPager = viewPager;
        mAdapter = new VideoShowListAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    protected void notifyDatasetChange() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * add all  title data,subclass should invoke this method to update title
     *
     * @param titleData
     */
    protected void updateIndicator(List<T> titleData) {
        if (null == titleData) return;
        data.clear();
        data.addAll(titleData);
        mPageIndicator.setViewPager(mViewPager);
        mPageIndicator.setOnPageChangeListener(this);
        mAdapter.notifyDataSetChanged();
    }

    protected abstract Fragment getFragment(int i);

    protected Bundle getBundle(int i) {
        return null;
    }

    class VideoShowListAdapter extends FragmentPagerAdapter {

        public VideoShowListAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = getFragment(i);
            Bundle bundle = getBundle(i);
            if (null != fragment && null != bundle) {
                fragment.setArguments(bundle);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).composeTitle();
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

    }
}
