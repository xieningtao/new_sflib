package com.sflib.umenglib.share;

import android.graphics.Bitmap;

/**
 * Created by xieningtao on 15-5-6.
 */
public class ShareContent {

    public final String title;
    public final String content;
    public final String url;
    public final String image_url;
    public final Bitmap bitmap;
    private String appName;

    private ShareContent(String title, String content, String url, String image_url, Bitmap bitmap) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.image_url = image_url;
        this.bitmap = bitmap;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public static class ShareContentBuilder {

        private String title;
        private String content;
        private String url;
        private String image_url;
        private Bitmap bitmap;

        public ShareContentBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ShareContentBuilder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public ShareContentBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public ShareContentBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ShareContentBuilder setImage_url(String image_url) {
            this.image_url = image_url;
            return this;
        }

        public ShareContent build() {

            return new ShareContent(title, content, url, image_url, bitmap);
        }
    }

    @Override
    public String toString() {
        return "ShareContent{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
