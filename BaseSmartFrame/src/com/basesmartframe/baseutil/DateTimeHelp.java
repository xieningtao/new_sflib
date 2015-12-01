package com.basesmartframe.baseutil;

import java.util.Calendar;
import java.util.Date;

public class DateTimeHelp {

	public static long DateToMilliseconds(Date date){
		if(date==null)return 0;
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}
}
