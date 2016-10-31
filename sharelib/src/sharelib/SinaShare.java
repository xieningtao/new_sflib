package com.sflib.sharelib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netease.huatian.module.sns.ShareActivity;
import com.netease.huatian.module.sns.ShareBean;
import com.netease.huatian.module.sns.ShareFactory;
import com.netease.huatian.module.sns.ShareSNSFragment;
import com.netease.huatian.module.sns.ShareStatistic;
import com.netease.huatian.module.sns.SnsManager;
import com.netease.huatian.module.sns.SsoHandler;
import com.netease.huatian.module.sns.share.ShareContent;
import com.netease.huatian.sfmsg.ThreadHelp;
import com.netease.util.fragment.SingleFragmentHelper;
import com.nostra13.universalimageloader.utils.L;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

import java.io.IOException;

/**
 * Created by NetEase on 2016/10/12 0012.
 */
public class SinaShare extends XShareInterface {


    private SsoHandler mSsoHandler;

    public SinaShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
    }

    @Override
    public void doShare() {
        doShareHelper();
    }

    private void doOtherShareHelp() {
        final WeiboParameters bundle = new WeiboParameters();
        bundle.add("access_token", SnsManager.getShareAccessToken(getContext()));
        ShareContent shareContent = getShareContent();
        bundle.add("pic", shareContent.image_url);
        bundle.add("status", shareContent.content);
        bundle.add("Content-type", "multipart/form-data");
        final String urll = Weibo.SERVER + "statuses/upload.json";
        final AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(Weibo.getInstance());
        ThreadHelp.runInSingleBackThread(new Runnable() {
            @Override
            public void run() {
                weiboRunner.request(getContext(), urll, bundle, Utility.HTTPMETHOD_POST, new AsyncWeiboRunner.RequestListener() {

                    @Override
                    public void onComplete(String result) {
                        L.i(TAG, "weiboRunner.onComplete,result: " + result);
                    }

                    @Override
                    public void onError(WeiboException exception) {
                        L.i(TAG, "weiboRunner.onError,exception: " + exception);
                    }

                    @Override
                    public void onIOException(IOException exception) {
                        L.i(TAG, "weiboRunner.onIOException,exception: " + exception);
                    }
                });
            }
        }, 0);
    }

    private void doShareHelper() {
        ShareContent shareContent = getShareContent();
        ShareSNSFragment.setBitmap(shareContent.bitmap);
        ShareBean shareBean = new ShareBean();
        shareBean.bitmap = null;
        shareBean.comment = shareContent.content;
        shareBean.shareType = ShareFactory.SHARE_TO_SINA_WEIBO;
        shareBean.title = shareContent.title;
        shareBean.url = shareContent.url;
        shareBean.which=ShareStatistic.CHANNEL_SINA_INDEX;
        Bundle bundle = new Bundle();
        bundle.putParcelable(ShareSNSFragment.SERIALIZABLE_NAME, shareBean);
        Activity activity = (Activity) getContext();
        Intent intent = SingleFragmentHelper.getStartIntent(activity, ShareSNSFragment.class.getName(),
                "ShareSNSFragment", bundle, null, ShareActivity.class);
        activity.startActivity(intent);
    }
}
