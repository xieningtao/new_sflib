package com.sf.circlelib.abscircle;

import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
public interface ISFContent {

    String getPhotoUrl();

    String getTitle();

    String getContent();

    List<ISFImage> getImages();

    List<ISFComment> getComments();
}
