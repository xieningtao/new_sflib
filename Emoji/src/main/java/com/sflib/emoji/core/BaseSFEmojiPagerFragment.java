package com.sflib.emoji.core;

import android.os.Bundle;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.basesmartframe.baseui.BaseFragment;
import com.sflib.CustomView.indicator.PageIndicator;
import com.sflib.emoji.R;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
abstract public class BaseSFEmojiPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPage;
    private EmojiPagerAdapter mAdapter;
    private PageIndicator mPageIndicator;
    private ViewGroup mContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_emoji, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        mContainer = (FrameLayout) view.findViewById(R.id.emoji_page_cotainer);
        mPageIndicator = (PageIndicator) view.findViewById(R.id.emoji_ci);
        mViewPage = (ViewPager) view.findViewById(R.id.emoji_pager);
        mViewPage.setOffscreenPageLimit(getFragmentCount());
        mAdapter = new EmojiPagerAdapter();
        mViewPage.setAdapter(mAdapter);
        mViewPage.setOnPageChangeListener(this);
        mPageIndicator.setViewPager(mViewPage);
    }

    protected void showLoaderView(boolean show) {
        if (show) {
            mContainer.setVisibility(View.VISIBLE);
            final View emojiPb = mContainer.findViewById(R.id.download_emoji_pb);
            final View downloadEmojiBt = mContainer.findViewById(R.id.download_emoji_bt);
            downloadEmojiBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emojiPb.setVisibility(View.VISIBLE);
                    downloadEmojiBt.setVisibility(View.GONE);
                    onDownLoadEmojiClick(mContainer);
                }
            });
        } else {
            mContainer.setVisibility(View.GONE);
        }
    }

    protected void notifyDatasetChange() {
        mAdapter.notifyDataSetChanged();
    }

    public ViewPager getViewPage() {
        return mViewPage;
    }

    protected void onDownLoadEmojiClick(ViewGroup progressBar) {

    }

    protected abstract int getFragmentCount();

    protected abstract int getEmojiCount(int groupPosition);

    protected Object instantiateEmojiItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.emoji_view, null);
        GridView gridView = (GridView) view.findViewById(R.id.emoji_gv);
        gridView.setAdapter(new EmojiAdapter(position));
        container.addView(view);
        return view;
    }

    protected abstract View getEmojiItemView(int groupPosition, int subPosition, View convertView, ViewGroup parent);

    class EmojiAdapter extends BaseAdapter {
        private int mGroupPosition;

        public EmojiAdapter(int groupPosition) {
            this.mGroupPosition = groupPosition;
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
            return getEmojiItemView(mGroupPosition, position, convertView, parent);
        }
    }

    class EmojiPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return instantiateEmojiItem(container, position);
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
            return view == object;
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
