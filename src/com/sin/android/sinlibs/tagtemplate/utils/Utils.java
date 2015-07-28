package com.sin.android.sinlibs.tagtemplate.utils;

import com.sin.android.sinlibs.tagtemplate.ViewRender;

import android.view.View;

public class Utils {

	private static ViewRender render = null;

	/***
	 * 
	 * @param view
	 *            The view which will be rendered
	 * @param model
	 *            The data object
	 * 
	 * <br/>
	 *            TextView Tag:<br/>
	 *            {.attribute-name}<br/>
	 *            {.a:attribute-name}<br/>
	 *            {.m:method-name}
	 * @deprecated
	 * @see ViewRender
	 */
	public static void renderView(View view, Object model) {
		if (render == null)
			render = new ViewRender();
		render.renderView(view, model);
	}

	// public static Object evalueTemplate(Object model, String tmpl){
	// {tmpl}
	// tmpl:
	// .m:xx.xx
	// }
}
