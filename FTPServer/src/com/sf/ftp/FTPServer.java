package com.sf.ftp;


/**
 * Created by xieningtao on 16-3-29.
 */
public class FTPServer {
    private static String TAG = FTPServer.class.getName();

    public static void main(String[] args) {
        FTPServerImpl ftpServer = new FTPServerImpl();
        ftpServer.connect();

    }


}
