package com.sflib.emoji.core;

import android.util.SparseArray;

import java.util.List;

/**
 * Created by NetEase on 2016/7/13 0013.
 */
public class ConfiguredEmojiGroup {

    private SparseArray<List<NewEmojiBean>> mEmojiBeans = new SparseArray<List<NewEmojiBean>>();

    private int mColumNum;
    private int mRow;
    private EmojiGroup mEmojiGroup;

    public SparseArray<List<NewEmojiBean>> getmEmojiBeans() {
        return mEmojiBeans;
    }

    public ConfiguredEmojiGroup(int row,int colum,EmojiGroup emojiGroup) {
        if (colum < 1) {
            colum = 1;
        }
        if(row<1){
            row=1;
        }
        this.mRow=row;
        this.mColumNum = colum;
        this.mEmojiGroup=emojiGroup;
    }
    public String getGroupPath(){
        return mEmojiGroup.getGoupPath();
    }
    public void doConfigure() {
        if (mEmojiGroup != null && mEmojiGroup.getEmojiGroup() != null) {
            List<NewEmojiBean> beans = mEmojiGroup.getEmojiGroup();
            int reminderSize = beans.size();
            int startIndex = 0;
            int endIndex = 0;
            int index = 0;
            int pageSize=mRow*mColumNum;
            while (reminderSize >=pageSize) {
                endIndex = startIndex+pageSize;
                mEmojiBeans.put(index, beans.subList(startIndex, endIndex));
                startIndex=endIndex;
                reminderSize=reminderSize-pageSize;
                index++;
            }
            if(reminderSize>0){
                mEmojiBeans.put(index,beans.subList(startIndex,startIndex+reminderSize));
            }
        }
    }
}
