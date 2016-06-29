package com.sf.SFSample.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.basesmartframe.baseui.BaseTabActivity;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/6/28 0028.
 */
public class FragmentTabActivity extends BaseTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTabAdapter(new FragmentTabAdpaper());
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    public class FragmentTabAdpaper implements BaseTabActivity.BaseTabSpecAdapter{

        @Override
        public int getCount() {
            return 4;
        }

        //refactor it later
        @Override
        public TabHost.TabSpec getTabSpec(final TabHost tabHost,int index, LayoutInflater layoutInflater) {
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
                case 3:
                    View tabView3=layoutInflater.inflate(R.layout.tab_item,null);
                    TabHost.TabSpec tabSpec3=tabHost.newTabSpec("index"+index).setIndicator(tabView3);
                    return tabSpec3;
            }
            return null;
        }

        @Override
        public Class<? extends Fragment> getFragmentClass(int index) {
            switch (index){
                case 0:
                    return GesturePullListFragment.class;
                case 1:
                    return PullListFragment.class;
                case 2:
                    return MyCircleFragment.class;
                case 3:
                    return GesturePullListFragment.class;
            }
            return null;
        }

        @Override
        public Bundle getBundle(int index) {
            return null;
        }
    }
}
