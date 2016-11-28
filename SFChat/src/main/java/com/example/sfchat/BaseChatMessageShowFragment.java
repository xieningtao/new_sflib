package com.example.sfchat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.basesmartframe.baseevent.GlobalEvent;
import com.basesmartframe.basepull.PullType;
import com.basesmartframe.baseui.BaseFragment;
import com.example.sfchat.item.BaseChatAdapter;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.media.MediaPlayManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sflib.CustomView.newhttpview.HttpViewManager;
import com.sflib.reflection.core.SFIntegerMessage;
import com.sflib.reflection.core.SFMsgId;
import com.sflib.reflection.core.ThreadHelp;

import java.util.List;

/**
 * Created by NetEase on 2016/11/28 0028.
 */

abstract public class BaseChatMessageShowFragment extends BaseFragment implements
        AdapterView.OnItemClickListener {

    protected PullToRefreshListView mPullToRefreshListView;
    private BaseChatAdapter mAdapter;

    private FrameLayout mHttpContainer;
    private HttpViewManager mHttpViewManager;

    private PullType mPullType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pull_list_fragment, null);
    }

    public PullToRefreshListView getPullToRefreshListView() {
        return mPullToRefreshListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        initLitener();
        doRefresh();
    }

    private void initView(View view, Bundle savedInstanceState) {
        mHttpContainer = (FrameLayout) view.findViewById(R.id.http_container);
        mHttpViewManager = HttpViewManager.createManagerByDefault(getActivity(), mHttpContainer);
        mPullToRefreshListView = (PullToRefreshListView) view
                .findViewById(R.id.pull_refresh_lv);
        mPullToRefreshListView.setOnItemClickListener(this);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mAdapter = new BaseChatAdapter(getActivity());
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

    protected int getFootViewCount() {
        return mPullToRefreshListView.getRefreshableView().getFooterViewsCount();
    }

    private boolean isListViewHasData() {
        if (mPullToRefreshListView.getRefreshableView() == null) return false;
        ListAdapter adapter = mPullToRefreshListView.getRefreshableView().getAdapter();
        if (adapter == null) return false;
        int headViewsCount = mPullToRefreshListView.getRefreshableView().getHeaderViewsCount();
        int footViewsCouont = mPullToRefreshListView.getRefreshableView().getFooterViewsCount();
        int count = adapter.getCount() - headViewsCount - footViewsCouont;
        return count > 0 ? true : false;
    }

    private void initLitener() {
        mPullToRefreshListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        doRefresh();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        doLoadMore();
                    }
                });
    }

    public void doLoadMore() {
        showHttpLoadingView();
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            mPullType = PullType.LOADMORE;
            onLoadMore();
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
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            showHttpLoadingView();
            mPullType = PullType.REFRESH;
            onRefresh();
        } else {
            onRefreshNoNetwork();
            ThreadHelp.runInMain(new Runnable() {
                @Override
                public void run() {
                    simpleFinishRefreshOrLoading();
                }
            }, 500);
        }
    }

    protected void simpleFinishRefreshOrLoading() {
        mPullToRefreshListView.onRefreshComplete();
        showHttpResultView();
    }

    protected void finishRefreshOrLoading(List<SFMsg> data,
                                          boolean isIncrement) {
        // handle data
        if (PullType.REFRESH == mPullType) {
            mAdapter.getMsgList().clear();
        }
        if (data != null && data.size() > 0) {
            this.mAdapter.getMsgList().addAll(data);
        }
        mAdapter.notifyDataSetChanged();
        showHttpResultView();
        simpleFinishRefreshOrLoading();
        if (isIncrement) {
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    @SFIntegerMessage(messageId = SFMsgId.NetworkMessage.NETWORK_AVAILABLE)
    public void onNetwokChange(GlobalEvent.NetworkEvent event) {
        if (event.hasNetwork) {
            if (!isListViewHasData()) {
                doRefresh();
            }
        }

    }

    /**
     * subclass will implements this function
     *
     * @return
     */

    protected abstract boolean onRefresh();

    protected boolean onRefreshNoNetwork() {
        return false;
    }

    protected abstract boolean onLoadMore();


    @Override
    public void onPause() {
        super.onPause();
        MediaPlayManager.getInstance().destroyPlayer();
    }
}
