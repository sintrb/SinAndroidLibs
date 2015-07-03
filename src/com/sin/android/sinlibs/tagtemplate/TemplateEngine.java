package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {
	static private final String TMPLEPATTERN = "\\{([^}]*)\\}";
	private ExpressionEngine expressionEngine = new ExpressionEngine();
	private Pattern tmplPattern = null;

	public TemplateEngine() {
		super();
		initEngine();
	}

	public TemplateEngine(String tmplStart, String tmplEnd) {
		super();
		initEngine();
	}

	public TemplateEngine(ExpressionEngine expressionEngine) {
		super();
		this.expressionEngine = expressionEngine;
	}

	private void initEngine() {
		tmplPattern = Pattern.compile(TMPLEPATTERN);
	}

	public String evalString(Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Matcher m = tmplPattern.matcher(tmpl);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "" + expressionEngine.eval(model, m.group(1).trim()));
		}

		return sb.toString();
	}
}