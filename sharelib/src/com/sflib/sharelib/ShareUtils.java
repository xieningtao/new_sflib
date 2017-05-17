package com.sflib.sharelib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;

import com.sf.loglib.L;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by NetEase on 2017/5/17 0017.
 */

public class ShareUtils {
    private final static String TAG = ShareUtils.class.getSimpleName();

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientInstalled(Context context) {
        return isAppInstalled(context, "com.tencent.mobileqq");
    }

    public static boolean isYiXinInstalled(Context context) {
        return isAppInstalled(context, "im.yixin");
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static byte[] doBitmapCompress(Bitmap bmp, final int maxLength) {
        return doBitmapCompress(bmp, 1.5f, maxLength);
    }

    public static byte[] doBitmapCompress(Bitmap bmp, float ratio, final int maxLength) {
        int with = (int) (bmp.getWidth() / ratio);
        int height = (int) (bmp.getHeight() / ratio);
        int widthGap = bmp.getWidth() - with;
        int heightGap = bmp.getHeight() - height;
        int left = widthGap / 2;
        int top = heightGap / 2;

        Bitmap resultBitmap = Bitmap.createBitmap(with, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        Rect rect = new Rect(left, top, with + left, height + top);
        canvas.drawBitmap(bmp, rect, new Rect(0, 0, with, height), null);
        //压缩到指定质量
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        byte[] result = output.toByteArray();
        int percentage = maxLength * 100 / result.length;
        while (result.length > maxLength) {
            output.reset();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, percentage, output);
            result = output.toByteArray();
            if (result.length <= maxLength) {
                break;
            } else {
                percentage -= 10;
                if (percentage < 0) {
                    percentage = 0;
                }
            }
        }
        try {
            output.close();
        } catch (Exception e) {
            L.error(TAG, e.getMessage());
        }

        return result;
    }
}
