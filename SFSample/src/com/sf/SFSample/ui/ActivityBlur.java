package com.sf.SFSample.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.config.BlurBitmapProcess;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/12/30 0030.
 */

public class ActivityBlur extends BaseActivity {
    private ImageView mBlurIv;

    private DisplayImageOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_activity);
        mBlurIv = (ImageView) findViewById(R.id.blur_iv);
        init();
        ImageLoader.getInstance().displayImage("http://img1.imgtn.bdimg.com/it/u=3686761795,438589224&fm=23&gp=0.jpg", mBlurIv,mOptions);
    }

    private void init() {
        mOptions = new DisplayImageOptions.Builder().preProcessor(new BlurBitmapProcess(this, 2)).build();
    }
}
