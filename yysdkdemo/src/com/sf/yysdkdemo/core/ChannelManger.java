package com.sf.yysdkdemo.core;

import com.yyproto.outlet.IMediaVideo;
import com.yyproto.outlet.IProtoMgr;
import com.yyproto.outlet.ISession;

/**
 * Created by xieningtao on 15-11-6.
 */
public class ChannelManger {

    public static ISession session() {
        return IProtoMgr.instance().getSess();
    }

    public static IMediaVideo mediaVideo() {
        return IProtoMgr.instance().getMedia();
    }
}
