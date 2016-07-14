package com.sflib.emoji.core;

import android.text.TextUtils;

import com.basesmartframe.filecache.cacheentry.FileCacheManager;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.sf.utils.baseutil.SFFileHelp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiHelp {
    private static final String TAG = EmojiHelp.class.getName();
    private static final String SUFIX = ".txt";
    private static Map<String, EmojiGroup> emojiMaps=new HashMap<String, EmojiGroup>();

    public static Map<String,EmojiGroup> getEmojiMaps(){
        return emojiMaps;
    }
    public static void loadEmojiFrom(String path) {
        L.i(TAG, "method->loadEmojiFrom,path: " + path);
        File directory = getFile(path);
        if (directory == null) {
            return ;
        }

        EmojiFileEntry emojiFileEntry = getFileManifest(directory, EmojiFileEntry.class);
        if (emojiFileEntry != null) {
            List<EmojiFileBean> emojiFileBeanList = emojiFileEntry.getEmojiFileBeen();
            for (EmojiFileBean fileBean : emojiFileBeanList) {
                String emojiPath = path + File.separator + fileBean.getFileName();
               EmojiGroup emojiGroup = readEmojiManifest(emojiPath);
                emojiMaps.put(fileBean.getFileId(),emojiGroup);
            }
        }
    }

    private static File getFile(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            L.e(TAG, "method->loadEmojiFrom,file is not exist");
            return null;
        }
        if (!directory.isDirectory()) {
            L.e(TAG, "method->loadEmojiFrom,file is not directory");
            return null;
        }
        return directory;
    }

    private static <T> T getFileManifest(File directory, Class<T> fileClass) {
        if (directory == null || !directory.isDirectory() || !directory.exists()) {
            L.e(TAG, "method->getFileManifest,file is illegal");
            return null;
        }
        File manifestFile = null;
        for (File emojiFile : directory.listFiles()) {
            String emojiFileName=emojiFile.getName();
            if (emojiFileName.endsWith(SUFIX)) {
                manifestFile = emojiFile;
                break;
            }
        }
        if (manifestFile != null) {
            String manifestFileName = manifestFile.getPath();
            L.i(TAG, "method->getFileManifest,manifestFileName: " + manifestFileName);
            T emojiFileEntry = parse(manifestFileName, fileClass);
            if (emojiFileEntry != null) {
                return emojiFileEntry;
            }
        }
        return null;
    }

    private static <T> T getFileManifest(String directoryPath, Class<T> fileClass) {
        File directory = getFile(directoryPath);
        return getFileManifest(directory, fileClass);
    }

    private static List<NewEmojiBean> readEmojiManifest(File emojiDirectory) {
        EmojiGroup emojiGroup = getFileManifest(emojiDirectory, EmojiGroup.class);
        if (emojiGroup != null) {
            return emojiGroup.getEmojiGroup();
        }
        return new ArrayList<NewEmojiBean>();
    }

    private static EmojiGroup readEmojiManifest(String directoryPath) {
        File emojiDirectory = getFile(directoryPath);
        EmojiGroup emojiGroup = getFileManifest(emojiDirectory, EmojiGroup.class);
        if (emojiGroup != null) {
            emojiGroup.setGoupPath(directoryPath);
            return emojiGroup;
        }
        return null;
    }

    private static <T> T parse(String manifestFileName, Class<T> emojiClass) {
        String manifestContent = SFFileHelp.getTxtFileContentWithAbsPath(manifestFileName);
        if (!TextUtils.isEmpty(manifestContent)) {
            try {
                Gson gson = new Gson();
                T emojiGroup = gson.fromJson(manifestContent, emojiClass);
                if (emojiGroup != null) {
                    return emojiGroup;
                }
            } catch (Exception e) {
                L.e(TAG, "method->parse,fail to parse json,exception: " + e);
            }
        }
        return null;
    }

}
