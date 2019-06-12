package com.basesmartframe.pickphoto;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.sf.loglib.L;


/**
 * Created by NetEase on 2016/10/13 0013.
 */
public class XTranslateTransform implements ViewPager.PageTransformer {
    private final String TAG = getClass().getName();
    private static final float MIN_SCALE = 0.75f;

    private float mScale = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        L.info(TAG, "transformPage view id:　" + view + " position: " + position);
        if (position <= -1) { // [-Infinity,-1) lef
        } else if (position <= 0) { // [-1,0] middle
            // Counteract the default slide transition
            L.info(TAG, "middle position: " + position);
            view.scrollTo((int) (pageWidth * position * mScale), 0);
        } else if (position <= 1) { // (0,1] right
            view.scrollTo((int) (pageWidth * position * mScale),0);
        } else { // (1,+Infinity]

        }
    }
}
