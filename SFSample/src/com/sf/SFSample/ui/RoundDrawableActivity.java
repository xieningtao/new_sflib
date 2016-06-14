package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.basehttp.SFHttpClient;
import com.basesmartframe.basepull.PullHttpResult;
import com.basesmartframe.basethread.ThreadHelp;
import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseui.BasePullListFragment;
import com.sf.SFSample.R;
import com.httpclient.core.AjaxParams;

import java.util.ArrayList;

public class RoundDrawableActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.round_test);
		getFragmentManager().beginTransaction()
				.replace(R.id.round_fl, new RoundDrawableFragment()).commit();
	}

	public static class RoundDrawableFragment extends
			BasePullListFragment<RoundBean> {

		public RoundDrawableFragment() {

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}

		@Override
		protected boolean onRefresh() {
			ThreadHelp.runInSingleBackThread(new Runnable() {

				@Override
				public void run() {
					SFHttpClient.get("http://news.baidu.com/", new RoundResult(
							RoundBean.class));
				}
			}, 100);
			return false;
		}

		@Override
		protected boolean onLoadMore() {
			return false;
		}

		@Override
		protected int[] getLayoutIds() {
			return new int[] { R.layout.roundimage_test_item };
		}

		@Override
		protected void bindView(BaseAdapterHelper help, int position,
				RoundBean bean) {
			help.setImageBuilder(R.id.round_iv0, bean.imUrl);
			help.setImageBuilder(R.id.round_iv1, bean.imUrl);
			help.setImageBuilder(R.id.round_iv2, bean.imUrl);
			help.setImageBuilder(R.id.round_iv3, bean.imUrl);
		}

		class RoundResult extends PullHttpResult<RoundBean> {
			private String url[] = {
					"http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
					"http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
					"http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg" };

			public RoundResult(Class<RoundBean> classType) {
				super(classType);
			}

			@Override
			protected void onPullResult(
					RoundBean t,
					AjaxParams params) {
				ArrayList<RoundBean> beans = new ArrayList<RoundBean>();
				for (int i = 0; i < 10; i++) {
					beans.add(new RoundBean(url[i % 3]));
				}
				finishRefreshOrLoading(beans, false);
			}

		}

	}

	public static class RoundBean {
		public final String imUrl;

		public RoundBean(String imUrl) {
			super();
			this.imUrl = imUrl;
		}

	}
}
