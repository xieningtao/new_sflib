package com.basesmartframe.updateutil.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务bean
 */
public class FilePreferenceService {

    private SharedPreferences pref;

    private static String DOWNLOAD_LOG_PREFERENCE = "download_log_preference";

    public FilePreferenceService(Context context) {
        pref = context.getSharedPreferences(DOWNLOAD_LOG_PREFERENCE, Context.MODE_PRIVATE);
    }

    /**
     * 获取每条线程已经下载的文件长度
     * 
     * @param path
     * @param num
     *            线程个数
     * @return
     */
    public Map<Integer, Long> getData(String path, int num) {

        Map<Integer, Long> data = new HashMap<Integer, Long>();

        for (int i = 0; i < num; i++) {
            Long length = pref.getLong(path + "_" + (i + 1), 0);
            data.put(i + 1, length);
        }

        return data;
    }

    /**
     * 保存每条线程已经下载的文件长度
     * 
     * @param path
     * @param map
     */
    public void save(String path, Map<Integer, Long> map) {

        Editor e = pref.edit();
        for (int i = 0; i < map.size(); i++) {
            e.putLong(path + "_" + (i + 1), map.get(i + 1));
            e.clear();
        }
        e.commit();

    }

    /**
     * 实时更新每条线程已经下载的文件长度
     * 
     * @param path
     * @param map
     */
    public void update(String path, Map<Integer, Long> map) {

        Editor e = pref.edit();
        for (int i = 0; i < map.size(); i++) {
            e.putLong(path + "_" + (i + 1), map.get(i + 1));
            e.commit();
        }
    }

    /**
     * 当文件下载完成后，删除对应的下载记录
     * 
     * @param path
     * @param num
     *            线程个数
     */
    public void delete(String path, int num) {

        Editor e = pref.edit();
        for (int i = 0; i < num; i++) {
            e.remove(path + "_" + (i + 1));
            e.commit();
        }

    }

}
