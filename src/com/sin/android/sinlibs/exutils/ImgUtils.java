package com.sin.android.sinlibs.exutils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImgUtils {
	public static void loadImage(Context context, ImageView iv, String url) {
		loadImage(context, iv, url, 0, 0);
	}

	public static void loadImage(Context context, ImageView iv, String url, int defimg, int errimg) {
		if (url != null && url.length() > 0) {
			RequestCreator rc = Picasso.with(context).load(url);
			if (defimg > 0)
				rc = rc.placeholder(defimg);
			if (errimg > 0)
				rc = rc.error(errimg);
			rc.into(iv);
		}
	}
}
