package com.sf.utils.baseutil;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.sf.loglib.L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by xieningtao on 15-5-20.
 */
public class SFFileHelp {
    public static final String TAG = "CacheFileHelp";

    /**
     * 10k
     */
    private static int sMemLimitation = 10;

    public static boolean isSDCardMounted() {
        return availableMemInSDcard();
    }

    public static boolean externalStorageExist() {
        boolean ret = false;
        ret = Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED);
        return ret;
    }

    public static boolean availableMemInSDcard() {
        if (!externalStorageExist()) {
            return false;
        }
        long total = getSDCardRemainSize();
        if (total < sMemLimitation) {
            return false;
        }
        return true;
    }

    private static long getSDCardRemainSize() {
        File sdcard = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(sdcard.getPath());
        long blockSize = statFs.getBlockSize();
        long avaliableBlocks = statFs.getAvailableBlocks();
        return avaliableBlocks * blockSize / 1024;
    }

    public static void removeDir(String dirPath) {
        if (isSDCardMounted()) {
            String path = Environment.getExternalStorageDirectory().getPath() + dirPath;
            File dir = new File(path);
            removeDirOrFile(dir);
        }
    }

    public static boolean removeDirOrFile(File file) {
        if (null == file)
            return false;
        if (file.isDirectory()) {
            for (String children : file.list()) {
                boolean success = removeDirOrFile(new File(file, children));
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean isFileExisted(String filePath) {
        if (isSDCardMounted()) {
            if (StringUtils.isNullOrEmpty(filePath)) {
                return false;
            }
            String path = Environment.getExternalStorageDirectory().getPath() + filePath;
            try {
                File file = new File(path);
                return (file.exists() && file.length() > 0);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean ensureDirExists(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            return dirFile.mkdirs();
        }
        return true;
    }

    public static boolean createDir(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            return dirFile.isDirectory();
        }
        return true;
    }

    public static boolean createDirsrecusive(String dir) {
        if (TextUtils.isEmpty(dir)) return false;
        int lastIndex = dir.lastIndexOf("/");
        String _dir = dir.substring(0, lastIndex);
//        if(ensureDirExists(_dir))
        return true;
    }

    public static File createFileOnSD(String dir, String name) {
        File file = null;
        if (isSDCardMounted()) {
            String dirPath = Environment.getExternalStorageDirectory().getPath() + dir;
            boolean create = createDir(dirPath);
            if (create) {
                file = new File(dirPath + "/" + name);
                try {
                    if (!file.exists() && !file.createNewFile()) {
                        file = null;
                    }
                } catch (IOException e) {
                    L.error("FileUtils", "can not create file on SD card");
                    file = null;
                }
            }
        } else {
            L.error(TAG, "fail to create dir path: " + dir);
        }
        return file;
    }


    public static String getTxtFileContent(String fileName) {
        String content = "";
        if (isSDCardMounted()) {
            if (StringUtils.isNullOrEmpty(fileName)) {
                return content;
            }
            String path = Environment.getExternalStorageDirectory().getPath() + fileName;
            File file = new File(path);
            if (file.isFile()) {
                InputStream instream = null;
                try {
                    instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader = new InputStreamReader(
                                instream);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                    }
                } catch (Exception e) {
                    L.error(TAG, "read fail, e = " + e);
                } finally {
                    if (instream != null) {
                        try {
                            instream.close();
                        } catch (Exception e) {
                            L.error(TAG, "read fail, e = " + e);
                        }
                    }
                }
            }
        }
        return content;
    }
    public static String getTxtFileContentWithAbsPath(String absPath) {
        String content = "";
        if (isSDCardMounted()) {
            if (StringUtils.isNullOrEmpty(absPath)) {
                return content;
            }
            File file = new File(absPath);
            if (file.isFile()) {
                InputStream instream = null;
                try {
                    instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader = new InputStreamReader(
                                instream);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                    }
                } catch (Exception e) {
                    L.error(TAG, "read fail, e = " + e);
                } finally {
                    if (instream != null) {
                        try {
                            instream.close();
                        } catch (Exception e) {
                            L.error(TAG, "read fail, e = " + e);
                        }
                    }
                }
            }
        }
        return content;
    }

    public static boolean writeTo(String content, String filePath) {
        if (isSDCardMounted()) {
            String path = Environment.getExternalStorageDirectory().getPath() + filePath;
            if (StringUtils.isNullOrEmpty(filePath) || StringUtils.isNullOrEmpty(content)) {
                return false;
            }
            File file = new File(path);
            if (file.isFile()) {
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);
                    if (ostream != null) {
                        OutputStreamWriter outputWriter = new OutputStreamWriter(
                                ostream);
                        PrintWriter writer = new PrintWriter(outputWriter);
                        writer.write(content);
                        writer.flush();
                        writer.close();
                    }
                } catch (Exception e) {
                    L.error(TAG, "read fail, e = " + e);
                    return false;
                } finally {
                    if (ostream != null) {
                        try {
                            ostream.close();
                        } catch (Exception e) {
                            L.error(TAG, " fail to close, e = " + e);
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
