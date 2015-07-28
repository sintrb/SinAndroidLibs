package com.sin.android.sinlibs.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;

public class InjectUtils {
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Ignore {

	}

	private static InjectUtils utils;

	public static InjectUtils getUtils() {
		if (utils == null)
			utils = new InjectUtils();
		return utils;
	}

	public interface ViewGetter {
		public View findViewById(int id);
	}

	public class ActivityViewGetter implements ViewGetter {
		private Activity activity;

		public ActivityViewGetter(Activity activity) {
			super();
			this.activity = activity;
		}

		@Override
		public View findViewById(int id) {
			return activity.findViewById(id);
		}
	}

	public class ViewViewGetter implements ViewGetter {
		private View view;

		public ViewViewGetter(View view) {
			super();
			this.view = view;
		}

		@Override
		public View findViewById(int id) {
			return view.findViewById(id);
		}
	}

	public static int intValOfClz(Class<?> resclz, String name) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		// Field f = resclz.getDeclaredField(name);
		Field f = resclz.getField(name);
		return f.getInt(resclz);
	}

	public static int injectViews(Activity activity, Class<?> resclz) {
		return injectViews(activity, resclz, getUtils().new ActivityViewGetter(activity));
	}
	
	public static int injectViews(Object o, Class<?> resclz, View rootView) {
		return injectViews(o, resclz, getUtils().new ViewViewGetter(rootView));
	}

	public static int injectViews(Object o, Class<?> resclz, ViewGetter viewGetter) {
		int count = 0;
		Class<?> clz = o.getClass();
		Class<?> viewclz = View.class;
		for (Field f : clz.getDeclaredFields()) {
			System.err.println(f.getName() + " " + f.getType());
			if (viewclz.isAssignableFrom(f.getType())) {
				if (f.getAnnotation(Ignore.class) == null) {
					try {
						int id = intValOfClz(resclz, f.getName());
						View view = viewGetter.findViewById(id);
						f.setAccessible(true);
						f.set(o, view);
						// System.out.println(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return count;
	}
}
