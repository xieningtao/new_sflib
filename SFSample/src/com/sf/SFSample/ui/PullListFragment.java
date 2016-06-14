package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseadapter.checkableadapter.CheckableAdapter;
import com.basesmartframe.basehttp.SFHttpClient;
import com.basesmartframe.basethread.ThreadHelp;
import com.basesmartframe.baseui.BasePullListFragment;
import com.basesmartframe.basepull.PullHttpResult;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sf.SFSample.R;
import com.httpclient.core.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class PullListFragment extends BasePullListFragment<Student> {

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
                SFHttpClient.get("http://news.baidu.com/", new AjaxParams(),
                        new PullStudentsResult(Students.class));
            }
        }, 100);
        return true;
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View headView = LayoutInflater.from(getActivity()).inflate(
                R.layout.banner_test_fragment, null);
        addHeaderView(headView);
        doRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.sfsamplemultilistview_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, Student bean) {
        help.setText(android.R.id.text1, bean.name);
    }

    @Override
    protected BaseAdapter WrapAdapterFactory(Bundle savedInstanceState,
                                             BaseAdapter adapter, PullToRefreshListView pullListView) {
        return new CheckableAdapter(savedInstanceState, adapter);
    }

    @Override
    protected void onWrappAdapterCreated(BaseAdapter adapter,
                                         PullToRefreshListView pullListView) {
        super.onWrappAdapterCreated(adapter, pullListView);
        if (adapter instanceof CheckableAdapter) {
            CheckableAdapter checkableAdapter = (CheckableAdapter) adapter;
            checkableAdapter.setAdapterView(pullListView.getRefreshableView());
            checkableAdapter.setOnItemClickListener(this);
        }
    }

    class PullStudentsResult extends PullHttpResult<Students> {

        private String url[] = {
                "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
                "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
                "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

        public PullStudentsResult(Class<Students> classType) {
            super(classType);
        }

        @Override
        protected void onPullResult(Students t,
                                    AjaxParams params) {
            List<Student> students = new ArrayList<Student>();
            List<BannerFragment.BannerBean> banners = new ArrayList<BannerFragment.BannerBean>();
            // if(null!=t){
            // students.addAll(t.students);
            // }
            for (int i = 0; i < 10; i++) {
                students.add(new Student("students" + i, 100 * i));

            }
            for (int i = 0; i < 3; i++) {
                banners.add(new BannerFragment.BannerBean(url[i]));
            }
            EventBus.getDefault().post(banners);

            finishRefreshOrLoading(students, false);
        }

    }


    class Students {
        public List<Student> students;
    }

    public void onEvent(String str) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }
}
