package com.basesmartframe.baseui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.basesmartframe.R;
import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseevent.GlobalEvent;
import com.basesmartframe.basepull.PullType;
import com.basesmartframe.baseutil.NetWorkManagerUtil;
import com.basesmartframe.baseview.newhttpview.HttpViewManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePullListFragment<T> extends BaseFragment implements
        OnItemClickListener {

    protected PullToRefreshListView mPullToRefreshListView;
    private BaseAdapter mAdapter;

    private FrameLayout mHttpContainer;
    private HttpViewManager mHttpViewManager;

    private PullType mPullType;

    private final List<T> data = new ArrayList<T>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pull_list_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        initLitener();
    }

    private void initView(View view, Bundle savedInstanceState) {
        mHttpContainer = (FrameLayout) view.findViewById(R.id.http_container);
        mHttpViewManager = HttpViewManager.createManagerByDefault(getActivity(), mHttpContainer);
        data.clear();
        mPullToRefreshListView = (PullToRefreshListView) view
                .findViewById(R.id.pull_refresh_lv);
        mPullToRefreshListView.setOnItemClickListener(this);
        mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
        BaseAdapter adapter = new PullListAdapter(this);
        mAdapter = WrapAdapterFactory(savedInstanceState, adapter,
                mPullToRefreshListView);
        onWrappAdapterCreated(mAdapter, mPullToRefreshListView);
        mPullToRefreshListView.setAdapter(mAdapter);
    }

    public void addHeaderView(View view) {
        mPullToRefreshListView.getRefreshableView().addHeaderView(view);
    }

    public void addFootView(View view) {
        mPullToRefreshListView.getRefreshableView().addFooterView(view);
    }

    protected int getHeadViewCount() {
        return mPullToRefreshListView.getRefreshableView()
                .getHeaderViewsCount();
    }

    private boolean isListViewHasData() {
        if (mPullToRefreshListView.getRefreshableView() == null) return false;
        ListAdapter adapter = mPullToRefreshListView.getRefreshableView().getAdapter();
        if (adapter == null) return false;
        int count = adapter.getCount();
        return count > 0 ? true : false;
    }

    private void initLitener() {
        mPullToRefreshListView
                .setOnRefreshListener(new OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        doRefresh();
                    }
                });

        mPullToRefreshListView
                .setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
                    @Override
                    public void onLastItemVisible() {
                        if (mPullToRefreshListView.getMode() == Mode.PULL_FROM_START) {
                            return;
                        }
                        doLoadMore();
                    }
                });

    }

    public void doLoadMore() {
        showHttpLoadingView();
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            onLoadMore();
            mPullType = PullType.LOADMORE;
        } else {
            simpleFinishRefreshOrLoading();
        }
    }

    private void showHttpLoadingView() {
        final boolean hasData = isListViewHasData();

        mHttpViewManager.showHttpLoadingView(hasData);

    }

    private void showHttpResultView() {
        final boolean hasData = isListViewHasData();
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            mHttpViewManager.showHttpViewNOData(hasData);
        } else {
            mHttpViewManager.showHttpViewNoNetwork(hasData);
        }

    }

    public void doRefresh() {
        showHttpLoadingView();
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            onRefresh();
            mPullType = PullType.REFRESH;
        } else {
            simpleFinishRefreshOrLoading();
        }
    }

    protected void simpleFinishRefreshOrLoading() {
        mPullToRefreshListView.onRefreshComplete();
    }

    protected void finishRefreshOrLoading(List<T> data,
                                          boolean isIncrement) {
        // handle data
        if (PullType.REFRESH == mPullType) {
            this.data.clear();
        }
        if (data != null && data.size() > 0) {
            this.data.addAll(data);
        }
        if (isIncrement) {
            mPullToRefreshListView.setMode(Mode.BOTH);
        } else {
            mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
        }
        mAdapter.notifyDataSetChanged();
        showHttpResultView();
        simpleFinishRefreshOrLoading();
    }

    public void onEvent(GlobalEvent.NetworkEvent event) {
        if (event.hasNetwork) {
            if (!isListViewHasData()) {
                doRefresh();
            }
        }

    }

    protected BaseAdapterHelper getAdaterHelp(View convertView, int position) {
        return BaseAdapterHelper.get(convertView, position);
    }

    protected int getViewType(int position) {
        return 0;
    }

    protected BaseAdapter WrapAdapterFactory(Bundle savedInstanceState,
                                             BaseAdapter adapter, PullToRefreshListView pullListView) {
        return adapter;
    }

    protected void onWrappAdapterCreated(BaseAdapter adapter,
                                         PullToRefreshListView pullListView) {

    }

    protected T getPullItem(int position) {
        return (T) mAdapter.getItem(position);
    }

    protected long getPullItemId(int position) {
        return mAdapter.getItemId(position);
    }

    class PullListAdapter extends BaseAdapter {

        private BasePullListFragment mBasePullListFragment;
        private final int layouts[];

        public PullListAdapter(BasePullListFragment basePullListFragment) {
            mBasePullListFragment = basePullListFragment;
            layouts = mBasePullListFragment.getLayoutIds();
        }

        @Override
        public int getCount() {
            return mBasePullListFragment.data.size();
        }

        @Override
        public Object getItem(int position) {
            return mBasePullListFragment.data.get(position);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public int getItemViewType(int position) {
            return mBasePullListFragment.getViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return layouts == null ? 0 : layouts.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                int resIndex = getItemViewType(position);
                convertView = LayoutInflater.from(mBasePullListFragment.getActivity()).inflate(
                        layouts[resIndex], null);
            }
            BaseAdapterHelper helper = mBasePullListFragment.getAdaterHelp(convertView, position);
            mBasePullListFragment.bindView(helper, position, mBasePullListFragment.data.get(position));

            return convertView;
        }
    }

    /**
     * subclass will implements this function
     *
     * @return
     */

    protected abstract boolean onRefresh();

    protected abstract boolean onLoadMore();


    protected abstract int[] getLayoutIds();

    protected abstract void bindView(BaseAdapterHelper help, int position,
                                     T bean);

}
