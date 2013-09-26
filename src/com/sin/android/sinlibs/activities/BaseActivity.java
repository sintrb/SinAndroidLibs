package com.sin.android.sinlibs.activities;

import com.sin.android.sinlibs.base.Callable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * BaseActivity
 * 
 * @author RobinTang
 * 
 *         2013-5-5
 */

public class BaseActivity extends Activity {
	private static final int WHAT_CALLABLE = 0;

	
	// 私有变量
	protected ProgressDialog processDialog = null;
	
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == WHAT_CALLABLE) {
				Callable callable = (Callable) msg.obj;
				callable.call(callable.args);
			} else {
				super.handleMessage(msg);
			}
		}
	};

	/**
	 * 主线程调用
	 * 
	 * @param callable
	 *            可调用对对象
	 * @param args
	 *            参数
	 */
	public void safeCall(Callable callable, Object... args) {
		if (args.length > 0)
			callable.args = args;
		Message message = new Message();
		message.obj = callable;
		message.what = WHAT_CALLABLE;
		handler.sendMessage(message);
	}

	/**
	 * 异步调用
	 * 
	 * @param callable
	 *            可调用对对象
	 * @param args
	 *            参数
	 */
	public void asynCall(Callable callable, Object... args) {
		final Callable callable0 = callable;
		if (args != null && args.length > 0)
			callable.args = args;
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				callable0.call(callable0.args);
			}
		});
		th.start();
	}

	/**
	 * 安全方式Toast创建
	 * 
	 * @param text
	 *            文本
	 * @param duration
	 *            显示时间
	 */
	public void safeToast(String text, int duration) {
		safeCall(new Callable() {
			@Override
			public void call(Object... args) {
				Toast.makeText(BaseActivity.this, (String) args[0], (Integer) args[1]).show();
			}
		}, text, duration);
	}

	public void safeToast(String text) {
		safeCall(new Callable() {
			@Override
			public void call(Object... args) {
				Toast.makeText(BaseActivity.this, (String) args[0], (Integer) args[1]).show();
			}
		}, text, Toast.LENGTH_SHORT);
	}

	public void safeToast(int textid, int duration) {
		this.safeToast(getResources().getString(textid), duration);
	}
	
	public void safeToast(int textid) {
		this.safeToast(getResources().getString(textid));
	}
	
	/**
	 * 创建消息对话框
	 * 
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param pstBtn
	 *            PositiveButton文字
	 * @param ngtBtn
	 *            NegativeButton文字
	 * @param pstLsn
	 *            PositiveButton的监听器
	 * @param ngtLsn
	 *            NegativeButton的监听
	 * @param oclLsn
	 *            OnCancelListener
	 * @return 对话框
	 */
	public Dialog crateMessageDialog(String title, String message, String pstBtn, String ngtBtn, DialogInterface.OnClickListener pstLsn, DialogInterface.OnClickListener ngtLsn, DialogInterface.OnCancelListener oclLsn) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(pstBtn, pstLsn);
		builder.setNegativeButton(ngtBtn, ngtLsn);
		builder.setOnCancelListener(oclLsn);
		return builder.create();
	}

	/**
	 * 创建消息对话框
	 * 
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框消息
	 * @param pstBtn
	 *            PositiveButton文字
	 * @param ngtBtn
	 *            NegativeButton文字
	 * @param pstLsn
	 *            PositiveButton的监听器
	 * @param ngtLsn
	 *            NegativeButton的监听
	 * @param oclLsn
	 *            OnCancelListener
	 * @return 对话框
	 */
	public Dialog crateMessageDialog(int title, int message, int pstBtn, int ngtBtn, DialogInterface.OnClickListener pstLsn, DialogInterface.OnClickListener ngtLsn, DialogInterface.OnCancelListener oclLsn) {
		Resources res = this.getResources();
		return crateMessageDialog(res.getString(title), res.getString(message), res.getString(pstBtn), res.getString(ngtBtn), pstLsn, ngtLsn, oclLsn);
	}
	

	public void asynCallAndShowProcessDlg(int messageid, Callable callable, Object... args) {
		asynCallAndShowProcessDlg(getResources().getString(messageid), callable, args);
	}

	public void asynCallAndShowProcessDlg(String message, Callable callable, Object... args) {
		final Callable clb = callable;
		final Object[] fargs = args;
		if (processDialog == null) {
			processDialog = new ProgressDialog(this);
		}
		processDialog.setMessage(message);
		processDialog.setCancelable(false);
		processDialog.setCanceledOnTouchOutside(false);
		processDialog.show();
		asynCall(new Callable() {

			@Override
			public void call(Object... args) {
				clb.call(fargs);
				safeCall(new Callable() {
					@Override
					public void call(Object... args) {
						processDialog.dismiss();
					}
				});
			}
		});
	}
}
