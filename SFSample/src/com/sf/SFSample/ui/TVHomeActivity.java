package com.sf.SFSample.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.basesmartframe.baseui.BaseViewPager;
import com.basesmartframe.baseui.NewBaseViewPager;
import com.basesmartframe.baseview.indicator.PageIndicator;
import com.basesmartframe.baseview.indicator.TabPageIndicator;
import com.sf.SFSample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-12-28.
 */
public class TVHomeActivity extends NewBaseViewPager<TVHomeActivity.HomeTitle> {

    public static class HomeTitle implements BaseViewPager.ViewPagerTitle {

        private final String name;

        public HomeTitle(String name) {
            this.name = name;
        }

        @Override
        public String composeTitle() {
            return name;
        }

    }

    private List<HomeTitle> mHomeTitles = new ArrayList<HomeTitle>();

    private PageIndicator mPageIndicator;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_viewpager);
        init();
        mHomeTitles.add(new HomeTitle("aa"));
        mHomeTitles.add(new HomeTitle("bb"));
        mHomeTitles.add(new HomeTitle("cc"));
        mHomeTitles.add(new HomeTitle("dd"));

        updateIndicator(mHomeTitles);

    }

    private void init() {
        mPageIndicator = (TabPageIndicator) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setUpIndicatorViewPager(mPageIndicator, mViewPager);
    }

    @Override
    protected Fragment getFragment(int i) {
        return new TVHorizontalGridViewFragment();
    }


}
