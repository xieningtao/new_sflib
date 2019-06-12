package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.basepull.PullHttpResult;
import com.sf.utils.ThreadHelp;
import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseui.BasePullListFragment;
import com.sf.SFSample.R;
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
//					SFHttpClient.get("http://news.baidu.com/", new RoundResult(
//							RoundBean.class));
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

	}

	public static class RoundBean {
		public final String imUrl;

		public RoundBean(String imUrl) {
			super();
			this.imUrl = imUrl;
		}

	}
}
