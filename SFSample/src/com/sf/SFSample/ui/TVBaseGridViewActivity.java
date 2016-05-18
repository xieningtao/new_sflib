package com.sf.SFSample.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import com.basesmartframe.log.L;
import com.example.androidtv.TVBaseViewPageActivity;
import com.example.androidtv.module.home.TVGameInterface;

import java.util.WeakHashMap;

/**
 * Created by xieningtao on 15-9-11.
 */
public abstract class TVBaseGridViewActivity extends TVBaseViewPageActivity {

    private WeakHashMap<Integer, Fragment> mFragmentMap = new WeakHashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentPagerAdapter createPagerAdapter() {
        return new HomePageAdapter(getFragmentManager());
    }

    public void onEvent(TVGameInterface.CategoryCountEvent countEvent) {
        L.info(TAG, "method->onReceiveTotalPageCount,countEvent: " + countEvent);
        int count = (int) Math.ceil(countEvent.mCount * 1.0f / 12);
        if (count == mHomePageAdapter.getCount()) {
            return;
        } else {
            ((HomePageAdapter) mHomePageAdapter).setCount(count);
            notifyDataSetChange();
        }

    }

    protected abstract Fragment createFragment();

    class HomePageAdapter extends FragmentPagerAdapter {

        private int mCount = 1;

        public HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = mFragmentMap.get(i);
            if (fragment == null) {
                fragment = createFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(HORIZONTAL_EXTRA_SPACE, mHorizontalExtraSpace);
                bundle.putInt(VERTICAL_EXTRA_SPACE, mVerticalExtraSpace);
                bundle.putInt(PAGE_INDEX, i);
                fragment.setArguments(bundle);
                mFragmentMap.put(i, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        public void setCount(int count) {
            mCount = count;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
