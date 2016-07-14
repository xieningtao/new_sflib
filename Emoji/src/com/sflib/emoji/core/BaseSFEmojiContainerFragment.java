package com.sflib.emoji.core;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseui.BaseFragment;
import com.sflib.CustomView.indicator.SFPageHeadView;
import com.sflib.emoji.R;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
abstract public class BaseSFEmojiContainerFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    protected ViewPager mViewPager;
    private SFPageHeadView mPageHeadView;
    private EmojiContainerAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container_emoji,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        mPageHeadView = (SFPageHeadView) view.findViewById(R.id.head_tabs);
        mViewPager = (ViewPager)view.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(getFragmentCount());
        mAdapter = new EmojiContainerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPageHeadView.setViewPager(mViewPager);
        mPageHeadView.setOnPageChangeListener(this);
    }

    protected abstract Fragment getFragment(int i);

    protected abstract int getFragmentCount();

    protected abstract View getTabHeadView(LayoutInflater inflater, int position, View parent);

    protected Bundle getBundle(int i) {
        return null;
    }

    class EmojiContainerAdapter extends FragmentPagerAdapter implements SFPageHeadView.SFPageHeadAdapter {

        public EmojiContainerAdapter(FragmentManager fm) {
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
            return getFragmentCount();
        }

        @Override
        public View getHeadView(LayoutInflater inflater, int position, View parent) {
            return getTabHeadView(inflater,position,parent);
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
