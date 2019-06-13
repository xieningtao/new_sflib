package com.basesmartframe.baseui;

import android.app.Fragment;
import android.os.Bundle;
import androidx.legacy.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.basesmartframe.R;

/**
 * Created by NetEase on 2016/6/27 0027.
 */
abstract public class BaseTabActivity extends BaseActivity implements TabHost.OnTabChangeListener{
    private TabWidget mTabWidget;
    private FragmentTabHost mTabHost;
    private BaseTabSpecAdapter mBaseTabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
    }

    private void initView() {
        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getFragmentManager(),R.id.real_content_container);

        mTabHost.setOnTabChangedListener(this);
    }

    public void setTabAdapter(BaseTabSpecAdapter adapter){
        mBaseTabAdapter=adapter;
        if(mBaseTabAdapter!=null){
            int count=mBaseTabAdapter.getCount();
            LayoutInflater layoutInflater=LayoutInflater.from(this);
            for(int i=0;i<count;i++){
                mTabHost.addTab(mBaseTabAdapter.getTabSpec(mTabHost,i,layoutInflater),mBaseTabAdapter.getFragmentClass(i),mBaseTabAdapter.getBundle(i));
            }
        }
    }
    protected FragmentTabHost getTabHost(){
        return mTabHost;
    }

    protected TabWidget getTabWidget(){
        return mTabWidget;
    }

   public   interface BaseTabSpecAdapter {
        int getCount();
        TabHost.TabSpec getTabSpec(TabHost tabHost,int index,LayoutInflater inflater);
        Class<? extends Fragment> getFragmentClass(int index);
        Bundle getBundle(int index);
    }

}
