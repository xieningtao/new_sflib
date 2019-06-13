package com.sf.SFSample.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.palette.graphics.Palette;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2017/5/19 0019.
 */

public class PaletteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette_activity);
        final TextView txtTv = (TextView) findViewById(R.id.txt_tv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.round_bit_map_test);
        // Asynchronous
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                if (p != null) {
                    Palette.Swatch swatch = p.getVibrantSwatch();
                    if (swatch != null) {
                        txtTv.setBackgroundColor(swatch.getRgb());
                        txtTv.setTextColor(swatch.getTitleTextColor());
                    }
                }
            }
        });
    }
}
