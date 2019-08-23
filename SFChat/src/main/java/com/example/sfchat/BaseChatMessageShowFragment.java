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
import com.basesmartframe.baseui.BaseFragment;
import com.example.sfchat.item.BaseChatAdapter;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.media.MediaPlayManager;
import com.sf.utils.ThreadHelp;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sflib.CustomView.newhttpview.HttpViewManager;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by NetEase on 2016/11/28 0028.
 */

abstract public class BaseChatMessageShowFragment extends BaseFragment implements
        AdapterView.OnItemClickListener {

    protected RecyclerView mPullToRefreshListView;
    private BaseChatAdapter mAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_pull_refresh_fragment, null);
    }

    public RecyclerView getPullToRefreshListView() {
        return mPullToRefreshListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        doRefresh();
    }

    private void initView(View view, Bundle savedInstanceState) {
        mAdapter = new BaseChatAdapter(getActivity());
//        mPullToRefreshListView.setAdapter(mAdapter);
    }

    public void doRefresh() {
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            onRefresh();
        } else {
            onRefreshNoNetwork();
        }
    }



    protected void finishRefreshOrLoading(List<SFMsg> data,
                                          boolean isIncrement) {
        // handle data
        if (data != null && data.size() > 0) {
            this.mAdapter.getMsgList().addAll(data);
        }
        mAdapter.notifyDataSetChanged();

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
