package com.sin.android.sinlibs.exutils;

import java.util.Locale;

import android.content.Context;

import com.google.gson.Gson;

public class StrUtils {
	public static String renderText(Context context, int strid, Object... args) {
		String s = context.getText(strid).toString();
		return String.format(Locale.getDefault(), s, args);
	}

	private static Gson gson = new Gson();

	public static String Obj2Str(Object o) {
		return gson.toJson(o);
	}

	public static <T> T Str2Obj(String s, Class<T> classOfT) {
		return gson.fromJson(s, classOfT);
	}

	public static String float2Str(float v) {
		// return "" + (((int) (v * 1000)) / 1000.0);
		return String.format(Locale.getDefault(), "%.03f", v);
	}

	public static String floatS2Str(String vs) {
		try {
			return float2Str(Float.parseFloat(vs));
		} catch (Exception e) {
			e.printStackTrace();
			return "-";
		}
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static String urlNoCached(String url) {
		StringBuffer sb = new StringBuffer(url);
		if (sb.indexOf("?") < 0)
			sb.append("?");
		char last = sb.charAt(sb.length() - 1);
		if (last != '&' && last != '?')
			sb.append('&');
		sb.append("_t_=");
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
}
