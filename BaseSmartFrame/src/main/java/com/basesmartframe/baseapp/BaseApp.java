package com.basesmartframe.baseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.multidex.MultiDexApplication;

import com.basesmartframe.baseevent.GlobalEvent;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sf.utils.ThreadHelp;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sf.utils.baseutil.SFToast;
import com.sflib.umenglib.share.ShareConstant;
import com.umeng.socialize.PlatformConfig;


public class BaseApp extends MultiDexApplication {

    public static Context gContext;
    private final String mCacheFileName = "sf_index_cache.txt";
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
                    //TODO 以后改成EventBus
//                    if (info != null && info.isAvailable()) {
//                        String name = info.getTypeName();
//                        SFBus.send(SFMsgId.NetworkMessage.NETWORK_AVAILABLE, new GlobalEvent.NetworkEvent(true, name));
//                    } else {
//                        SFBus.send(SFMsgId.NetworkMessage.NETWORK_AVAILABLE, new GlobalEvent.NetworkEvent(false, ""));
//                    }
                }
            }
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        initGlobal();
        init();
    }

    private void init() {
        ThreadHelp.initThread(this);
        registerNetworkReceiver();
        initImageLoader(this);
//        CacheIndexManager.getInstance().init(this, mCacheFileName);
        NetWorkManagerUtil.init(this);
    }

    private void initGlobal() {
        gContext = this;
    }

    public void initImageLoader(Context context) {

        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(SFFileHelp.getImageLoadDiskCacheSize()); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(options);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, filter);
    }
}
