package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExpressionEngine {
	public Object eval(Object model, String exp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (exp.startsWith(".a:")) {
			return evalAttribute(model, exp.substring(3));
		} else if (exp.startsWith(".m:")) {
			return evalMethod(model, exp.substring(3));
		} else if (exp.startsWith(".")) {
			return evalAttribute(model, exp.substring(1));
		}
		throw new IllegalArgumentException("unknown expression: " + exp);
	}

	public Object evalAttribute(Object model, String att) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> clz = model.getClass();
		Field field = clz.getField(att);
		return field.get(model);
	}

	public Object evalMethod(Object model, String met) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clz = model.getClass();
		Method method = clz.getMethod(met);
		return method.invoke(model);
	}
}
