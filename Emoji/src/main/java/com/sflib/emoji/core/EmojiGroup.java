package com.sflib.emoji.core;

import java.util.List;

/**
 * Created by NetEase on 2016/7/12 0012.
 */
public class EmojiGroup {

    private List<EmojiBean> emojiGroup;

    private String goupPath;

    public String getGoupPath() {
        return goupPath;
    }

    public void setGoupPath(String goupPath) {
        this.goupPath = goupPath;
    }

    public List<EmojiBean> getEmojiGroup() {
        return emojiGroup;
    }

    public void setEmojiGroup(List<EmojiBean> emojiGroup) {
        this.emojiGroup = emojiGroup;
    }
}
