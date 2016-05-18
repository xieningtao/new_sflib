package com.sf.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by xieningtao on 16-3-29.
 */
public class FTPServerImpl extends FTPBaseImpl {


    private final int mServerSocketTimeOut = 3000 * 30;

    public void connect() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(mAddr, mPort));
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "fail to create or bind serverSocket,reason: " + e.getMessage());
            closeServerSocket(serverSocket);
            return;
        }

        try {
            serverSocket.setSoTimeout(mServerSocketTimeOut);
            socket = serverSocket.accept();
        } catch (SocketTimeoutException exception) {
            exception.printStackTrace();
            LogUtil.error(TAG, "serverSocket is timeout,reason: " + exception.getMessage());
            closeSocket(socket);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "fail to accept serverSocket,reason: " + e.getMessage());
            closeSocket(socket);
            return;
        }

        transferDataFromServer(socket);

        closeSocket(socket);
    }

    private void transferDataFromServer(Socket socket) {
        InputStream is = null;
        OutputStream os = null;
        int i=0;
        while (i<100) {
            LogUtil.infor(TAG, "i: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    is = socket.getInputStream();
                    os = socket.getOutputStream();

                    int contentType = receiveContent(is);
                    LogUtil.infor(TAG, "method->transferDataFromServer,contentType: " + contentType);
                    if (contentType == RECEIVE_ERROR) {
                        closeStream(is, os);
                        return;
                    } else if (contentType == RECERVE_CONTENT) {
                        boolean contentAck = sendContentAck(os);
                        LogUtil.infor(TAG, "method->transferDataFromServer,contentSender: " + contentAck);
                        if (!contentAck) {
                            closeStream(is, os);
                            return;
                        }
                        break;
                    } else if (contentType == RECEIVE_HEARTBEAT) {
                        boolean heartBeatSender = sendHeartBeatAck(os);
                        LogUtil.infor(TAG, "method->transferDataFromServer,heartBeatSender: " + heartBeatSender);
                        if (!heartBeatSender) {
                            closeStream(is, os);
                            return;
                        }
                    } else {
                        LogUtil.error(TAG, "method->transferDataFromServer, it is an error contentType");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.error(TAG, "is or os stream is error,reason: " + e.getMessage());
                    closeStream(is, os);
                    return;
                }
            }
        }
        closeStream(is, os);
        LogUtil.infor(TAG, "method->transferDataFromServer,finish transport data!!!!!!!!!");
    }

    private void closeServerSocket(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                LogUtil.error(TAG, "fail to close serverSocket,reason: " + e1.getMessage());
            }
        }
    }


}
