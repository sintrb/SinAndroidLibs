package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExpressionEngine {
    public Object eval(Object model, String exp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object res = null;
        String nexp = null;
        String[] atbs = exp.split("\\.");
        for (String s : atbs) {
            nexp = s;
            if (nexp.length() > 0 && model != null) {
                if (nexp.startsWith("a:")) {
                    // 获取属性
                    res = model = evalAttribute(model, nexp.substring(2));
                } else if (nexp.startsWith("m:")) {
                    // 执行方法
                    res = model = evalMethod(model, nexp.substring(2));
                } else if (nexp.startsWith("f:")) {
                    // 过滤器
                    res = model = evalFilter(model, nexp.substring(2));
                } else {
                    res = model = evalAttribute(model, nexp);
                }
            }
        }
        return res;
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

    public Object evalFilter(Object model, String filter) throws IllegalArgumentException {
        throw new IllegalArgumentException("unknow how to handle filter(" + filter + ") with data " + model);
    }
}
