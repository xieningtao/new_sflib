package com.sflib.emoji.core;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseFragment;
import com.sflib.CustomView.indicator.PageIndicator;
import com.sflib.CustomView.indicator.SFPageHeadView;
import com.sflib.emoji.R;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
abstract public class BaseSFEmojiPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPage;
    private EmojiPagerAdapter mAdapter;
    private PageIndicator mPageIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_emoji,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        mPageIndicator= (PageIndicator) view.findViewById(R.id.emoji_ci);
        mViewPage= (ViewPager) view.findViewById(R.id.emoji_pager);
        mViewPage.setOffscreenPageLimit(getFragmentCount());
        mAdapter=new EmojiPagerAdapter();
        mViewPage.setAdapter(mAdapter);
        mViewPage.setOnPageChangeListener(this);
        mPageIndicator.setViewPager(mViewPage);
    }


    protected abstract int getFragmentCount();

    protected abstract int getEmojiCount(int groupPosition);

    protected  Object instantiateEmojiItem(ViewGroup container, int position){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.emoji_view, null);
        GridView gridView = (GridView) view.findViewById(R.id.emoji_gv);
        gridView.setAdapter(new EmojiAdapter(position));
        container.addView(view);
        return view;
    }

    protected abstract View getEmojiItemView(int groupPosition,int subPosition,View convertView,ViewGroup parent);

    class EmojiAdapter extends BaseAdapter {
        private int mGroupPosition;

        public EmojiAdapter(int groupPosition){
            this.mGroupPosition=groupPosition;
        }
        @Override
        public int getCount() {
            return getEmojiCount(mGroupPosition);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           return getEmojiItemView(mGroupPosition,position,convertView,parent);
        }
    }

    class EmojiPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return instantiateEmojiItem(container,position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return getFragmentCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
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
