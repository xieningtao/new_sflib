package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.basesmartframe.baseui.BannerHeaderFragment;
import com.basesmartframe.baseview.indicator.PageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerFragment extends
        BannerHeaderFragment<BannerFragment.BannerBean> implements BannerHeaderFragment.AbsBannerViewFactory {

    public static class BannerBean implements
            BannerHeaderFragment.BannerUrlAction {

        private final String url;

        public BannerBean(String url) {
            super();
            this.url = url;
        }

        @Override
        public String getBannerUrl() {
            return url;
        }

    }

    private List<View> views = new ArrayList<View>();

    @Override
    public ViewPager createViewpager(View rootView) {
        return (ViewPager) rootView.findViewById(R.id.scroll_pager);
    }

    @Override
    public PageIndicator createPageIndicatory(View rootView) {
        return (PageIndicator) rootView.findViewById(R.id.circle_indicator);
    }

    @Override
    protected AbsBannerViewFactory createBannerViewFactory() {
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.basesmartframe.R.layout.banner_viewpager_layout, null);
    }

    @Override
    protected View makeview(int position, ViewGroup container, String url) {
//		if (views.size() - 1 >= position) {
//			ImageView iv = (ImageView) views.get(position);
//			ImageLoader.getInstance().displayImage(url, iv);
//			return iv;
//		} else {
        ImageView iv = new ImageView(getActivity());
        iv.setScaleType(ScaleType.CENTER);
        iv.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ImageLoader.getInstance().displayImage(url, iv);
//			views.add(iv);
        return iv;
//		}
    }

    @Override
    protected void onPageSelected(int position, BannerBean bean) {
        // TODO Auto-generated method stub

    }
}
