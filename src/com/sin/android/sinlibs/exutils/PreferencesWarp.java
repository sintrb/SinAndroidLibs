package com.sin.android.sinlibs.exutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferencesWarp {
	protected Context context;
	private SharedPreferences preferences;
	private Gson gson;

	public PreferencesWarp(Context context, SharedPreferences preferences) {
		super();
		this.context = context;
		this.preferences = preferences;
		this.gson = new Gson();
	}

	public <T> T getObj(String name, Class<T> classOfT) {
		String v = preferences.getString(name, null);
		try {
			T o = gson.fromJson(v, classOfT);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setObj(String name, Object o) {
		try {
			preferences.edit().putString(name, gson.toJson(o)).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
