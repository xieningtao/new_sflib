package com.sf.httpclient.newcore.cache;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.sf.httpclient.core.AjaxParams;
import com.sf.utils.baseutil.Md5Utils;
import com.sf.utils.ThreadHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class CacheIndexManager {
    private static CacheIndexManager manager = new CacheIndexManager();
    private CacheIndexAction<CacheIndexBean> mCacheIndexAction;

    /**
     * 这个会在多个线程进行操作，所以才采用线程安全的方式来操作
     */
    private List<CacheIndexBean> mAllCacheIndexBeans = new CopyOnWriteArrayList<>();


    private CacheIndexManager() {

    }

    public static CacheIndexManager getInstance() {
        return manager;
    }

    public void init(Context context, String cacheIndexName) {
        mCacheIndexAction = new CacheIndexActionImpl(context, cacheIndexName);
        ThreadHelp.runInSingleBackThread(new Runnable() {
            @Override
            public void run() {
                loadCacheIndex();
            }
        },0);
    }

    /**
     * 这个是读文件的操作,建议在后台线程操作
     */
    private void loadCacheIndex() {
        List<CacheIndexBean> beanList = mCacheIndexAction.loadIndex();
        mAllCacheIndexBeans.addAll(beanList);
    }

    /**
     * 这个是写文件的操作,建议在后台线程操作
     */
    public boolean saveAllCacheIndex() {
        return mCacheIndexAction.saveIndex(mAllCacheIndexBeans);
    }

    public boolean addCacheIndex(CacheIndexBean cacheIndexBean) {
        if (cacheIndexBean == null) {
            return false;
        }
        if (isCached(cacheIndexBean)) {
            return false;
        }
        mAllCacheIndexBeans.add(cacheIndexBean);
        return true;
    }


    public boolean isCached(CacheIndexBean cacheIndexBean) {
        if (cacheIndexBean == null) {
            return false;
        }
        return mAllCacheIndexBeans.contains(cacheIndexBean);
    }


    public CacheIndexBean build(String url, AjaxParams params, CacheType cacheType) {
        return getCacheIndexBean(url, params, cacheType);
    }

    private CacheIndexBean getCacheIndexBean(String url, AjaxParams params, CacheType cacheType) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String paramsStr = "";
        if (params != null) {
            paramsStr = params.toString();
        }
        String md5Params = Md5Utils.getMD5(url + paramsStr);
        String path = "";
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            String myPath = uri.getPath();
            int urlIndex = myPath.lastIndexOf("/");
            if (urlIndex != -1) {
                path = myPath.substring(urlIndex, myPath.length());
            }
        }
        return new CacheIndexBean(md5Params, path, cacheType.ordinal());
    }

//    public boolean removeCacheIndex(String url, AjaxParams params, CacheType cacheType) {
//        CacheIndexBean cacheIndexBean = getCacheIndexBean(url, params, cacheType);
//        if (cacheIndexBean == null) {
//            return false;
//        }
//        return mAllCacheIndexBeans.remove(cacheIndexBean);
//    }


}
