package com.sf.httpclient.newcore.cache;


import android.content.Context;

import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class CacheIndexActionImpl implements CacheIndexAction<CacheIndexBean> {
    private final String TAG = getClass().getName();
    private final String mCacheIndexName;
    private final File mCacheIndexDir;

    public CacheIndexActionImpl(Context context, String cacheIndexName) {
        mCacheIndexDir = context.getCacheDir();
        if (!mCacheIndexDir.exists()) {
            mCacheIndexDir.mkdirs();
        }
        this.mCacheIndexName = cacheIndexName;
    }

    /**
     * load all the index form cached file
     *
     * @return
     */
    @Override
    public List<CacheIndexBean> loadIndex() {
        File loadFile = new File(mCacheIndexDir, mCacheIndexName);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(loadFile));
            List<CacheIndexBean> cacheIndexBeanList = (List<CacheIndexBean>) objectInputStream.readObject();
        } catch (IOException e) {
            L.error(TAG, "loadIndex exception: " + e);
        } catch (ClassNotFoundException e) {
            L.error(TAG, "loadIndex exception: " + e);
        }
        return Collections.emptyList();
    }

    /**
     * @param cacheIndexBeanList
     * @return true if save successful otherwise failed
     */
    @Override
    public boolean saveIndex(List<CacheIndexBean> cacheIndexBeanList) {
        if (cacheIndexBeanList == null || cacheIndexBeanList.isEmpty()) {
            return false;
        }
        File saveFile = new File(mCacheIndexDir, mCacheIndexName);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveFile, false));
            objectOutputStream.writeObject(cacheIndexBeanList);
            return true;
        } catch (IOException e) {
            L.error(TAG, "loadIndex exception: " + e);
        }
        return false;
    }
}
