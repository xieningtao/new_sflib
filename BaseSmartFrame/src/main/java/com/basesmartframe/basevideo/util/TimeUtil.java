package com.basesmartframe.basevideo.util;

/**
 * Created by xieningtao on 15-5-7.
 */
public class TimeUtil {
    public static int getSeconds(int milliseconds){
        return (milliseconds-getMinutes(milliseconds)*(1000*60))/1000;
    }

    public static int getMinutes(int milliseconds){
        return milliseconds/(1000*60);
    }

    public static String getMSFormatTime(int milliseconds){
        int minutes=getMinutes(milliseconds);
        int seconds=getSeconds(milliseconds);
        return String.format("%02d:%02d",minutes,seconds);
    }
}
