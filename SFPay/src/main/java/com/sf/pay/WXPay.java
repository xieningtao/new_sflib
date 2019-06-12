package com.sf.pay;


import android.app.Activity;

import com.sf.loglib.L;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by NetEase on 2016/6/15 0015.
 */
public class WXPay extends BasePay {
    private WXPayBean wxPayBean;

    public WXPay(WXPayBean bean) {
        this.wxPayBean = bean;
    }

    private void init(){

    }
    @Override
    public void pay(Activity activity) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
        if (msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
            // 将该app注册到微信
            msgApi.registerApp(PayConstant.WX_APP_ID);
            if (wxPayBean != null) {
                PayReq request = new PayReq();
                request.appId = wxPayBean.getAppId();
                request.partnerId = wxPayBean.getPartnerId();
                request.prepayId = wxPayBean.getPrepayId();
                request.nonceStr =wxPayBean.getNonceStr();
                request.timeStamp = wxPayBean.getTimeStamp();
                request.packageValue = wxPayBean.getPackageValue();
                request.sign = wxPayBean.getSign();
                L.info(TAG, "method->weiPayHelp,weipay request params: " + wxPayBean.toString());
                msgApi.sendReq(request);
                if(mPayCallBack!=null){
                    mPayCallBack.onSuccess();
                }
            }else {
                if(mPayCallBack!=null){
                    mPayCallBack.onFail();
                }
            }
        }else {
            if(mPayCallBack!=null){
                mPayCallBack.onFail();
            }
        }
    }
}
