package com.sf.utils.baseutil;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by NetEase on 2016/10/20 0020.
 */
public class SFTextUtils {

    public enum NumberBase {
        K_BASE(1000, "k"), W_BASE(10000, "w");
        private int base;
        private String text;

        NumberBase(int base, String text) {
            this.base = base;
            this.text = text;
        }

        public int getBase() {
            return base;
        }

        public String getText() {
            return text;
        }

    }

    /**
     * @param number
     * @param base
     * @return
     */
    public static String getText(int number, NumberBase base) {
        if (number < base.getBase()) {
            return String.valueOf(number);
        } else if (number == base.getBase()) {
            return 1 + base.getText();
        } else {
            float result = number * 1.0f / base.getBase();
            return new DecimalFormat("#.0").format(result) + base.getText();
        }
    }


    public static String getSubText(String content, int number) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        int length = content.length();
        if (length < number) {
            return content;
        } else {
            return content.substring(0, number);
        }
    }
}
