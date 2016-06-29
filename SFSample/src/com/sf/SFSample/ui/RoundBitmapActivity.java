package com.sf.SFSample.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.utils.baseutil.UnitHelp;
import com.sf.SFSample.R;
import com.sflib.CustomView.canvas.RoundBitmap;

/**
 * Created by xieningtao on 15-12-18.
 */
public class RoundBitmapActivity extends BaseActivity {

    private ImageView center_iv;
    private ImageView fitxy_iv;
    private ImageView fitstart_iv;
    private ImageView fitend_iv;
    private ImageView fitcenter_iv;
    private ImageView centerinside_iv;
    private ImageView centercrop_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_bitmap);
        init();
    }

    private void init() {
        center_iv = (ImageView) findViewById(R.id.center_iv);
        fitxy_iv = (ImageView) findViewById(R.id.fitxy_iv);
        fitstart_iv = (ImageView) findViewById(R.id.fitstart_iv);
        fitend_iv = (ImageView) findViewById(R.id.fitend_iv);
        centerinside_iv = (ImageView) findViewById(R.id.centerinside_iv);
        centercrop_iv = (ImageView) findViewById(R.id.centercrop_iv);
        fitcenter_iv = (ImageView) findViewById(R.id.fitcenter_iv);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.round_bit_map_test);
        int radius = UnitHelp.dip2px(this, 20);
//        Bitmap roundBitmap = RoundBitmap.createRoundBitmap(bitmap, radius, radius);
//        Drawable drawable = new BitmapDrawable(getResources(), roundBitmap);
//        center_iv.setImageDrawable(drawable);
//
//        fitxy_iv.setImageDrawable(drawable);
//        fitstart_iv.setImageDrawable(drawable);
//        fitend_iv.setImageDrawable(drawable);
//        centerinside_iv.setImageDrawable(drawable);
//        centercrop_iv.setImageDrawable(drawable);
//        fitcenter_iv.setImageDrawable(drawable);


        Bitmap c_roundBitmap = RoundBitmap.createRoundBitmap(bitmap, radius, radius, 0, 0);
        Drawable c_drawable = new BitmapDrawable(getResources(), c_roundBitmap);
        center_iv.setImageDrawable(c_drawable);

        fitxy_iv.setImageDrawable(c_drawable);
        fitstart_iv.setImageDrawable(c_drawable);
        fitend_iv.setImageDrawable(c_drawable);
        centerinside_iv.setImageDrawable(c_drawable);
        centercrop_iv.setImageDrawable(c_drawable);
        fitcenter_iv.setImageDrawable(c_drawable);


    }

}
