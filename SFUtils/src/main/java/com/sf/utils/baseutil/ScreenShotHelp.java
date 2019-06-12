package com.sf.utils.baseutil;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.sf.loglib.file.SFFileCreationUtil;

/**
 * screenshot
 * @author xieningtao
 *
 */
public class ScreenShotHelp {

	public static void screenShot(Activity activity, int w, int h) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
//		Rect frame = new Rect();
//		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight =SystemUIWHHelp.getStatusBarHeight(activity);
		Log.i("TAG", "" + statusBarHeight);

		// 获取屏幕长和高
		int width = SystemUIWHHelp.getScreenRealWidth(activity);
		int height = SystemUIWHHelp.getScreenRealHeight(activity);
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		BitmapHelp.saveBitmap(b, SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT, ".png").getAbsolutePath());
		SFToast.showToast( "截屏成功");
	}

	public static void screenScrollViewShot(Activity activity, View view,
			int w, int h) {
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		BitmapHelp.saveBitmap(bitmap, SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT, ".png").getAbsolutePath());
		SFToast.showToast("截屏成功");
	}

	public static String screenScrollViewShot(Activity activity, View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		String path = BitmapHelp.saveBitmap(bitmap, SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT, ".png").getAbsolutePath());
		if (TextUtils.isEmpty(path)) {
			SFToast.showToast( "截屏失败");
			return "";
		} else {
			SFToast.showToast( "截屏成功");
		}
		return path;
	}
}
