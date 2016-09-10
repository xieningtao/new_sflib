package com.sf.circlelib;

import com.sf.circlelib.abscircle.ISFImage;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFImage implements ISFImage {
    private String url;


    @Override
    public String getImageUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
