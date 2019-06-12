package com.sflib.openGL.tan;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;

import com.basesmartframe.baseapp.BaseApp;
import com.sf.utils.baseutil.UnitHelp;

/**
 * Created by xieningtao on 16-3-27.
 */
public class Word2BitmapUtil {

    /**
     * @param str
     * @return null if str is empty or null
     */
    public static Bitmap word2Bitmap(String str,int color) {
        if (TextUtils.isEmpty(str)) return null;
        Paint paint = new Paint();
        paint.setTextSize(UnitHelp.sp2px(BaseApp.gContext, 32));
        paint.setColor(color);
        Rect rect = new Rect();
        int length = str.length();
//        paint.getTextBounds(str, 0, length, rect);

        int width = (int) Math.ceil(paint.measureText(str));
        int height = (int) Math.ceil(paint.descent()-paint.ascent());
        Log.i("Word2BitmapUtil", "method->word2Bitmap width: " + width+" height: "+height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(str, 0, Math.abs(paint.ascent()), paint);

        return bitmap;
    }

}
