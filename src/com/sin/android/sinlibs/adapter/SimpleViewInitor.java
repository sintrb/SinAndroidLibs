package com.sin.android.sinlibs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface SimpleViewInitor {
	public View initView(Context context, int position, View convertView, ViewGroup parent, Object data);
}
