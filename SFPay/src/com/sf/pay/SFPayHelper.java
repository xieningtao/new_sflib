package com.sf.pay;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.basesmartframe.basehttp.SFHttpClient;
import com.sf.httpclient.core.AjaxParams;

import java.util.List;

/**
 * Created by NetEase on 2016/6/15 0015.
 */
public class SFPayHelper {
    public static boolean isWeiPayInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;

    }

    //do it later
    public static void getDefaultWXPayParams() {
        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        SFHttpClient.get(url,new BaseAjaxCallBack<WXPayBean>(WXPayBean.class){

            @Override
            public void onResult(Object t, AjaxParams params) {

            }
        });
    }
}
