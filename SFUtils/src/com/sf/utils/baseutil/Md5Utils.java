package com.sf.utils.baseutil;

import com.sf.loglib.L;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class Md5Utils {
    private final static String TAG = Md5Utils.class.getName();
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * @param val
     * @return "" if fail to get MD5
     */
    public static String getMD5(String val) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密
            return byteToString(m);
        } catch (NoSuchAlgorithmException e) {
            L.error(TAG, "getMD5 exception: " + e);
        }
        return "";
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

}
