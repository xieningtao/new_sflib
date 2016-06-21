package com.basesmartframe.baseapp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.basesmartframe.baseevent.GlobalEvent;
import com.basesmartframe.share.ShareConstant;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;

import de.greenrobot.event.EventBus;

public class BaseApp extends Application {

    public static Context gContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initGlobal();
        init();
    }

    private void init() {
        registerNetworkReceiver();
        initImageLoader(this);
        initUMengShare();
    }

    private void initUMengShare() {
        PlatformConfig.setWeixin(ShareConstant.WEIXIN_APPID,ShareConstant.WEIXIN_SECRET_KEY);
        PlatformConfig.setQQZone(ShareConstant.QQ_APPID,ShareConstant.QQ_SECRET_KEY);
        PlatformConfig.setSinaWeibo(ShareConstant.SINA_APPID,ShareConstant.SINA_SECRET_KEY);
    }

    private void initGlobal() {
        gContext = this;
    }

    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (null != action
                        && action
                        .equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = connectivityManager
                            .getActiveNetworkInfo();
                    if (info != null && info.isAvailable()) {
                        String name = info.getTypeName();
                        EventBus.getDefault().post(
                                new GlobalEvent.NetworkEvent(true, name));
                    } else {
                        EventBus.getDefault().post(
                                new GlobalEvent.NetworkEvent(false, ""));
                    }
                }
            }
        }

    };
}
