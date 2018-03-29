package com.sin.android.sinlibs.tagtemplate.test;

//import com.haulwin.hyapp.utils.StrUtils;
import com.sin.android.sinlibs.tagtemplate.ExpressionEngine;
import com.sin.android.sinlibs.tagtemplate.TemplateEngine;

public class TestMain {
//	public static void main(String[] args) {
//		ExpressionEngine ee = new ExpressionEngine() {
//			@Override
//			public Object evalFilter(Object model, String filter) {
//				try {
//					if ("smallimage".equals(filter)) {
//						String val = (String) model;
//						model = StrUtils.getImgUrl(val, "small");
//					} else if ("autoempty".equals(filter)) {
//						if (model == null ||
//								(model instanceof String && StrUtils.isNullOrEmpty((String) model)) ||
//								(model instanceof Boolean && (Boolean) model == false) ||
//								(model instanceof Float && (Float) model == 0) ||
//								(model instanceof Integer && (Integer) model == 0) ||
//								(model instanceof Long && (Long) model == 0)) {
//							model = null;
//						}
//					} else {
//						model = super.evalFilter(model, filter);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return model;
//			}
//		};
//		TemplateEngine te = new TemplateEngine();
//		Model m = new Model();
//		m.age=0;
//		try {
//			System.out.println(ee.eval(m, ".name"));
//
//			System.out.println(ee.eval(m, ".a:name"));
//			System.out.println(ee.eval(m, ".age.f:autoempty"));
//			System.out.println(ee.eval(m, ".m:toString"));
//
//			System.out.println(te.evalString(m, "姓名{name} 姓名{age} To{.m:toString}"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
