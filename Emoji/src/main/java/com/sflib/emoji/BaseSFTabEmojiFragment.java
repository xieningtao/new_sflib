package com.sflib.emoji;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.basesmartframe.baseui.BaseFragment;
import com.sflib.CustomView.tab.SFFragmentTabHost;

/**
 * Created by NetEase on 2016/7/7 0007.
 */
abstract public class BaseSFTabEmojiFragment extends BaseFragment implements TabHost.OnTabChangeListener{

    private TabWidget mTabWidget;
    private SFFragmentTabHost mTabHost;
    private BaseTabSpecAdapter mBaseTabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emoji,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View rootView) {
        mTabWidget = (TabWidget)rootView.findViewById(android.R.id.tabs);
        mTabHost = (SFFragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getFragmentManager(), R.id.real_content_container);

        mTabHost.setOnTabChangedListener(this);
    }

    public void setTabAdapter(BaseTabSpecAdapter adapter) {
        mBaseTabAdapter = adapter;
        if (mBaseTabAdapter != null) {
            int count = mBaseTabAdapter.getCount();
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            for (int i = 0; i < count; i++) {
                mTabHost.addTab(mBaseTabAdapter.getTabSpec(mTabHost, i, layoutInflater), mBaseTabAdapter.getFragmentClass(i), mBaseTabAdapter.getBundle(i));
            }
        }
    }

    protected SFFragmentTabHost getTabHost() {
        return mTabHost;
    }

    protected TabWidget getTabWidget() {
        return mTabWidget;
    }

    public interface BaseTabSpecAdapter {
        int getCount();

        TabHost.TabSpec getTabSpec(TabHost tabHost, int index, LayoutInflater inflater);

        Class<? extends Fragment> getFragmentClass(int index);

        Bundle getBundle(int index);
    }
}
