package com.example.androidtv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.basesmartframe.basethread.ThreadHelp;
import com.basesmartframe.baseui.BannerHeaderFragment;
import com.basesmartframe.baseview.indicator.PageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import android.support.v13.*;
/**
 * Created by xieningtao on 15-12-28.
 */
public class HomeBannerFragment extends BannerHeaderFragment<HomeBannerFragment.BannerBean> implements BannerHeaderFragment.AbsBannerViewFactory {

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

    private List<BannerBean> banners = new ArrayList<>();

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
        return inflater.inflate(R.layout.banner_viewpager_layout, null);
    }

    @Override
    protected View makeview(int position, ViewGroup container, String url) {
//		if (views.size() - 1 >= position) {
//			ImageView iv = (ImageView) views.get(position);
//			ImageLoader.getInstance().displayImage(url, iv);
//			return iv;
//		} else {
        ImageView iv = new ImageView(getActivity());
        iv.setScaleType(ImageView.ScaleType.CENTER);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageLoader.getInstance().displayImage(url, iv);
//			views.add(iv);
        return iv;
//		}
    }


    @Override
    protected void onPageSelected(int position, BannerBean bean) {
        // TODO Auto-generated method stub

    }

    private String url[] = {
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
            "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};


    @Override
    public void onResume() {
        super.onResume();

        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                banners.clear();
                for (int i = 0; i < 3; i++) {
                    banners.add(new HomeBannerFragment.BannerBean(url[i]));
                }
                EventBus.getDefault().post(banners);
            }
        }, 2000);

    }
}

