package com.sf.SFSample.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.basesmartframe.baseui.BannerHeaderFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerFragment extends
		BannerHeaderFragment<BannerFragment.BannerBean> {

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
