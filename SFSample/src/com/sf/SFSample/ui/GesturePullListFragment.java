package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.sf.httpclient.newcore.MethodType;
import com.basesmartframe.request.SFHttpRequestImpl;
import com.sf.httpclient.newcore.SFRequest;
import com.basesmartframe.request.SFResponseCallback;
import com.basesmartframe.baseui.BasePullListFragment;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.sf.SFSample.R;
import com.sf.httpclient.core.AjaxParams;

import java.util.ArrayList;
import java.util.List;

public class GesturePullListFragment extends BasePullListFragment<Student> {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		doRefresh();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}


	@Override
	protected boolean onRefresh() {
		String httpUrl = "http://news.baidu.com/";

		SFHttpRequestImpl request = new SFHttpRequestImpl();
		SFRequest _request = new SFRequest(httpUrl, MethodType.GET) {
			@Override
			public Class getClassType() {
				return Student.class;
			}
		};
		request.getData(_request,new ImageResponse());
		return true;
	}

	class ImageResponse implements SFResponseCallback<PullListFragment.Students> {

		private String url[] = {
				"http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
				"http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
				"http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

		@Override
		public void onResult(boolean success, PullListFragment.Students bean) {
			List<Student> students = new ArrayList<Student>();
			for (int i = 0; i < 10; i++) {
				students.add(new Student("students" + i, 100 * i));

			}
			finishRefreshOrLoading(students, false);
		}

		@Override
		public void onStart(AjaxParams params) {

		}

		@Override
		public void onLoading(long count, long current) {

		}
	}

	@Override
	protected boolean onLoadMore() {
		return false;
	}

	@Override
	protected int[] getLayoutIds() {
		return new int[] { R.layout.gesture_list_item };
	}

	@Override
	protected void bindView(BaseAdapterHelper help, int position, Student bean) {
		LayoutParams params = help.getView(R.id.first_ll).getLayoutParams();
		params.width = SystemUIWHHelp.getScreenRealWidth(getActivity());
		help.getView(R.id.first_ll).setLayoutParams(params);
	}

}
