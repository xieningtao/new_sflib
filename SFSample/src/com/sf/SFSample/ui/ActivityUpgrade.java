package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.updateutil.UpdateService;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/7/26 0026.
 */
public class ActivityUpgrade extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_layout);
        findViewById(R.id.download_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateService updateService=new UpdateService(ActivityUpgrade.this);
                updateService.start("http://3g.163.com/links/4002?vs=38");
            }
        });
    }
}
