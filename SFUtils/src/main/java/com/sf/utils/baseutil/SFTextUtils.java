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

    public enum NumberUnit {
        K(10000, 9999999, "K"), M(10000000, 99999999999l, "m"), MAX(100000000000l, Long.MAX_VALUE, "m+");
        private long leftInterval;
        private long rightInterval;
        private String unit;

        NumberUnit(long left, long right, String unit) {
            this.leftInterval = left;
            this.rightInterval = right;
            this.unit = unit;
        }

        public long getLeftInterval() {
            return leftInterval;
        }

        public void setLeftInterval(long leftInterval) {
            this.leftInterval = leftInterval;
        }

        public long getRightInterval() {
            return rightInterval;
        }

        public void setRightInterval(long rightInterval) {
            this.rightInterval = rightInterval;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public static String formatNumber(long value) {
        if (NumberUnit.K.getLeftInterval() > value) {
            return String.valueOf(value);
        } else if (NumberUnit.K.getLeftInterval() <= value && value <= NumberUnit.K.getRightInterval()) {
            int curValue = (int) (value / NumberUnit.K.getLeftInterval());
            return String.valueOf(curValue) + NumberUnit.K.getUnit();
        } else if (NumberUnit.M.getLeftInterval() <= value && value <= NumberUnit.M.getRightInterval()) {
            int curValue = (int) (value / NumberUnit.M.getLeftInterval());
            return String.valueOf(curValue) + NumberUnit.M.getUnit();
        } else {
            int curValue = (int) (value / NumberUnit.MAX.getLeftInterval());
            return String.valueOf(curValue) + NumberUnit.MAX.getUnit();
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
