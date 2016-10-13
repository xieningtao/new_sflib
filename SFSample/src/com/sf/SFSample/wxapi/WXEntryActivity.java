/**
 * @(#)WXEntryActivity.java, 2014-5-30. 
 * 
 * Copyright 2014 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sf.SFSample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sf.loglib.L;
import com.sflib.umenglib.share.ShareConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author xltu
 */
//this is for umeng share
//public class WXEntryActivity extends WXCallbackActivity  {
//
//}

//this is userful for  wx lib platform
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = getClass().getName();
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ShareConstant.WEIXIN_APPID, false);
        api.registerApp(ShareConstant.WEIXIN_APPID);
        api.handleIntent(getIntent(), this);
        L.info(TAG, "onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.info(TAG, "onNewIntent,intent: " + intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        L.info(TAG, "onReq req type: " + req.getType()+" openId: "+req.openId);
    }


    @Override
    public void onResp(BaseResp resp) {
        L.info(TAG, "onResp resp type: " + resp.getType()+" openId: "+resp.openId+" errorCode: "+resp.errCode+" errorStr: "+resp.errStr);
    }
}
