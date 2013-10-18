package com.sin.android.sinlibs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 视图初始化器
 * 
 * @author RobinTang
 * 
 *         2013-10-09
 */
public interface SimpleViewInitor {
	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文
	 * @param position
	 *            将要初始化的子视图位置
	 * @param convertView
	 *            转换视图
	 * @param parent
	 *            父视图
	 * @param data
	 *            该子视图对应的数据，可以强制转换
	 * @return 初始化过的视图
	 */
	public View initView(Context context, int position, View convertView, ViewGroup parent, Object data);
}
