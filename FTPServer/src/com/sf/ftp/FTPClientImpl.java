package com.sf.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by xieningtao on 16-3-31.
 */
public class FTPClientImpl extends FTPBaseImpl {

    private final int mSocketConnectTimeOut = 3000;
    private final int TIME_GAP = 5 * 1000;
    private long preTime = 0;

    public void connect() {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(mAddr, mPort), mSocketConnectTimeOut);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();


        } catch (SocketTimeoutException timeout) {
            timeout.printStackTrace();
            LogUtil.error(TAG, "method->connect,SocketTimeoutException,reason: " + timeout.getMessage());
            closeSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->connect,IOException,reason: " + e.getMessage());
            closeSocket(socket);
        }


        transferDataFromClient(socket);
    }

    private void transferDataFromClient(Socket socket) {
        InputStream is = null;
        OutputStream os = null;

        int i = 0;
        while (i < 100) {
            i++;
            LogUtil.infor(TAG, "i: " + i);
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();

                boolean heartBeatSender = sendHeartBeat(os);

                LogUtil.infor(TAG, "method->transferDataFromClient,heartBeatSender: " + heartBeatSender);
                if (!heartBeatSender) {
                    closeStream(is, os);
                    return;
                }
                boolean heartBeatReceiveAck = receiveHeartbeatAck(is);

                LogUtil.infor(TAG, "method->transferDataFromClient,heartBeatReceiveAck: " + heartBeatReceiveAck);
                if (!heartBeatReceiveAck) {
                    closeStream(is, os);
                    return;
                }

                byte content[] = "this is my content from client".getBytes("utf-8");
                boolean contentSender = sendContent(os, content);

                LogUtil.infor(TAG, "method->transferDataFromClient,contentSender: " + contentSender);
                if (!contentSender) {
                    closeStream(is, os);
                    return;
                }
                boolean contentReceiveAck = receiveContentAck(is);

                LogUtil.infor(TAG, "method->transferDataFromClient,contentReceiveAck: " + contentReceiveAck);
                if (!contentReceiveAck) {
                    closeStream(is, os);
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error(TAG, "is or os stream is error,reason: " + e.getMessage());
                closeStream(is, os);
                return;
            }
        }
        closeStream(is, os);
    }


    private boolean isInTimeGap() {
        long curTime = System.currentTimeMillis();
        long detal = curTime - preTime;
        if (detal < TIME_GAP) {
            return true;
        } else {
            preTime = curTime;
            return false;
        }
    }
}
