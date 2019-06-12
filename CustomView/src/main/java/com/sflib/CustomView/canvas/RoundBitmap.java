package com.sflib.CustomView.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;


/**
 * Created by xieningtao on 15-12-18.
 */
public class RoundBitmap {

    public static Bitmap createRoundBitmap(Bitmap bitmap, int roundx, int roundy) {
        if (bitmap == null) {
            return bitmap;
        }
        int bitmap_w = bitmap.getWidth();
        int bitmap_h = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect src = new Rect(0, 0, bitmap_w, bitmap_h);
        RectF dest = new RectF(0, 0, bitmap_w, bitmap_h);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(dest, roundx, roundy, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dest, paint);

        return output;
    }

    public static Bitmap createRoundBitmap(Bitmap bitmap, int top_left, int top_right, int bottom_left, int bottom_right) {
        if (bitmap == null) {
            return bitmap;
        }
        int bitmap_w = bitmap.getWidth();
        int bitmap_h = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect src = new Rect(0, 0, bitmap_w, bitmap_h);
        RectF dest = new RectF(0, 0, bitmap_w, bitmap_h);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int radio = top_left;
        canvas.drawRoundRect(dest, radio, radio, paint);


        float square_left = dest.left;
        float square_right = dest.left + dest.width();
        float square_top = dest.top;
        float square_bottom = dest.top + dest.height();

        RectF square = new RectF();
        if (top_left == 0) {
            square.set(square_left, square_top, square_left + radio, square_top + radio);
            canvas.drawRect(square, paint);
        }

        if (top_right == 0) {
            square.set(square_right - radio, square_top, square_right, square_top + radio);
            canvas.drawRect(square, paint);
        }

        if (bottom_left == 0) {
            square.set(square_left, square_bottom - radio, square_left + radio, square_bottom);
            canvas.drawRect(square, paint);
        }

        if (bottom_right == 0) {
            square.set(square_right - radio, square_bottom - radio, square_right, square_bottom);
            canvas.drawRect(square, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, src, dest, paint);

        return output;
    }

    //生成圆角图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 14;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
