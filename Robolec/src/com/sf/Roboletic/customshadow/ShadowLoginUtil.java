package com.sf.Roboletic.customshadow;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
@Implements(LoginUtil.class)
public class ShadowLoginUtil {
    private  boolean isOnline = false;

    @Implementation
    public  boolean isOnline() {
        return isOnline;
    }

    public  void setOnline(boolean online) {
       this.isOnline = online;
        System.out.println("online: "+isOnline);
    }
}
