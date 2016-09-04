package com.sf.circlelib;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SpannableFactory {

    public static Spannable spannableTextColor(String content, int color) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        SpannableString spanString = new SpannableString(content);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
}
