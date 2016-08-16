package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseadapter.checkableadapter.CheckableAdapter;
import com.sf.httpclient.newcore.MethodType;
import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.httpclient.newcore.SFRequest;
import com.basesmartframe.request.SFResponseCallback;
import com.basesmartframe.baseui.BasePullListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sf.SFSample.R;
import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.SFHttpStringCallback;

import java.util.ArrayList;
import java.util.List;


public class PullListFragment extends BasePullListFragment<Student> {

    private void doRequest() {

    }

    @Override
    protected boolean onRefresh() {
        String httpUrl = "http://news.baidu.com/";
        SFRequest _request = new SFRequest(httpUrl, MethodType.GET) {
            @Override
            public Class getClassType() {
                return Student.class;
            }
        };
        SFHttpGsonHandler sfHttpGsonHandler = new SFHttpGsonHandler(_request, new SFHttpStringCallback<Student>() {

            private String url[] = {
                    "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
                    "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
                    "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

            @Override
            public void onSuccess(SFRequest request,Student g) {
                List<Student> students = new ArrayList<Student>();
                List<BannerFragment.BannerBean> banners = new ArrayList<BannerFragment.BannerBean>();
                for (int i = 0; i < 10; i++) {
                    students.add(new Student("students" + i, 100 * i));

                }
                for (int i = 0; i < 3; i++) {
                    banners.add(new BannerFragment.BannerBean(url[i]));
                }
                mBannerFt.updateData(banners);

                finishRefreshOrLoading(students, false);
            }

            @Override
            public void onFailed(SFRequest request,Exception e) {
                List<Student> students = new ArrayList<Student>();
                List<BannerFragment.BannerBean> banners = new ArrayList<BannerFragment.BannerBean>();
                for (int i = 0; i < 10; i++) {
                    students.add(new Student("students" + i, 100 * i));

                }
                for (int i = 0; i < 3; i++) {
                    banners.add(new BannerFragment.BannerBean(url[i]));
                }
                mBannerFt.updateData(banners);

                finishRefreshOrLoading(students, false);
            }
        });
        sfHttpGsonHandler.start();
        return true;
    }

    class ImageResponse implements SFResponseCallback<Students> {

        private String url[] = {
                "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
                "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
                "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

        @Override
        public void onResult(boolean success, Students bean) {
            List<Student> students = new ArrayList<Student>();
            List<BannerFragment.BannerBean> banners = new ArrayList<BannerFragment.BannerBean>();
            for (int i = 0; i < 10; i++) {
                students.add(new Student("students" + i, 100 * i));

            }
            for (int i = 0; i < 3; i++) {
                banners.add(new BannerFragment.BannerBean(url[i]));
            }
            mBannerFt.updateData(banners);

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

    private BannerFragment mBannerFt;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View headView = LayoutInflater.from(getActivity()).inflate(
                R.layout.banner_test_fragment, null);
        mBannerFt = new BannerFragment();
        getFragmentManager().beginTransaction().replace(R.id.banner_containner, mBannerFt).commit();
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


    public static class Students {
        public List<Student> students;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }
}
