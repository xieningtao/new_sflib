package com.basesmartframe.basevideo.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xieningtao on 15-3-31.
 */
public class DateHelp {

    public static long DateToMilliseconds(Date date){
        if(date==null)return 0;
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }
}
