package com.sf.ftp;

/**
 * Created by xieningtao on 16-3-31.
 */
public class FTPClient {
    private static String TAG = FTPClient.class.getName();

    public static void main(String[] args) {
        FTPClientImpl ftpClient = new FTPClientImpl();
        ftpClient.connect();
    }
}
