package com.sflib.emoji.core;

import java.util.List;

/**
 * Created by NetEase on 2016/7/12 0012.
 */
public class EmojiFileEntry {
    private List<EmojiFileBean> emojiFileBeen;

    public List<EmojiFileBean> getEmojiFileBeen() {
        return emojiFileBeen;
    }

    public void setEmojiFileBeen(List<EmojiFileBean> emojiFileBeen) {
        this.emojiFileBeen = emojiFileBeen;
    }

    @Override
    public String toString() {
        return "EmojiFileEntry{" +
                "emojiFileBeen=" + emojiFileBeen +
                '}';
    }
}
