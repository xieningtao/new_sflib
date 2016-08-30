package com.sf.Roboletic.sfhttptest;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.Roboletic.customshadow.LoginUtil;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
@Implements(SFHttpGsonHandler.class)
public class SFHttpGsonHandlerShadow {

    @Implementation
    protected void start(){
        System.out.println("SFHttpGsonHandlerShadow start called");
    }
}
