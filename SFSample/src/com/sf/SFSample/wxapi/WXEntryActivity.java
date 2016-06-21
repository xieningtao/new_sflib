/**
 * @(#)WXEntryActivity.java, 2014-5-30. 
 * 
 * Copyright 2014 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sf.SFSample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.share.ShareConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xltu
 */
public class WXEntryActivity extends WXCallbackActivity  {

}
