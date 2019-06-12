package com.sf.httpclient.newcore.cachecore;

import android.content.Context;

import com.sf.dblib.DbUtils;
import com.sf.httpclient.newcore.cache.CacheIndexBean;
import com.sf.httpclient.newcore.cache.CacheIndexManager;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SqliteCacheManager {

    private DbUtils mDbUtils;
    private static SqliteCacheManager sqliteCacheManager = new SqliteCacheManager();

    private SqliteCacheManager() {

    }

    public static SqliteCacheManager getInstance() {
        return sqliteCacheManager;
    }

    public void initCache(Context context) {
        mDbUtils = DbUtils.create(context);
    }

    public boolean save(CacheIndexBean cacheIndexBean, String content) {
        if (cacheIndexBean == null) {
            return false;
        }
        SqliteCache sqliteCache = new SqliteCache(mDbUtils, cacheIndexBean.getParamsMd5());
        boolean save= sqliteCache.save(content);
        if(save){
            CacheIndexManager.getInstance().addCacheIndex(cacheIndexBean);
        }
        return save;
    }

    public String get(CacheIndexBean cacheIndexBean) {
        if (cacheIndexBean == null) {
            return "";
        }
        SqliteCache sqliteCache = new SqliteCache(mDbUtils, cacheIndexBean.getParamsMd5());
        return sqliteCache.get();
    }
}
