package com.sin.android.sinlibs.tagtemplate;

import java.lang.reflect.InvocationTargetException;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; ++i) {
                renderView(vg.getChildAt(i), model);
            }
        }
    }

    public void renderView(View view, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (view instanceof TextView) {
            renderTextView((TextView) view, model, tmpl);
        } else if (view instanceof EditText) {
            renderEditText((EditText) view, model, tmpl);
        } else if (view instanceof ImageView) {
            renderImageView((ImageView) view, model, tmpl);
        } else if (view instanceof Button) {
            renderButton((Button) view, model, tmpl);
        } else {
//            Log.e("ViewRender", "Unknow how to render " + view + " with data" + model + " tmpl: " + tmpl);
        }
    }

    public void renderTextView(TextView tv, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        tv.setText(templateEngine.evalString(model, tmpl));
    }

    public void renderEditText(EditText et, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        et.setText(templateEngine.evalString(model, tmpl));
    }

    public void renderImageView(ImageView iv, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        iv.setImageURI(Uri.parse(templateEngine.evalString(model, tmpl)));
    }

    public void renderButton(Button btn, Object model, String tmpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        btn.setText(templateEngine.evalString(model, tmpl));
    }
}
