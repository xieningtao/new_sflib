package com.sflib.emoji.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.sf.loglib.file.SFFileHelp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiLoadManager {
    private static final String TAG = EmojiLoadManager.class.getName();
    private static final String SUFIX = ".txt";
    private Map<String, EmojiGroup> emojiMaps = new HashMap<String, EmojiGroup>();
    private EmojiFileEntry mEmojiFileEntry;
    private String mEmojiPath;
    private static EmojiLoadManager emojiLoadManager = new EmojiLoadManager();

    private EmojiLoadManager() {

    }

    public Map<String, EmojiGroup> getEmojiMaps() {
        return emojiMaps;
    }

    public EmojiFileBean getEmojiFileBean(String fileId) {
        List<EmojiFileBean> emojiFileBeanList = mEmojiFileEntry.getEmojiFileBeen();
        for (EmojiFileBean fileBean : emojiFileBeanList) {
            if (fileBean.getFileId().equals(fileId)) {
                return fileBean;
            }
        }
        return null;
    }

    public static EmojiLoadManager getInstance() {
        return emojiLoadManager;
    }

    public void loadEmojiOutlineFrom(String path) {
        L.i(TAG, "method->loadEmojiOutlineFrom,path: " + path);
        File directory = getFile(path);
        if (directory == null) {
            return;
        }
        mEmojiPath = path;
        mEmojiFileEntry = getFileManifest(directory, EmojiFileEntry.class);

    }

    public List<EmojiFileBean> getEmojiBeanList() {
        if (mEmojiFileEntry == null) {
            return new ArrayList<>();
        }
        return mEmojiFileEntry.getEmojiFileBeen();
    }

    public List<String> getEmojiKeys() {
        List<String> keys = new ArrayList<>();
        if (mEmojiFileEntry == null) {
            return keys;
        }

        List<EmojiFileBean> emojiFileBeanList = mEmojiFileEntry.getEmojiFileBeen();
        if (emojiFileBeanList != null && !emojiFileBeanList.isEmpty()) {
            for (EmojiFileBean bean : emojiFileBeanList) {
                keys.add(bean.getFileId());
            }
        }
        return keys;
    }

    public void loadAllEmoji() {
        if (mEmojiFileEntry != null) {
            List<EmojiFileBean> emojiFileBeanList = mEmojiFileEntry.getEmojiFileBeen();
            for (EmojiFileBean fileBean : emojiFileBeanList) {
                if (emojiMaps.get(fileBean.getFileId()) == null) {
                    String emojiPath = mEmojiPath + File.separator + fileBean.getFileName();
                    EmojiGroup emojiGroup = readEmojiManifest(emojiPath);
                    emojiMaps.put(fileBean.getFileId(), emojiGroup);
                }
            }
        }
    }

    public EmojiFileBean getEmojiFileBeanById(String fileId) {
        if (mEmojiFileEntry == null || TextUtils.isEmpty(fileId)) {
            return null;
        }

        List<EmojiFileBean> emojiFileBeanList = mEmojiFileEntry.getEmojiFileBeen();
        if (emojiFileBeanList != null && !emojiFileBeanList.isEmpty()) {
            for (EmojiFileBean bean : emojiFileBeanList) {
                if (fileId.equals(bean.getFileId())) {
                    return bean;
                }
            }
        }
        return null;

    }

    public EmojiGroup loadEmoji(EmojiFileBean fileBean) {
        if (emojiMaps.get(fileBean.getFileId()) == null) {
            String emojiPath = mEmojiPath + File.separator + fileBean.getFileName();
            EmojiGroup emojiGroup = readEmojiManifest(emojiPath);
            emojiMaps.put(fileBean.getFileId(), emojiGroup);
            return emojiGroup;
        } else {
            return emojiMaps.get(fileBean.getFileId());
        }
    }


    private static File getFile(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            L.e(TAG, "method->loadEmojiOutlineFrom,file is not exist");
            return null;
        }
        if (!directory.isDirectory()) {
            L.e(TAG, "method->loadEmojiOutlineFrom,file is not directory");
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
            String emojiFileName = emojiFile.getName();
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

    private static List<EmojiBean> readEmojiManifest(File emojiDirectory) {
        EmojiGroup emojiGroup = getFileManifest(emojiDirectory, EmojiGroup.class);
        if (emojiGroup != null) {
            return emojiGroup.getEmojiGroup();
        }
        return new ArrayList<EmojiBean>();
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
