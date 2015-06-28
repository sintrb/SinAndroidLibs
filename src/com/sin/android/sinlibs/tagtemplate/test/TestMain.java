package com.sin.android.sinlibs.tagtemplate.test;

import com.sin.android.sinlibs.tagtemplate.ExpressionEngine;
import com.sin.android.sinlibs.tagtemplate.TemplateEngine;

public class TestMain {
	public static void main(String[] args) {
		ExpressionEngine ee = new ExpressionEngine();
		TemplateEngine te = new TemplateEngine();
		Model m = new Model();
		try {
			System.out.println(ee.eval(m, ".name"));

			System.out.println(ee.eval(m, ".a:name"));
			System.out.println(ee.eval(m, ".age"));
			System.out.println(ee.eval(m, ".m:toString"));

			System.out.println(te.evalString(m, "姓名{name} 姓名{age} To{.m:toString}"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
