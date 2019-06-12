package com.basesmartframe.filecache.cacheentry;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by xieningtao on 15-5-20.
 */
public class CacheKeyUtil {

    public static String mapToCacheKey(String dir,String pageNum,String postfix){
        if(TextUtils.isEmpty(dir)||TextUtils.isEmpty(pageNum)||TextUtils.isEmpty(postfix))return "";
        return dir+ File.separator+pageNum+postfix;
    }

    public static String mapToCacheKey(String dir,String pageNum){
        return mapToCacheKey(dir,pageNum,".txt");
    }

    public static String mapToCacheKey(String dir){
        return mapToCacheKey(dir,"1",".txt");
    }

}
