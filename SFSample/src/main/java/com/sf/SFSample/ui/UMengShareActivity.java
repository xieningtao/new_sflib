package com.sf.SFSample.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.dialoglib.DialogFactory;
import com.sflib.umenglib.share.DefaultShareAdapter;
import com.sflib.umenglib.share.ShareContent;
import com.sflib.umenglib.share.XSocialShareView;
import com.sf.SFSample.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by NetEase on 2016/6/21 0021.
 */
public class UMengShareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng_share);

        findViewById(R.id.dialog_share_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = View.inflate(UMengShareActivity.this, R.layout.view_umeng_share, null);
                XSocialShareView shareView = rootView.findViewById(R.id.share);
                ShareAction shareAction = new ShareAction(UMengShareActivity.this);
                UMWeb web = new UMWeb("https://github.com/xieningtao/new_sflib");
                web.setTitle("share title test");
                web.setDescription("share content test");
                web.setThumb(new UMImage(UMengShareActivity.this, "http://img4.imgtn.bdimg.com/it/u=2989430555,1416378759&fm=21&gp=0.jpg"));
                shareAction.withMedia(web);
                shareView.setShareAction(shareAction);
                shareView.setShareAdapter(new DefaultShareAdapter());
                final Dialog dialog = DialogFactory.getNoFloatingDimDialog(UMengShareActivity.this, rootView);
                dialog.show();
                rootView.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        findViewById(R.id.view_share_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
