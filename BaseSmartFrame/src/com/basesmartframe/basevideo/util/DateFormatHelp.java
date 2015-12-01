package com.basesmartframe.basevideo.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xieningtao on 15-3-31.
 */
public class DateFormatHelp {

    public static String _YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static String _YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String SLASH_YYYYMMDD = "yyyy/MM/dd";

    public static String dateTimeFormat(Calendar calender, String format_str) {
        if (TextUtils.isEmpty(format_str)) return "";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(format_str);
        return format.format(calender.getTime());
    }

    public static Date StrDateToCalendar(String cotent) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(cotent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateTimeToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy/MM/dd");
        return format.format(date);
    }
}

