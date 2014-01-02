package com.sin.android.sinlibs.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * Assets文件操作封装
 * 
 * @author RobinTang
 *         2014-1-2
 */
public class AssetsUtils {
	/**
	 * 读取文本文件
	 * @param context	上下文
	 * @param fileName	文件名
	 * @return	文本内容
	 */
	public static String readAssetTxt(Context context, String fileName) {
		try {
			InputStreamReader ir = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader br = new BufferedReader(ir);
			String l = null;
			StringBuffer sb = new StringBuffer();
			while ((l = br.readLine()) != null) {
				sb.append(l);
				sb.append("\n");
			}
			br.close();
			ir.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
