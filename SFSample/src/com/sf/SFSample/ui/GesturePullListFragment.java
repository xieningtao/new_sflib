package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.basehttp.SFHttpClient;
import com.basesmartframe.basepull.PullHttpResult;
import com.basesmartframe.basethread.ThreadHelp;
import com.basesmartframe.baseui.BasePullListFragment;
import com.basesmartframe.baseutil.SystemUIWHHelp;
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
		ThreadHelp.runInSingleBackThread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SFHttpClient.get("http://news.baidu.com/", null,
						new PullStudentsResult(Student.class));
			}
		}, 100);
		return true;
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

	class PullStudentsResult extends PullHttpResult<Student> {

		public PullStudentsResult(Class<Student> classType) {
			super(classType);
		}

		@Override
		protected void onPullResult(Student t,
				AjaxParams params) {
			List<Student> students = new ArrayList<Student>();
			for (int i = 0; i < 10; i++) {
				students.add(new Student("students" + i, 100 * i));

			}

			finishRefreshOrLoading(students, false);
		}

	}

}
