package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sf.SFSample.R;
import com.sflib.CustomView.listview.XPinAdapter;
import com.sflib.CustomView.listview.XPinListView;

/**
 * Created by xieningtao on 16-3-25.
 */
public class XPinListViewActivity extends Activity {
    private XPinListView mXPinListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpin_listview);

        mXPinListView = (XPinListView) findViewById(R.id.xpin_lv);
        TextView update_tv = (TextView) findViewById(R.id.update_head_tv);
        mXPinListView.setPinedView(update_tv);
        mXPinListView.setAdapter(new XPinAdapter(this));
    }
}