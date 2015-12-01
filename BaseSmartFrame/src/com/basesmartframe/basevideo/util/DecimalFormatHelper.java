package com.basesmartframe.basevideo.util;

import android.text.TextUtils;

/**
 * Created by xieningtao on 15-6-9.
 */
public class DecimalFormatHelper {


    public enum DecimalPattern {
        W_PATTERN("%.1f万", 10000, 0),
        K_PATTERN("%.1fK", 1000, 0);

        public final String mPatternStr;
        public final int mUpBound;
        public final int mLowBound;

        DecimalPattern(String patternStr, int upBound, int lowBound) {
            this.mPatternStr = patternStr;
            this.mUpBound = upBound;
            this.mLowBound = lowBound;
        }
    }

    public static String format(String num_str, DecimalPattern pattern) {
        if (TextUtils.isEmpty(num_str)) return "0";
        int num = 0;
        try {
            num = Integer.valueOf(num_str);
        } catch (Exception e) {
            // Do nothing
        }
        return format(num, pattern);
    }

    public static String format(int num, DecimalPattern pattern) {
        String num_txt;
        if (num > pattern.mUpBound) {
            float floatNum = (float) (num * 1.0 / pattern.mUpBound);
            num_txt = String.format(pattern.mPatternStr, floatNum);
        } else if (num < pattern.mLowBound) {
            num_txt = String.valueOf(pattern.mLowBound);
        } else {
            num_txt = String.valueOf(num);
        }
        return num_txt;
    }
}
