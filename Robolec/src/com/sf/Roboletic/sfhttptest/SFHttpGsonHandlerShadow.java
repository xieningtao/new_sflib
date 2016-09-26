package com.sf.Roboletic.sfhttptest;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.Roboletic.customshadow.LoginUtil;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by NetEase on 2016/8/17 0017.
 */
@Implements(SFHttpGsonHandler.class)
public class SFHttpGsonHandlerShadow {

    public void __constructor(SFRequest request, SFHttpStringCallback httpStringCallback) {

    }

    @Implementation
    public void start() {
        System.out.println("SFHttpGsonHandlerShadow start called");
    }
}
