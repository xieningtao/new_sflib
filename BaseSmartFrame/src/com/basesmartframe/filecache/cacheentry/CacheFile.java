package com.basesmartframe.filecache.cacheentry;


import com.sf.loglib.file.SFFileHelp;

import java.io.File;

/**
 * Created by xieningtao on 15-5-20.
 */
public class CacheFile {

    private final String mBase;
    private final String mFileName;
    private final String mPath;

    public CacheFile(String base, String fileName) {
        this.mBase = base;
        this.mFileName = fileName;
        mPath = mBase + File.separator + mFileName;
    }


    public boolean createCacheFileIfNotExist() {
        if (!SFFileHelp.isFileExisted(mPath)) {
            File file = SFFileHelp.createFileOnSD(mBase, mFileName);
            return file == null ? false : true;
        }
        return true;
    }


    public boolean isCacheExist() {
        return SFFileHelp.isFileExisted(mPath);
    }

    public String readContent() {
        return SFFileHelp.getTxtFileContent(mPath);
    }


    public boolean writeContent(String content) {
        return SFFileHelp.writeTo(content, mPath);
    }

    public void removeAllCaches() {
        SFFileHelp.removeDir(mBase);
    }
}
