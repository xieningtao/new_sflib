package com.basesmartframe.data;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.basesmartframe.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * Created by elielo on 2016/11/07.
 */
public class ViewBind {
    public static final DisplayImageOptions DEFAULT_IMAGE_OPTIONS = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_launcher)
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .cacheInMemory(true)
            .build();

    public static void resetViewSize(View targetView, int targetWidth, int targetHeight) {
        if (targetHeight <= 0 || targetWidth <= 0) {
            return;
        }
        targetView.getLayoutParams().width = targetWidth;
        targetView.getLayoutParams().height = targetHeight;
        targetView.setLayoutParams(targetView.getLayoutParams());
    }

    private static void displayImageWithSpecifiedSize(String uri, ImageView imageView, DisplayImageOptions
            options, int targetWidth, int targetHeight, ImageLoadingListener listener) {
        displayImageWithSpecifiedSize(uri, uri, imageView, options, targetWidth, targetHeight, listener);
    }

    private static void displayImageWithSpecifiedSize(String tag, String uri, ImageView imageView, DisplayImageOptions
            options, int targetWidth, int targetHeight, ImageLoadingListener listener) {
        if (TextUtils.isEmpty(uri) || !uri.equals(imageView.getTag(R.id.url) == null ? "" : imageView.getTag(R.id.url))) {
            resetViewSize(imageView, targetWidth, targetHeight);
            ImageBindUtil.displayImageWithTag(tag, uri, imageView, options, listener);
        }
    }

    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
        displayImageWithSpecifiedSize(uri, imageView, options, 0, 0, null);
    }
}
