package com.sflib.emoji.core;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiBean {
    public final String mCode;

    public final long mId;

    public final String mKey;

    public final String mValue;

    public final String mFullPath;

    public final String mText;

   private EmojiBean(String code, long id, String key, String value, String fullPath, String text) {
        this.mCode = code;
        this.mId = id;
        this.mKey = key;
        this.mValue = value;
        this.mFullPath = fullPath;
        this.mText = text;
    }

    public static class EmojiBeanBuilder {
        private String code;
        private long id;
        private String key;
        private String value;
        private String fullPath;
        private String text;

        public EmojiBeanBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public EmojiBeanBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public EmojiBeanBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public EmojiBeanBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public EmojiBeanBuilder setFullPath(String fullPath) {
            this.fullPath = fullPath;
            return this;
        }

        public EmojiBeanBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public EmojiBean createEmojiBean() {
            return new EmojiBean(code, id, key, value, fullPath, text);
        }
    }
}
