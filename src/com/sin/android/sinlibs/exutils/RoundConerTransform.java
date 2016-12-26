package com.sin.android.sinlibs.exutils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

public class RoundConerTransform implements Transformation {
    private float mRadius = 0;

    public RoundConerTransform(float mRadius) {
        this.mRadius = mRadius;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        if (target != source) {
            source.recycle();
        }
        return target;
    }

    @Override
    public String key() {
        return "roundconer";
    }
}
