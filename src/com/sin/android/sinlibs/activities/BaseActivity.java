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
import android.view.View;
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

	public void showToast(int textid) {
		showToast(textid, Toast.LENGTH_SHORT);
	}

	public void showToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	public void showToast(int textid, int duration) {
		Toast.makeText(BaseActivity.this, textid, duration).show();
	}

	public void showToast(String text, int duration) {
		Toast.makeText(BaseActivity.this, text, duration).show();
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
	public Dialog createMessageDialog(String title, String message, String pstBtn, String ngtBtn, DialogInterface.OnClickListener pstLsn, DialogInterface.OnClickListener ngtLsn, DialogInterface.OnCancelListener oclLsn) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(pstBtn, pstLsn);
		builder.setNegativeButton(ngtBtn, ngtLsn);
		builder.setOnCancelListener(oclLsn);
		return builder.create();
	}

	/**
	 * 使用视图空间创建对话框
	 * 
	 * @param title
	 *            对话框标题
	 * @param view
	 *            对话框正文控件
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
	public Dialog createMessageDialog(String title, View view, String pstBtn, String ngtBtn, DialogInterface.OnClickListener pstLsn, DialogInterface.OnClickListener ngtLsn, DialogInterface.OnCancelListener oclLsn) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setView(view);
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
	public Dialog createMessageDialog(int title, int message, int pstBtn, int ngtBtn, DialogInterface.OnClickListener pstLsn, DialogInterface.OnClickListener ngtLsn, DialogInterface.OnCancelListener oclLsn) {
		Resources res = this.getResources();
		return createMessageDialog(res.getString(title), res.getString(message), res.getString(pstBtn), res.getString(ngtBtn), pstLsn, ngtLsn, oclLsn);
	}

	public void asynCallAndShowProcessDlg(int messageid, Callable callable, Object... args) {
		asynCallAndShowProcessDlg(getResources().getString(messageid), callable, args);
	}

	public void asynCallAndShowProcessDlg(String message, Callable callable, Object... args) {
		asynCallAndShowProcessDlg(-1, 0, message, callable, args);
	}

	public void safeSetProcessDlg(int messageid, int procs) {
		safeCall(new Callable() {

			@Override
			public void call(Object... args) {
				setProcessDlg((Integer) args[0], (Integer) args[1]);
			}
		}, messageid, procs);
	}

	public void safeSetProcessDlg(String message, int procs) {
		safeCall(new Callable() {

			@Override
			public void call(Object... args) {
				setProcessDlg((String) args[0], (Integer) args[1]);
			}
		}, message, procs);
	}

	public void setProcessDlg(int messageid, int procs) {
		setProcessDlg(messageid != 0 ? getResources().getString(messageid) : null, procs);
	}

	public void setProcessDlg(String message, int procs) {
		if (processDialog != null) {
			if (message != null)
				processDialog.setMessage(message);
			if (procs >= 0) {
				processDialog.setProgress(procs);
				processDialog.setTitle("EE" + procs);
			}
		}
	}

	public void asynCallAndShowProcessDlg(int procs, int max, int messageid, Callable callable, Object... args) {
		asynCallAndShowProcessDlg(procs, max, getResources().getString(messageid), callable, args);
	}

	public void asynCallAndShowProcessDlg(int procs, int max, String message, Callable callable, Object... args) {
		final Callable clb = callable;
		final Object[] fargs = args;
		// if (processDialog == null) {
		processDialog = new ProgressDialog(this);
		processDialog.setCancelable(false);
		processDialog.setCanceledOnTouchOutside(false);
		processDialog.setIndeterminate(false);
		// }
		if (procs >= 0 && max > 0) {
			processDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			processDialog.setProgress(procs);
			processDialog.setMax(max);
		} else {
			processDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		}
		processDialog.setMessage(message);
		processDialog.show();
		asynCall(new Callable() {

			@Override
			public void call(Object... args) {
				clb.call(fargs);
				safeCall(new Callable() {
					@Override
					public void call(Object... args) {
						if (processDialog.isShowing() && !BaseActivity.this.isFinishing()) {
							try {
								processDialog.dismiss();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		});
	}
}
