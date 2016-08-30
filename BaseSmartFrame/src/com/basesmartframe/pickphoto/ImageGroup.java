/**
 * @(#)ImageGroup.java, 2015年1月15日. 
 * 
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import java.util.ArrayList;

/**
 * 照片文件夹数据模型
 * @author xltu
 *
 */
public class ImageGroup {

    private String mDirName = "";
    
    private String mDirPath = "";

    private ArrayList<ImageBean> mImages = new ArrayList<ImageBean>();

    public String getDirName() {
        return mDirName;
    }

    public void setDirName(String dirName) {
        this.mDirName = dirName;
    }
    
    public String getDirPath() {
        return mDirPath;
    }

    public void setDirPath(String dirPath) {
        this.mDirPath = dirPath;
    }

    /**
     * 获取第一张图片的路径(作为封面)
     * 
     * @return
     */
    public ImageBean getFirstImgPath() {
        if (mImages.size() > 0) {
            return mImages.get(0);
        }
        return null;
    }

    /**
     * 获取图片数量
     * 
     * @return
     */
    public int getImageCount() {
        return mImages.size();
    }

    public ArrayList<ImageBean> getImages() {
        return mImages;
    }

    /**
     * 添加一张图片
     * 
     * @param image
     */
    public void addImage(ImageBean image) {
        if (mImages == null) {
            mImages = new ArrayList<ImageBean>();
        }
        mImages.add(image);
    }
    
    public void addImage(ArrayList<ImageBean> data){
        if (mImages == null) {
            mImages = new ArrayList<ImageBean>();
        }
        mImages.addAll(data);
    }

    @Override
    public String toString() {
        return "ImageGroup [firstImgPath=" + getFirstImgPath() + ", dirName=" + mDirName
                + ", imageCount=" + getImageCount() + "]";
    }

    /**
     * <p>
     * 重写该方法
     * <p>
     * 使只要图片所在的文件夹名称(dirName)相同就属于同一个图片组
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImageGroup)) {
            return false;
        }
        return mDirPath.equals(((ImageGroup)o).mDirPath);
    }
}
