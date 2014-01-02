package com.sin.android.sinlibs.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

/**
 * 对话框常用封装
 * 
 * @author RobinTang
 *         2014-12-19
 */
public class Dialogs {

	/**
	 * 对话框回调
	 * 
	 * @author RobinTang
	 *         2013-12-19
	 */
	public interface Callback {
		/**
		 * 回调函数
		 * 
		 * @param dialog
		 *            对话框
		 * @param index
		 *            动作位置
		 * @param object
		 *            回调数据
		 * @return 一般无效
		 */
		public boolean callback(DialogInterface dialog, int index, Object object);
	}

	/**
	 * 创建一个消息框
	 * 
	 * @param context
	 *            上下文
	 * @param callback
	 *            回调接口
	 * @param title
	 *            消息框标题
	 * @param message
	 *            消息正文
	 * @param btns
	 *            按钮文本
	 * @return 对话框
	 */
	public static Dialog messageDialog(Context context, final Callback callback, String title, String message, String... btns) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);

		DialogInterface.OnClickListener lsn = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (callback != null && callback.callback(dialog, which, null))
					dialog.dismiss();
			}
		};

		switch (btns.length) {
		case 0:
			builder.setPositiveButton(android.R.string.ok, lsn);
			break;
		case 1:
			builder.setPositiveButton(btns[0], lsn);
			break;
		default:
			builder.setPositiveButton(btns[0], lsn);
			builder.setNegativeButton(btns[1], lsn);
			break;
		}

		Dialog dlg = builder.create();
		dlg.setCancelable(false);
		dlg.setCanceledOnTouchOutside(false);
		return dlg;
	}

	/**
	 * 创建一个选择框
	 * 
	 * @param context
	 *            上下文
	 * @param callback
	 *            回调接口
	 * @param title
	 *            选择框标题
	 * @param type
	 *            选择框类型，0单选并立即返回，1单选需确认，2多选需确认
	 * @param items
	 *            可选条目
	 * @return 对话框
	 */
	public static Dialog selectDialog(Context context, final Callback callback, String title, int type, String... items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		switch (type) {
		case 1:
			final View view = new View(context);
			view.setTag(-1);
			builder.setSingleChoiceItems(items, (Integer) view.getTag(), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					view.setTag(which);
				}
			});
			builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null)
						callback.callback(dialog, -1, null);
				}
			});
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null)
						callback.callback(dialog, 0, new int[] { (Integer) view.getTag() });
				}
			});
			break;
		case 2:
			final boolean[] selected = new boolean[items.length];
			for (int i = 0; i < selected.length; ++i) {
				selected[i] = false;
			}
			builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					selected[which] = isChecked;
				}
			});
			builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null)
						callback.callback(dialog, -1, null);
				}
			});
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null) {
						ArrayList<Integer> ls = new ArrayList<Integer>();
						for (int i = 0; i < selected.length; ++i) {
							if (selected[i])
								ls.add(i);
						}
						int[] sel = new int[ls.size()];
						for (int i = 0; i < ls.size(); ++i) {
							sel[i] = ls.get(i);
						}
						callback.callback(dialog, 0, sel);
					}
				}
			});
			break;
		default:
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					if (callback != null && callback.callback(dialog, item, null))
						dialog.dismiss();
				}
			});
			break;
		}

		Dialog dlg = builder.create();
		dlg.setCancelable(false);
		dlg.setCanceledOnTouchOutside(false);
		return dlg;
	}

	/**
	 * 创建一个输入对话框
	 * 
	 * @param context
	 *            上下文
	 * @param callback
	 *            回调接口
	 * @param title
	 *            标题
	 * @param text
	 *            默认的文本
	 * @return 对话框
	 */
	public static Dialog inputDialog(Context context, final Callback callback, String title, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);

		final EditText editText = new EditText(context);
		editText.setText(text);
		builder.setView(editText);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = editText.getText().toString();
				if (callback != null && callback.callback(dialog, which, text))
					dialog.dismiss();
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (callback != null && callback.callback(dialog, which, null))
					dialog.dismiss();
			}
		});

		Dialog dlg = builder.create();
		dlg.setCancelable(false);
		dlg.setCanceledOnTouchOutside(false);
		return dlg;
	}
}
