package com.sflib.sharelib.img;

import android.content.Context;

import com.sflib.sharelib.core.XShareInterface;
import com.sflib.umenglib.share.ShareContent;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by NetEase on 2017/5/3 0003.
 */

public class ImgSinaShare extends XShareInterface {


    private SsoHandler mSsoHandler;

    public ImgSinaShare(ShareContent shareContent, Context context) {
        super(shareContent, context);
    }

    @Override
    public void doShare() {
        doOtherShareHelp();
    }

    private void doOtherShareHelp() {

    }


}

