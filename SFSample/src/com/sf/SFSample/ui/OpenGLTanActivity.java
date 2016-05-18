package com.sf.SFSample.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sflib.openGL.tan.Word2BitmapUtil;
import com.sflib.openGL.tan.XTanSufaceView;

/**
 * Created by xieningtao on 16-3-27.
 */
public class OpenGLTanActivity extends BaseActivity {

    private XTanSufaceView mXTanSufaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan);

        ImageView imageView = (ImageView) findViewById(R.id.tan_iv);
        Bitmap bitmap = Word2BitmapUtil.word2Bitmap("hello open gl",0xffff0000);
        imageView.setImageBitmap(bitmap);

        mXTanSufaceView = (XTanSufaceView) findViewById(R.id.xtan_sv);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mXTanSufaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mXTanSufaceView.onResume();
    }
}
