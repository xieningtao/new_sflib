package com.sf.utils.baseutil;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * 1.create file with time name and specific suffix
 * 2.create file name with format time
 *
 * @author xieningtao
 */
public class SFFileCreationUtil {

    public final static String TAG = "SFFileUtil";

    /**
     * 外部存储的基本路劲
     */
    public final static String BASICPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    public static String getBasicPath() {
        return BASICPATH;
    }

    /**
     * @param directory 目录名称
     * @param suffix    后缀，比如xxxxx.png .png就是后缀
     * @return 如果directory或者suffix有一个为null, 返回为null
     */
    public static File createFile(SimpleDateFormat format, String directory, String suffix) {
        if (TextUtils.isEmpty(directory) || TextUtils.isEmpty(suffix))
            return null;

        String rootDirectory = BASICPATH + File.separator + directory;
        String fullPath = rootDirectory + File.separator + createNameByTimeFormat(format) + suffix;

        // 创建目录
        File file = new File(rootDirectory);
        if (!file.exists()) {
            file.mkdir();
        }
        // 创建文件
        File image = new File(fullPath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "创建文件失败，reason: " + e.getMessage());
            return null;
        }
        return image;
    }

    public static File createFile(String format_str, String directory, String suffix) {
        if (TextUtils.isEmpty(format_str)) return null;
        SimpleDateFormat format = new SimpleDateFormat(format_str);
        return createFile(format, directory, suffix);
    }

    /**
     * 以默认工程名sfproject为目录名称来创建文件
     *
     * @param suffix 后缀，比如xxxxx.png .png就是后缀
     * @return
     */
    public static File createFile(SimpleDateFormat format, String suffix) {
        return createFile(format, BaseUtilConfig.DEFAULT_DIRECTORY, suffix);
    }

    public static File createFile(String format_str, String suffix) {
        return createFile(format_str, BaseUtilConfig.DEFAULT_DIRECTORY, suffix);
    }

    /**
     * 获取这种格式的文件名字yyyymmddhhmmss_随机数
     *
     * @param randomNum ，名字的随机数的位数
     * @return 返回值的形式为yyyymmddhhmmss_随机数
     */
    public static String createNameWithRandom(SimpleDateFormat format, int randomNum) {
        if (format == null) return "";
        if (randomNum < 0)
            randomNum = 1;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        builder.append("_");
        for (int i = 0; i < randomNum; i++) {
            int num = random.nextInt(10);
            builder.append(num + "");
        }
        return createNameByTimeFormat(format) + builder.toString();
    }

    /**
     * create name by time format such as yyMMddHHmmss
     *
     * @param format
     * @return
     */
    public static String createNameByTimeFormat(SimpleDateFormat format) {
        if (format == null) return "";
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public static String createNameByTimeFormat(String format_str) {
        if (TextUtils.isEmpty(format_str)) return "";
        SimpleDateFormat format = new SimpleDateFormat(format_str);
        return createNameByTimeFormat(format);
    }

}
