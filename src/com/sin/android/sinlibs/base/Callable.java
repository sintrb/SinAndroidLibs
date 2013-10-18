package com.sin.android.sinlibs.base;

/**
 * 可调用对象
 * 
 * @author RobinTang
 * 
 *         2013-5-5
 */
public abstract class Callable {
	public Object[] args;

	public Callable() {
		super();
	}

	/**
	 * 执行方法
	 * 
	 * @param args
	 *            不定参数
	 */
	abstract public void call(Object... args);
}
