package com.sf.SFSample.nybao;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseSFTabActivity;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYHomeActivity extends BaseSFTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTabWidget().setDividerDrawable(null);
        getTabWidget().setBackgroundResource(R.drawable.ny_home_bottom_layer);
        setTabAdapter(new FragmentTabAdpaper());
        initActionBar();
        updateActionBar();
    }

    private void initActionBar() {
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.ny_home_title);
    }

    private void updateActionBar() {
        View rootView = getActionBar().getCustomView();
        rootView.setBackgroundColor(getResources().getColor(R.color.text_red));
        ImageView logoIv = (ImageView) rootView.findViewById(R.id.ny_logo);
        logoIv.setImageResource(R.drawable.app_icon);
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
                    ImageView tabIv0 = (ImageView) tabView0.findViewById(R.id.tab_iv);
                    tabIv0.setImageResource(R.drawable.ny_news_selector);
                    TextView tabTv0 = (TextView) tabView0.findViewById(R.id.tab_tv);
                    tabTv0.setText(R.string.news);
                    TabHost.TabSpec tabSpec0 = tabHost.newTabSpec("index" + index).setIndicator(tabView0);
                    return tabSpec0;
                case 1:
                    View tabView1 = layoutInflater.inflate(R.layout.tab_item, null);
                    ImageView tabIv1 = (ImageView) tabView1.findViewById(R.id.tab_iv);
                    tabIv1.setImageResource(R.drawable.ny_video_selector);
                    TextView tabTv1 = (TextView) tabView1.findViewById(R.id.tab_tv);
                    tabTv1.setText(R.string.video);
                    TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("index" + index).setIndicator(tabView1);
                    return tabSpec1;
                case 2:
                    View tabView2 = layoutInflater.inflate(R.layout.tab_item, null);
                    ImageView tabIv2 = (ImageView) tabView2.findViewById(R.id.tab_iv);
                    tabIv2.setImageResource(R.drawable.ny_topic_selector);
                    TextView tabTv2 = (TextView) tabView2.findViewById(R.id.tab_tv);
                    tabTv2.setText(R.string.beauty);
                    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("index" + index).setIndicator(tabView2);
                    return tabSpec2;
                case 3:
                    View tabView3 = layoutInflater.inflate(R.layout.tab_item, null);
                    ImageView tabIv3 = (ImageView) tabView3.findViewById(R.id.tab_iv);
                    tabIv3.setImageResource(R.drawable.ny_profile_selector);
                    TextView tabTv3 = (TextView) tabView3.findViewById(R.id.tab_tv);
                    tabTv3.setText(R.string.profile);
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
                    return NYFragmentPic.class;
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
