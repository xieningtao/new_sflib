package com.basesmartframe.filecache.cacheentry;

import android.text.TextUtils;

import com.sf.utils.baseutil.SFFileHelp;

import java.io.File;

/**
 * Created by xieningtao on 15-5-20.
 */
abstract public class BaseFileCache implements CacheAction<String> {
    public static final String CACHE_FILE_BASE = "/kiwi/filecache";
    private final CacheFile mCacheFile;

    public BaseFileCache(String key) {
        if(!TextUtils.isEmpty(key)) {
            int lastIndex=key.lastIndexOf("/");
            String sub_dir=key.substring(0,lastIndex);
            String file_name=key.substring(lastIndex+1,key.length());
            String dir_base=CACHE_FILE_BASE+ File.separator+sub_dir;
            mCacheFile = new CacheFile(dir_base, file_name);
            mCacheFile.createCacheFileIfNotExist();
        }else {
           throw  new IllegalArgumentException("filePath is null");
        }
    }

    public static boolean isCacheFileExist(String key){
        String path=CACHE_FILE_BASE+File.separator+key;
        return SFFileHelp.isFileExisted(path);
    }

    @Override
    public synchronized boolean writeContentTo(String content) {
        return mCacheFile.writeContent(content);
    }

    @Override
    public synchronized String readContent() {
        return mCacheFile.readContent();
    }

    abstract boolean isOutofDate();
}
