package com.sf.SFSample.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.loglib.L;
import com.sflib.umenglib.share.ShareConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{

	private IWXAPI api;

	public static final int WEI_PAY_SUCCESS=0;
	public static final int WEI_PAY_ERROR=-1;
	public static final int WEI_PAY_CANCEL=-2;

	public static final int WEI_PAY_RESULT=1001;

	public static final String WEI_PAY_CODE="wei_pay_code";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, ShareConstant.WEIXIN_APPID);
		api.handleIntent(getIntent(), this);
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	public void onReq(BaseReq req) {
		L.debug(TAG, "method->onReq, transaction = " + req.transaction+" openId: "+req.openId);
	}

	@Override
	public void onResp(BaseResp resp) {
		L.debug(TAG, "method->onResp, errStr = " + resp.errStr + " errorCode: " + resp.errCode);
	}
}