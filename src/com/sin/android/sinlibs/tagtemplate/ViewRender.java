package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.InvocationTargetException;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewRender {
	public TemplateEngine templateEngine = new TemplateEngine();
	private String templateTagStart = "tt:";

	public ViewRender() {
		super();
	}

	public ViewRender(TemplateEngine templateEngine) {
		super();
		this.templateEngine = templateEngine;
	}

	public ViewRender(String templateTagStart) {
		super();
		this.templateTagStart = templateTagStart;
	}

	public ViewRender(TemplateEngine templateEngine, String templateTagStart) {
		super();
		this.templateEngine = templateEngine;
		this.templateTagStart = templateTagStart;
	}

	public void renderView(View view, Object model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) view;
			int count = vg.getChildCount();
			for (int i = 0; i < count; ++i) {
				renderView(vg.getChildAt(i), model);
			}
		} else {
			Object tag = view.getTag();
			if (tag instanceof String) {
				String stag = (String) tag;
				if (stag.startsWith(templateTagStart)) {
					renderView(view, model, stag.substring(templateTagStart.length()));
				}
			}
		}
	}

	public void renderView(View view, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (view instanceof TextView) {
			renderTextView((TextView) view, model, tmpl);
		}
	}

	public void renderTextView(TextView tv, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		tv.setText(templateEngine.evalString(model, tmpl));
	}
}
