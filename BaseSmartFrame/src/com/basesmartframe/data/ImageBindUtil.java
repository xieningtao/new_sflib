package com.basesmartframe.data;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.basesmartframe.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sf.utils.baseutil.FP;

/**
 * Created by Carlos on 2015/11/9.
 */
public class ImageBindUtil {

    private static LruCache<String, String> sImageUrlCache = new LruCache<String, String>(200);
    private static ImageLoader sImageLoader = ImageLoader.getInstance();

    /**
     * 使用自定义tag作为缓存key来显示图片，如果对应的tag之前显示过图片，则先显示缓存，再显示给定url的图片
     * 逻辑如下：
     * <pre>
     *
     *    对于给定的tag，维护一份基于{@link LruCache}对应的缓存，以tag为key，成功显示的url为value。
     *    当加载图片时，遍历tag数组，先从缓存中找tag对应的url，如果有，则说明该tag之前显示过图片，调用{@link ImageLoader}先显示该缓存的url对应的图片，然后再加载新的url图片
     *    由于缓存url对应的图片也被{@link ImageLoader}缓存过，所以缓存会很快加载出来。
     *
     *    如果对应的tag找不到，则遍历寻找数组中下一个tag，如果所有的tag都找不到对应的url，则直接加载新url的图片
     *
     *    当新的图片下载成功之后，更新所有的tag对应的url缓存
     * </pre>
     *
     * @param tags      标记数组，用于标记对应的url并做缓存
     * @param newUrl    需要加载的新的图片地址，如果加载成功，会用此地址更新tag数组里面元素对应的缓存
     * @param imageView 显示图片的view
     * @param options   图片显示选项
     * @param listener  回调
     */
    public static void displayImageWithTags(String[] tags, String newUrl, final ImageView imageView, final DisplayImageOptions options, final ImageLoadingListener listener) {
        if (newUrl == null) {
            newUrl = "";
        }
        if (!TextUtils.isEmpty(newUrl)) {
            if (FP.eq(newUrl, imageView.getTag(R.id.url))) {
                return;
            }
            imageView.setTag(R.id.url, newUrl);

            String cachedUrl = getCacheUrl(tags);

            if (!TextUtils.isEmpty(cachedUrl) && !cachedUrl.equals(newUrl)) {
                displayImageWithCacheUrl(tags, newUrl, cachedUrl, imageView, options, listener);
                return;
            }
        }

        displayImage(tags, newUrl, imageView, options, listener, true);
    }

    private static String getCacheUrl(String[] tags) {
        String cachedUrl = "";
        if (!FP.empty(tags)) {
            for (String tag : tags) {
                cachedUrl = getCacheUrl(tag);
                if (!TextUtils.isEmpty(cachedUrl)) {
                    break;
                }
            }
        }
        return cachedUrl;
    }

    /**
     * 等同于使用{@link #displayImageWithTags(String[], String, ImageView, DisplayImageOptions, ImageLoadingListener)}
     */
    public static void displayImageWithTag(final String tag, final String uri, final ImageView imageView, final DisplayImageOptions options, final ImageLoadingListener listener) {
        displayImageWithTags(new String[]{tag}, uri, imageView, options, listener);
    }

    private static void displayImageWithCacheUrl(final String tags[], final String newUrl, String cachedUrl, final ImageView imageView, final DisplayImageOptions options, final ImageLoadingListener listener) {

        ImageLoadingListener cacheImageLoadingListener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                displayImage(tags, newUrl, imageView, options, listener, false);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                displayImage(tags, newUrl, imageView, options, listener, false);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };

        sImageLoader.displayImage(cachedUrl, imageView, options, cacheImageLoadingListener);
    }


    private static void displayImage(final String tags[], String url, final ImageView imageView,
                                     DisplayImageOptions options, final ImageLoadingListener listener, final boolean needDisplayDefault) {
        ImageLoadingListener localListener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (listener != null) {
                    listener.onLoadingStarted(imageUri, view);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (listener != null) {
                    listener.onLoadingFailed(imageUri, view, failReason);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (!FP.empty(tags)) {
                    for (String tag : tags) {
                        sImageUrlCache.put(tag, imageUri);
                    }
                }

                if (imageUri.equals(imageView.getTag(R.id.url)) && loadedImage != null && !loadedImage.isRecycled() && !needDisplayDefault) {
                    imageView.setImageBitmap(loadedImage);
                }
                if (listener != null) {
                    listener.onLoadingComplete(imageUri, view, loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (listener != null) {
                    listener.onLoadingCancelled(imageUri, view);
                }
            }
        };
        if (needDisplayDefault) {
            sImageLoader.displayImage(url, imageView, options, localListener);
        } else {
            sImageLoader.loadImage(url, options, localListener);
        }
    }

    private static String getCacheUrl(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return sImageUrlCache.get(tag);
        }
        return "";
    }
}
