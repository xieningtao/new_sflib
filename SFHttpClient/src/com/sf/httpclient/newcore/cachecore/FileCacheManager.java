package com.sf.httpclient.newcore.cachecore;

import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.cache.CacheIndexBean;
import com.sf.httpclient.newcore.cache.CacheIndexManager;
import com.sf.httpclient.newcore.cache.CacheType;
import com.sf.utils.baseutil.Md5Utils;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class FileCacheManager {
    private static FileCacheManager fileCacheManager = new FileCacheManager();
    private String mBasePath="sf_http_cache";

    private FileCacheManager() {

    }

    public void setBasePath(String basePath) {
        mBasePath = basePath;
    }

    public static FileCacheManager getInstance() {
        return fileCacheManager;
    }

    public boolean save(CacheIndexBean cacheIndexBean, String content) {
        return saveHelp(cacheIndexBean, content);
    }

    public String get(CacheIndexBean cacheIndexBean) {
        return getHelp(cacheIndexBean);
    }

    private boolean saveHelp(CacheIndexBean cacheIndexBean, String content) {
        if (cacheIndexBean == null) {
            return false;
        }
        boolean isCached = CacheIndexManager.getInstance().isCached(cacheIndexBean);
        if (isCached) {
            String localContent = getHelp(cacheIndexBean);
            String localMd5 = Md5Utils.getMD5(localContent);
            String saveMd5 = Md5Utils.getMD5(content);
            if (localMd5 != saveMd5) {
                return doSave(cacheIndexBean, content);
            } else {
                return true;
            }
        } else {
            return doSave(cacheIndexBean, content);
        }
    }

    private boolean doSave(CacheIndexBean cacheIndexBean, String content) {
        FileCache fileCache = new FileCache(mBasePath,cacheIndexBean.getParamsMd5());
        boolean save = fileCache.save(content);
        if (save) {
            CacheIndexManager.getInstance().addCacheIndex(cacheIndexBean);
        }
        return save;
    }

    private String getHelp(CacheIndexBean cacheIndexBean) {
        if (cacheIndexBean == null) {
            return "";
        }
        FileCache fileCache = new FileCache(mBasePath,cacheIndexBean.getParamsMd5());
        return fileCache.get();
    }


}
