package com.sf.httpclient.newcore.cachecore;

import android.text.TextUtils;

import com.sf.dblib.DbUtils;
import com.sf.dblib.annotation.Table;
import com.sf.dblib.exception.DbException;
import com.sf.dblib.sqlite.Selector;
import com.sf.httpclient.newcore.cache.CacheIndexManager;
import com.sf.loglib.L;
import com.sf.utils.baseutil.Md5Utils;

import java.lang.annotation.Annotation;
import java.util.Calendar;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SqliteCache implements CacheAction<String> {
    private final String TAG = getClass().getName();
    private String mIndexMd5;
    private DbUtils mDbUtils;

    public SqliteCache(DbUtils dbUtils, String indexMd5) {
        this.mIndexMd5 = indexMd5;
        this.mDbUtils = dbUtils;
    }

    @Override
    public boolean save(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            HttpCacheBean cacheBean = mDbUtils.findFirst(Selector.from(HttpCacheBean.class).where("indexMd5", "==", mIndexMd5));
            if (cacheBean != null) {
                String md5 = Md5Utils.getMD5(str);
                if (!md5.equals(cacheBean.getContentMd5())) {
                    cacheBean.setContent(str);
                    cacheBean.setContentMd5(md5);
                    cacheBean.setDate(Calendar.getInstance().getTime());
                    mDbUtils.saveOrUpdate(cacheBean);
                    return true;
                }
            } else {
                HttpCacheBean httpCacheBean = new HttpCacheBean();
                String md5 = Md5Utils.getMD5(str);
                httpCacheBean.setContent(str);
                httpCacheBean.setContentMd5(md5);
                httpCacheBean.setDate(Calendar.getInstance().getTime());
                mDbUtils.save(httpCacheBean);
                return true;
            }
        } catch (DbException e) {
            L.error(TAG, "save exception: " + e);
        }
        return false;
    }

    @Override
    public String get() {
        try {
            HttpCacheBean cacheBean = mDbUtils.findFirst(Selector.from(HttpCacheBean.class).where("indexMd5", "==", mIndexMd5));
            if (cacheBean != null) {
                return cacheBean.getContent();
            }
        } catch (DbException e) {
            L.error(TAG, "get exception: " + e);
        }
        return "";
    }

}
