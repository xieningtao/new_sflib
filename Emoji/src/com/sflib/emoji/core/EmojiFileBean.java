package com.sflib.emoji.core;

/**
 * Created by NetEase on 2016/7/12 0012.
 */
public class EmojiFileBean {
    private String fileName;
    private String fileId;
    private String fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "EmojiFileBean{" +
                "fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
