package com.sin.android.sinlibs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	static SimpleDateFormat shortformatter = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);

	public static String getTimeString() {
		return getTimeString(new Date());
	}

	public static String getTimeString(Date date) {
		return formatter.format(date);
	}

	public static String getShortTimeString(Date date) {
		return shortformatter.format(date);
	}
}
