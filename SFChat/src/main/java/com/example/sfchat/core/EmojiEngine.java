package com.example.sfchat.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public class EmojiEngine {

    private String parseEmoji(String emoji) {
        String regEx = "(\\[(\\\\([0-9]|[a-z]){3,5}){1,11}\\])";
        int start = 0;

        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(emoji);

        while (mat.find(start)) {
            String hit = mat.group();
            if (hit != null && hit.length() >= 2) {
                String realContent = hit.substring(1, hit.length() - 1);
                realContent = realContent.replace("\\\\", "\\");
                String unicodeStr = unicodeToString(realContent);
                emoji = emoji.replace(hit, unicodeStr);
                start = mat.end();
            } else {
                start = mat.start() + 1;
            }
        }
        return emoji;
    }

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;

    }
}
