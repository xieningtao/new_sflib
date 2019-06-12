package com.sf.SFSample;

import com.basesmartframe.baseapp.BaseApp;
import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;
import com.sf.baidulib.SFBaiduLocationManager;
import com.sf.loglib.L;

import org.greenrobot.greendao.database.Database;

public class SFApp extends BaseApp {
    public static final String APP_ID = "57f9edc887d4a7e337b8c231";
    public static final String APP_ID_KEY = "MmNsUDJONjlNc2xwNzEtbVY3RE5KUQ";
    public static SFApp instance;
    private DaoSession mDaoSession;
//    public static final String APP_ID_KEY = "WHB0a1QzUXZwNDZJMXFYYjNpbnJxZw";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        startModule();
        doTest();
    }

    public static SFApp getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void initDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    private void startModule() {
    }

    private void init() {
        SFBaiduLocationManager.getInstance().init(getApplicationContext());
        SFBaiduLocationManager.getInstance().requestLocate();
        MaxLeap.initialize(this, APP_ID, APP_ID_KEY, MaxLeap.REGION_CN);
        initDao();
    }

    private void doTest() {
        MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            L.debug("MaxLeap", "SDK 成功连接到你的云端应用！");
                        } else {
                            L.debug("MaxLeap", "应用访问凭证不正确，请检查。exception: " + e);
                        }
                    }
                });
    }
}