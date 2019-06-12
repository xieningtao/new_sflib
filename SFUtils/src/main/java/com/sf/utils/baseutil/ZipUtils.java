package com.sf.utils.baseutil;

import android.os.Environment;
import android.text.TextUtils;

import com.sf.loglib.L;
import com.sf.loglib.file.SFFileHelp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by NetEase on 2016/9/6 0006.
 */
public class ZipUtils {
    private static final String TAG = ZipUtils.class.getName();

    public static File decompress(String srcPath, String desDirName) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(desDirName)) {
            return null;
        }
        long startTime = System.currentTimeMillis();
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(
                    srcPath));//输入源zip路径
            BufferedInputStream bin = new BufferedInputStream(zin);
            boolean isDirExist = SFFileHelp.createDirOnSDCard(desDirName);
            if (isDirExist) {
                File desFile = null;
                File dirFile = null;
                ZipEntry entry;
                try {
                    byte buffer[] = new byte[1024];
                    while ((entry = zin.getNextEntry()) != null) {
                        if (entry.isDirectory()) {
                            SFFileHelp.createDirOnSDCard(desDirName + File.separator + entry.getName());
                        } else {
                            desFile = SFFileHelp.createFileOnSD(desDirName, entry.getName());
                            if (desFile != null) {
                                FileOutputStream out = new FileOutputStream(desFile);
                                BufferedOutputStream bout = new BufferedOutputStream(out);
                                int b = 0;
                                while ((b = bin.read(buffer, 0, buffer.length)) != -1) {
                                    bout.write(buffer, 0, b);
                                }
                                bout.flush();
                                bout.close();
                                out.close();
                            }
                        }
                    }
                    bin.close();
                    zin.close();
                    String path = Environment.getExternalStorageDirectory().getPath() + File.separator + desDirName;
                    return new File(path);
                } catch (IOException e) {
                    try {
                        bin.close();
                        zin.close();
                    } catch (IOException e1) {
                        L.info(TAG, "decompress exception: " + e1);
                    }
                    L.info(TAG, "decompress exception: " + e);
                } catch (Exception e) {
                    L.info(TAG, "decompress exception: " + e);
                }
            }
            long endTime = System.currentTimeMillis();
            L.info(TAG, "decompress time: " + (endTime - startTime));
        } catch (FileNotFoundException e) {
            L.info(TAG, "decompress exception: " + e);
        } catch (Exception e) {
            L.info(TAG, "decompress exception: " + e);
        }
        return null;
    }
}
