package com.sf.yysdkdemo.core;

import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import com.sf.yysdkdemo.YYDemoApp;
import com.yyproto.base.ISessWatcher;
import com.yyproto.base.ProtoEvent;
import com.yyproto.outlet.IMediaVideo;
import com.yyproto.outlet.IProtoMgr;
import com.yyproto.outlet.ISession;
import com.yyproto.outlet.SessEvent;
import com.yyproto.outlet.SessRequest;

/**
 * Created by xieningtao on 15-11-6.
 */
public class ChannelModule implements ISessWatcher {
    private final String TAG = ChannelModule.class.getName();

    public ChannelModule() {
        IProtoMgr protoMgr = IProtoMgr.instance();
        String yy_token = "yym08andtv";
        byte tokenByte[] = yy_token.getBytes();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yydemologs";
        byte pathByte[] = path.getBytes();
        protoMgr.init(YYDemoApp.sContext, tokenByte, tokenByte, 1, pathByte);

    }

    /**
     * @param requestInfo,pay attention to requestInfo
     */
    public void joinChannelRequest(ChannelRequestInfo requestInfo) {
        if (requestInfo == null) {
            throw new NullPointerException("requestInfo is null");
        }
        ISession session = ChannelManger.session();
        SparseArray<byte[]> props = null;
        final String kickContext = requestInfo.kickContext;
        if (kickContext != null) {//kick Context always none,why?
            props = new SparseArray<byte[]>();
            props.put(SessRequest.SessJoinReq.JOIN_REQ_CONTEXT, kickContext.getBytes());
            props.put(SessRequest.SessJoinReq.JOIN_REQ_MULTI_KICK, "1".getBytes());
        }
        final long sid = requestInfo.sid;
        final long subSid = requestInfo.subSid;
        session.join((int) sid, (int) subSid, props, "app_join".getBytes());
        session.watch(this);//pass onEvent callback
    }

    public void leaveChannelRequest() {
        IMediaVideo mediaVideo = ChannelManger.mediaVideo();
        mediaVideo.leave();
        ISession session = ChannelManger.session();
        session.leave();

    }

    private void onJoinRes(SessEvent.ETSessJoinRes evt) {
        Log.i(TAG, "method->onJoinRes,join channel result: " + evt.mSuccess);
        if (evt.mSuccess) {

        } else {

        }
    }

    @Override
    public void onEvent(ProtoEvent evt) {
        switch (evt.eventType()) {
            case SessEvent.evtType.EVENT_JOIN_RES://enter channel
                onJoinRes((SessEvent.ETSessJoinRes) evt);
                break;
        }
    }
}
