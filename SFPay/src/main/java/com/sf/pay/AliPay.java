package com.sf.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.sf.loglib.L;
import com.sf.pay.ali.PayResult;
import com.sf.utils.ThreadHelp;

/**
 * Created by NetEase on 2016/6/15 0015.
 */
public class AliPay extends BasePay {
    //注意这个字符串的格式
    private final String mPayInfo;

    public AliPay(String payInfo) {
        this.mPayInfo = payInfo;
    }

    @Override
    public void pay(final Activity activity) {
        ThreadHelp.runInSingleBackThread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                final String result = alipay.pay(mPayInfo);
                ThreadHelp.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        PayResult payResult = new PayResult(result);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        L.debug(TAG,"method->handleMessage,alipay resultInfo: "+resultInfo+" resultStatus: "+resultStatus);
                        if (TextUtils.equals(resultStatus, "9000")){
                            if(mPayCallBack!=null){
                                mPayCallBack.onSuccess();
                            }
                        }else {
                            if(mPayCallBack!=null){
                                mPayCallBack.onFail();
                            }
                        }
                    }
                });
            }
        },0);
    }
}
