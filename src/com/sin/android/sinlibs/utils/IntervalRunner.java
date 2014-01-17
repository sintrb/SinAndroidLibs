package com.sin.android.sinlibs.utils;

import com.sin.android.sinlibs.base.Callable;

/**
 * 周期性任务
 * 
 * @author RobinTang 2014-1-17
 */
public class IntervalRunner implements Runnable {
	private Callable callable = null;
	private int interval = 100;
	private Object[] args = null;
	private boolean running = false;
	private Thread thread = null;

	public IntervalRunner(Callable callable, int interval, Object... args) {
		super();
		this.callable = callable;
		this.interval = interval;
		this.args = args;
	}

	public void stop() {
		this.running = false;
	}

	public int getInterval() {
		return interval;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			this.callable.call(this.args);
			if (interval > 0) {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 直接周期性执行可调用对象
	 * 
	 * @param callable
	 *            可调用对象
	 * @param interval
	 *            周期
	 * @param args
	 *            其他参数
	 * @return 执行的线程
	 */
	public static IntervalRunner run(Callable callable, int interval, Object... args) {
		IntervalRunner runner = new IntervalRunner(callable, interval, args);
		Thread thread = new Thread(runner);
		runner.thread = thread;
		thread.start();
		return runner;
	}
}
