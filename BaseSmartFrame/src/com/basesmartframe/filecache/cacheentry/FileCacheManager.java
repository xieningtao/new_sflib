package com.basesmartframe.filecache.cacheentry;

import com.sf.utils.baseutil.SFFileHelp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieningtao on 15-5-20.
 */
public class FileCacheManager {

    private static FileCacheManager instance = new FileCacheManager();

    private Map<String, BaseFileCache> mCacheMap = new HashMap<>();

    private boolean flag = true;

    private FileCacheManager() {

    }

    public static FileCacheManager getInstance() {
        return instance;
    }

    public boolean isCacheExist(String dir, String pageNum) {
        String key = CacheKeyUtil.mapToCacheKey(dir, pageNum);
        return StringFileCache.isCacheFileExist(key);
    }

    public String get(String dir, String pageNum) {
        String key = CacheKeyUtil.mapToCacheKey(dir, pageNum);
        StringFileCache fileCache = getFileCache(key);
        if (null == fileCache) return "";
        return fileCache.readContent();
    }

    public boolean save(String dir, String pageNum, String content) {
        String key = CacheKeyUtil.mapToCacheKey(dir, pageNum);
        StringFileCache fileCache = getFileCache(key);
        if (null == fileCache) return false;
        return fileCache.writeContentTo(content);
    }

    synchronized private StringFileCache getFileCache(String key) {
        StringFileCache fileCache = null;
        if (null != mCacheMap) {
            if (mCacheMap.containsKey(key)) {
                fileCache = (StringFileCache) mCacheMap.get(key);
            }
            if (null == fileCache) {
                fileCache = new StringFileCache(key);
                mCacheMap.put(key, fileCache);
            } else {
                if (!StringFileCache.isCacheFileExist(key)) {
                    fileCache = new StringFileCache(key);
                    mCacheMap.put(key, fileCache);
                }
            }
        }
        return fileCache;
    }

    synchronized public void removeAllCache() {
        mCacheMap.clear();
        String relativePath = BaseFileCache.CACHE_FILE_BASE;
        SFFileHelp.removeDir(relativePath);
    }

    synchronized public void clean() {
        mCacheMap.clear();
    }
}
