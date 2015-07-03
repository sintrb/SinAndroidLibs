package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.InvocationTargetException;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

	public void renderView(View view, Object model) {
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
					String tmpl = stag.substring(templateTagStart.length());
					try {
						renderView(view, model, tmpl);
					} catch (Exception e) {
						Log.e("TT", "Error, Model:" + model + " Tmpl:" + tmpl);
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void renderView(View view, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if (view instanceof TextView) {
			renderTextView((TextView) view, model, tmpl);
		} else if (view instanceof EditText) {
			renderEditText((EditText) view, model, tmpl);
		}
	}

	public void renderTextView(TextView tv, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		tv.setText(templateEngine.evalString(model, tmpl));
	}

	public void renderEditText(EditText et, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		et.setText(templateEngine.evalString(model, tmpl));
	}
	
	public void renderImageView(ImageView iv, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//		et.setText(templateEngine.evalString(model, tmpl));
	}
}
