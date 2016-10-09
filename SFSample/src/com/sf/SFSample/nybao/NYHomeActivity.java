package com.sf.SFSample.nybao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseui.BaseSFTabActivity;
import com.sf.SFSample.R;
import com.sf.SFSample.tiantu.FragmentTianTuList;
import com.sf.SFSample.ui.MyCircleFragment;
import com.sf.SFSample.ui.PullListFragment;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYHomeActivity extends BaseSFTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTabWidget().setDividerDrawable(null);
        setTabAdapter(new FragmentTabAdpaper());
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    public class FragmentTabAdpaper implements BaseSFTabActivity.BaseTabSpecAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        //refactor it later
        @Override
        public TabHost.TabSpec getTabSpec(final TabHost tabHost, int index, LayoutInflater layoutInflater) {
            switch (index) {
                case 0:
                    View tabView0 = layoutInflater.inflate(R.layout.tab_item, null);
                    TabHost.TabSpec tabSpec0 = tabHost.newTabSpec("index" + index).setIndicator(tabView0);
                    return tabSpec0;
                case 1:
                    View tabView1 = layoutInflater.inflate(R.layout.tab_item, null);
                    TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("index" + index).setIndicator(tabView1);
                    return tabSpec1;
                case 2:
                    View tabView2 = layoutInflater.inflate(R.layout.tab_item, null);
                    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("index" + index).setIndicator(tabView2);
                    return tabSpec2;
                case 3:
                    View tabView3 = layoutInflater.inflate(R.layout.tab_item, null);
                    TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("index" + index).setIndicator(tabView3);
                    return tabSpec3;
            }
            return null;
        }

        @Override
        public Class<? extends Fragment> getFragmentClass(int index) {
            switch (index) {
                case 0:
                    return NYFragmentNews.class;
                case 1:
                    return NYFragmentVideo.class;
                case 2:
                    return NYFragmentTopic.class;
                case 3:
                    return NYFragmentProfile.class;
            }
            return null;
        }

        @Override
        public Bundle getBundle(int index) {
            return null;
        }
    }
}
