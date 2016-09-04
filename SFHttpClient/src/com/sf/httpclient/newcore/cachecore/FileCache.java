package com.sf.httpclient.newcore.cachecore;

import com.sf.utils.baseutil.SFFileHelp;

import java.io.File;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class FileCache implements CacheAction<String> {
    private final String mPath;

    public FileCache(String dirName,String fileName) {
        SFFileHelp.createDir(dirName);
        this.mPath = dirName+ File.separator+fileName;
    }

    @Override
    public boolean save(String content) {
        return SFFileHelp.writeTo(content, mPath);
    }

    @Override
    public String get() {
        return SFFileHelp.getTxtFileContent(mPath);
    }
}
