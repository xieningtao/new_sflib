package com.sf.circlelib;

import com.sf.circlelib.abscircle.ISFComment;
import com.sf.circlelib.abscircle.ISFContent;
import com.sf.circlelib.abscircle.ISFImage;

import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFContent implements ISFContent {

    private String content;

    private String title;

    private List<ISFImage> images;

    private List<ISFComment> comments;

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<ISFImage> images) {
        this.images = images;
    }

    public void setComments(List<ISFComment> comments) {
        this.comments = comments;
    }

    @Override
    public String getPhotoUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<ISFImage> getImages() {
        return images;
    }

    @Override
    public List<ISFComment> getComments() {
        return comments;
    }
}
