package com.sf.httpclient.newcore.cachecore;

import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.cache.CacheIndexBean;
import com.sf.httpclient.newcore.cache.CacheIndexManager;
import com.sf.httpclient.newcore.cache.CacheType;
import com.sf.utils.baseutil.Md5Utils;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class MemoryCacheManager {
    public Map<String, WeakReference<MemoryCache>> mMemoryCache = new ConcurrentHashMap<>();
    private static MemoryCacheManager memoryCacheManager = new MemoryCacheManager();

    private MemoryCacheManager() {

    }

    public static MemoryCacheManager getInstance() {
        return memoryCacheManager;
    }

    public boolean save(CacheIndexBean cacheIndexBean, String content) {
        boolean isCached = CacheIndexManager.getInstance().isCached(cacheIndexBean);
        if (isCached) {
            String localContent = get(cacheIndexBean);
            String localMd5 = Md5Utils.getMD5(localContent);
            String saveMd5 = Md5Utils.getMD5(content);
            if (localMd5 != saveMd5) {
                if (saveHelp(cacheIndexBean,content)) {
                    return true;
                }
            }
        } else {
            if (saveHelp(cacheIndexBean, content)) {
                return true;
            }
        }
        return false;
    }

    private boolean saveHelp(CacheIndexBean cacheIndexBean, String content) {
        if (cacheIndexBean != null) {
            MemoryCache memoryCache = new MemoryCache();
            memoryCache.save(content);
            mMemoryCache.put(cacheIndexBean.getParamsMd5(), new WeakReference<MemoryCache>(memoryCache));
            return true;
        }
        return false;
    }

    public String get(CacheIndexBean cacheIndexBean) {
        boolean isCached = CacheIndexManager.getInstance().isCached(cacheIndexBean);
        if (isCached) {
            WeakReference<MemoryCache> resultWeakReference = mMemoryCache.get(cacheIndexBean.getParamsMd5());
            if (resultWeakReference != null && resultWeakReference.get() != null) {
                return resultWeakReference.get().get();
            }
        }
        return "";
    }
}
