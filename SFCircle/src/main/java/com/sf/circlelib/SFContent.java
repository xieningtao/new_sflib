package com.sf.circlelib;

import com.sf.circlelib.abscircle.ISFComment;
import com.sf.circlelib.abscircle.ISFContent;
import com.sf.circlelib.abscircle.ISFImage;

import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFContent implements ISFContent {
    @Override
    public String getPhotoUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public List<ISFImage> getImages() {
        return null;
    }

    @Override
    public List<ISFComment> getComments() {
        return null;
    }
}
