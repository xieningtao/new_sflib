package com.sf.utils.baseutil;

import android.text.TextUtils;

import com.sf.loglib.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用时间的format
 *
 * @author xieningtao
 */
public class DateFormatHelp {

    // refactor to enum
    public static String _YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static String _YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String SLASH_YYYYMMDD = "yyyy/MM/dd";

    /**
     * general simple data format
     */
    public final static SimpleDateFormat _YYYYMMDDHHMM_FORMAT = new SimpleDateFormat(DateFormatHelp._YYYYMMDDHHMM);
    public final static SimpleDateFormat _YYYYMMDDHHMMSS_FORMAT = new SimpleDateFormat(DateFormatHelp._YYYYMMDDHHMMSS);
    public final static SimpleDateFormat SLASH_YYYYMMDD_FORMAT = new SimpleDateFormat(DateFormatHelp.SLASH_YYYYMMDD);

    public static String dateTimeFormat(Calendar calender, String format_str) {
        if (calender == null || TextUtils.isEmpty(format_str))
            return "";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(format_str);
        return format.format(calender.getTime());
    }

    public static Date StrDateToCalendar(String cotent, String format_str) {
        if (TextUtils.isEmpty(cotent) || TextUtils.isEmpty(format_str))
            return null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(format_str);
        try {
            return format.parse(cotent);
        } catch (ParseException e) {
            e.printStackTrace();
            L.error(DateFormatHelp.class, e.getMessage());
            return null;
        }
    }

    public static String DateTimeToStr(Date date, String format_str) {
        if (date == null || TextUtils.isEmpty(format_str))
            return null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(format_str);
        return format.format(date);
    }
}
