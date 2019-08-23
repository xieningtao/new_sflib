package com.sf.utils.baseutil;

import android.content.Context;
import android.os.Environment;

/**
 * 对外部存储卡一些情况的检测
 * @author xieningtao
 *
 */
public class StorageStateCheckUtil {

	/**
	 * 是否有外部存储，没有外部存储，无法使用拍照功能
	 * @param context
	 */
	public static void sdCardCheck(Context context){
		boolean isExtenalOk=isExternalStorageReadable()||isExternalStorageWritable();
		if(!isExtenalOk){
			SFToast.showToast( "检测不到SD卡，无法使用拍照功能");
		}
	}
	
	/**
	 * 检测外部存储是否为可读，可写
	 * @return
	 */
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

	/**
	 * 检测外部存储是否可读
	 * @return
	 */
	public static boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
