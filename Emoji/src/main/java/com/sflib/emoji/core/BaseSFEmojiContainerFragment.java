package com.sflib.emoji.core;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import androidx.legacy.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseui.BaseFragment;
import com.sflib.CustomView.indicator.SFPageHeadView;
import com.sflib.emoji.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
abstract public class BaseSFEmojiContainerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    protected ViewPager mViewPager;
    private SFPageHeadView mPageHeadView;
    private EmojiContainerAdapter mAdapter;

    private Map<Integer, Fragment> mFragmentMap = new HashMap<Integer, Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container_emoji, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mPageHeadView = view.findViewById(R.id.head_tabs);
        mViewPager = view.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(getFragmentCount());
        mAdapter = new EmojiContainerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPageHeadView.setViewPager(mViewPager);
        mPageHeadView.setOnPageChangeListener(this);
        mPageHeadView.setOnHeadItemClick(new SFPageHeadView.OnHeadItemClick() {
            @Override
            public void onHeadItemClick(int position) {
                Fragment fragment = mFragmentMap.get(position);
                if (fragment instanceof BaseSFEmojiPagerFragment) {
                    BaseSFEmojiPagerFragment emojiPagerFragment = (BaseSFEmojiPagerFragment) fragment;
                    ViewPager viewPager = emojiPagerFragment.getViewPage();
                    if (viewPager != null) {
                        viewPager.setCurrentItem(0);
                    }
                }
            }
        });
    }

    protected void notifyDataSetChange() {
        mAdapter.notifyDataSetChanged();
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
            if (mFragmentMap.get(i) != null) {
                return mFragmentMap.get(i);
            }
            Fragment fragment = getFragment(i);
            Bundle bundle = getBundle(i);
            if (null != fragment && null != bundle) {
                fragment.setArguments(bundle);
            }
            mFragmentMap.put(i, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return getFragmentCount();
        }

        @Override
        public View getHeadView(LayoutInflater inflater, int position, View parent) {
            return getTabHeadView(inflater, position, parent);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        L.info(TAG,TAG+".onPageScrollStateChanged state: "+state);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        L.info(TAG,TAG+".onPageScrolled,position: "+position+" positionOffset: "+positionOffset+" positionOffsetPixels: "+positionOffsetPixels);
    }


    @Override
    public void onPageSelected(int position) {
//        L.info(TAG,TAG+".onPageSelected,position: "+position);

    }
}
