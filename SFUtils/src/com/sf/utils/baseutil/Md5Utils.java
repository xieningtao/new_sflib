package com.sf.utils.baseutil;

import com.sf.loglib.L;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class Md5Utils {
    private final static String TAG = Md5Utils.class.getName();

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
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            L.error(TAG, "getMD5 exception: " + e);
        }
        return "";
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }
}
