package com.sf.utils.baseutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.sf.loglib.L;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类，包括 1、通过不同的with和height来进行图片展示 2、压缩图片 3、保存图片到相应的文件
 * 
 * @author xieningtao
 * 
 */
public class BitmapHelp {

	public final static String TAG=BitmapHelp.class.getName();
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 *            目标图片的宽度 像素为单位
	 * @param reqHeight
	 *            目标图片的高度 像素为单位
	 * @return 缩放的比例
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		if (options == null) {
			throw new NullPointerException("option is null");
		}
		if (reqHeight < 0 || reqWidth < 0) {
			throw new IllegalArgumentException(
					"height or width is less than 0. reqHeight: " + reqHeight
							+ " reqWidth: " + reqWidth);
		}
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 根据目标图片的高宽，返回bitmap用于显示
	 * 
	 * @param filePath
	 *            图片的全路径
	 * @param reqWidth
	 *            目标图片的宽度 像素为单位
	 * @param reqHeight
	 *            目标图片的高度 像素为单位
	 * @return 返回bitmap
	 */
	public static Bitmap decodeFileInSize(String filePath, int reqWidth,
			int reqHeight) {
		if (TextUtils.isEmpty(filePath)) {
			throw new IllegalAccessError("filePath is illegal");
		}

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}


	/**
	 * 把bitmap进行压缩，并进行base64编码以后 转换成String
	 * 
	 * @param bitmap
	 * @param percenthise
	 *            范围为0-100
	 * @return 如果bitmap为null,返回值为""
	 */
	public static String bitmapToString(Bitmap bitmap, int percenthise) {
		if (bitmap == null)
			return "";
		byte[] b = compress(bitmap, percenthise);
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 压缩图片
	 * 
	 * @param bitmap
	 * @param persenthise
	 *            范围为0-100
	 * @return
	 */
	public static byte[] compress(Bitmap bitmap, int persenthise) {
		if (bitmap == null)
			return new byte[0];
		if (persenthise <= 0)
			persenthise = 0;
		else if (persenthise > 100)
			persenthise = 100;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, persenthise, baos);
		return baos.toByteArray();
	}

	/**
	 * 通过根据时间格式yyyymmddhhmmss_随机数的形式命名图片的名字
	 * 
	 * @param bitmap
	 *            图片
	 * @param path
	 *            保存图片的 图片的路径
	 * @return
	 */
	public static String saveBitmap(Bitmap bitmap, String path) {
		byte content[] = compress(bitmap, 100);
		return saveInFile(path, content);
	}

	/**
	 * 把图片保存在文件里面，名字的形式为yyyymmddhhmmss_随机数，随机数的位数为5位
	 * 
	 * @param path
	 *            图片的路径
	 * @param bos
	 * @return
	 */
	private static String saveInFile(final String path, byte[] bos) {
		if (TextUtils.isEmpty(path))
			return "";
		if (bos == null || bos.length == 0)
			return "";
		File file = new File(path);
		if (file.exists()) {
			L.error(TAG, "重命名图片");
			return "";
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				L.error(TAG, "创建文件失败，reason: " + e.getMessage());
				return "";
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(bos);
			fos.flush();
			fos.close();
			return file.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			L.error(TAG, "文件不存在  reason: " + e.toString());
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			L.error(TAG, "文件读写出错 reason: " + e.toString());
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				L.error(TAG, "文件关闭失败 reason: " + e.toString());
			}
			return "";
		}
	}


	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
