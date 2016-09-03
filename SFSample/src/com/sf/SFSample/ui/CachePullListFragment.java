package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseadapter.checkableadapter.CheckableAdapter;
import com.basesmartframe.baseui.BasePullListFragment;
import com.basesmartframe.request.SFHttpGsonCacheHandler;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sf.SFSample.R;
import com.sf.httpclient.newcore.HttpType;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.httpclient.newcore.cache.CacheType;
import com.sf.httpclient.newcore.cache.SFCacheRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class CachePullListFragment extends BasePullListFragment<Student> {

    @Override
    protected boolean onRefreshNoNetwork() {
        doRealRequest();
        return true;
    }

    @Override
    protected boolean onRefresh() {
        doRealRequest();
        return true;
    }

    private void doRealRequest() {
        String httpUrl = "http://tool.oschina.net/apidocs/apidoc?api=jdk-zh";
        SFCacheRequest cacheRequest = new SFCacheRequest(httpUrl, MethodType.GET) {
            @Override
            public Class getClassType() {
                return Student.class;
            }
        };
        cacheRequest.setCacheType(CacheType.FILE);
        cacheRequest.setHttpType(HttpType.LOCAL_THEN_NETWORK);
        SFHttpGsonCacheHandler sfHttpGsonHandler = new SFHttpGsonCacheHandler(cacheRequest, new SFHttpStringCallback<Student>() {

            private String url[] = {
                    "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
                    "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
                    "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

            @Override
            public void onSuccess(SFRequest request, Student g) {
                List<Student> students = new ArrayList<Student>();
                for (int i = 0; i < 10; i++) {
                    students.add(new Student("students" + i, 100 * i));
                }
                finishRefreshOrLoading(students, false);
            }

            @Override
            public void onFailed(SFRequest request, Exception e) {
                List<Student> students = new ArrayList<Student>();
                for (int i = 0; i < 10; i++) {
                    students.add(new Student("students" + i, 100 * i));

                }
                finishRefreshOrLoading(students, false);
            }
        });
        sfHttpGsonHandler.start();
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    private BannerFragment mBannerFt;

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
