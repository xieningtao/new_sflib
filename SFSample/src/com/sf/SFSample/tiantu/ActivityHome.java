package com.sf.SFSample.tiantu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.baseui.BaseSFTabActivity;
import com.sf.SFSample.R;
import com.sf.SFSample.ui.GesturePullListFragment;
import com.sf.SFSample.ui.MyCircleFragment;
import com.sf.SFSample.ui.PullListFragment;

/**
 * Created by NetEase on 2016/9/18 0018.
 */
public class ActivityHome extends BaseSFTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTabWidget().setDividerDrawable(null);
        setTabAdapter(new FragmentTabAdpaper());
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    public class FragmentTabAdpaper implements BaseSFTabActivity.BaseTabSpecAdapter{

        @Override
        public int getCount() {
            return 3;
        }

        //refactor it later
        @Override
        public TabHost.TabSpec getTabSpec(final TabHost tabHost, int index, LayoutInflater layoutInflater) {
            switch (index){
                case 0:
                    View tabView0=layoutInflater.inflate(R.layout.tab_item,null);
                    TabHost.TabSpec tabSpec0=tabHost.newTabSpec("index"+index).setIndicator(tabView0);
                    return tabSpec0;
                case 1:
                    View tabView1=layoutInflater.inflate(R.layout.tab_item,null);
                    TabHost.TabSpec tabSpec1=tabHost.newTabSpec("index"+index).setIndicator(tabView1);
                    return tabSpec1;
                case 2:
                    View tabView2=layoutInflater.inflate(R.layout.tab_item,null);
                    TabHost.TabSpec tabSpec2=tabHost.newTabSpec("index"+index).setIndicator(tabView2);
                    return tabSpec2;
            }
            return null;
        }

        @Override
        public Class<? extends Fragment> getFragmentClass(int index) {
            switch (index){
                case 0:
                    return FragmentTianTuList.class;
                case 1:
                    return PullListFragment.class;
                case 2:
                    return MyCircleFragment.class;
            }
            return null;
        }

        @Override
        public Bundle getBundle(int index) {
            return null;
        }
    }
}
