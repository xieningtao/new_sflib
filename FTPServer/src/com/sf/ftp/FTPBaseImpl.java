package com.sf.ftp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by xieningtao on 16-3-31.
 */
public class FTPBaseImpl {
    public final int mPort = 8090;
    public final String mAddr = "localhost";
    protected final String TAG = getClass().getName();

    public final String HEARTBIT_FLAG = "heartBit";
    public final String HEARTBIT_FLAG_SENDER = "heartBit_sender";
    public final String HEARTBIT_FLAG_ACK = "heartBit_ack";
    public final String CONTENT_FLAG = "content";
    public final String CONTENT_FLAG_SENDER = "content_sender";
    public final String CONTENT_FLAG_ACK = "content_ack";

    public final int RECEIVE_ERROR = -1;
    public final int RECEIVE_HEARTBEAT = 0;
    public final int RECERVE_CONTENT = 1;


    public byte[] decimal2Binary(int decimal) {
        byte _byte[] = new byte[4];
        for (int i = 0; i < _byte.length; i++) {
            _byte[0] = 0;
        }
        _byte[3] = (byte) decimal;

        int d_8 = decimal >> 8;

        _byte[2] = (byte) d_8;

        int d_16 = decimal >> 16;
        _byte[1] = (byte) d_16;
        int d_31 = decimal >> 24;

        _byte[0] = (byte) d_31;


        return _byte;
    }

    public int binary2Decimal(byte _byte[]) {
        if (_byte == null) return 0;
        int d_0 = (_byte[3] & 0x000000ff);

        int d_8 = ((_byte[2] << 8) & 0x0000ff00);

        int d_16 = ((_byte[1] << 16) & 0x00ff0000);

        int d_31 = ((_byte[0] << 24) & 0xff000000);

        System.out.println("d_0: " + d_0 + " d_8: " + d_8 + " d_16: " + d_16 + " d_31: " + d_31);

        return d_31 + d_16 + d_8 + d_0;

    }

    protected boolean receiveContentAck(InputStream inputStream) {

        return receiveAck(inputStream, CONTENT_FLAG, CONTENT_FLAG_ACK);
    }

    protected boolean receiveHeartbeatAck(InputStream inputStream) {
        return receiveAck(inputStream, HEARTBIT_FLAG, HEARTBIT_FLAG_ACK);
    }

    protected int receiveContent(InputStream inputStream) {
        try {
            String flagContent = getFlag(inputStream);
            String content = handleContent(inputStream);
            LogUtil.infor(TAG, "method->receiveContent,content: " + content);
            if (CONTENT_FLAG.equals(flagContent)) {//mark it as content
                return RECERVE_CONTENT;
            } else if (HEARTBIT_FLAG.equals(flagContent)) {//mark is as heartbeat
                return RECEIVE_HEARTBEAT;
            } else {
                LogUtil.error(TAG, "method->receiveContent, it is an error flagContent. flagContent: " + flagContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->receiveContent, IOException: " + e.getMessage());
        }
        return RECEIVE_ERROR;
    }

    protected boolean receiveHeartbeat(InputStream inputStream) {
        return receiveAck(inputStream, HEARTBIT_FLAG, HEARTBIT_FLAG_SENDER);
    }

    private String getFlag(InputStream inputStream) throws IOException {
        byte flagLengthByte[] = new byte[4];
        inputStream.read(flagLengthByte);
        int flagLength = binary2Decimal(flagLengthByte);

        byte flagContent[] = new byte[flagLength];
        inputStream.read(flagContent);

        String receiveFlag = new String(flagContent, "utf-8");
        LogUtil.infor(TAG, "method->getFlag,flagLength: " + flagLength + " receiveFlag:"
                + receiveFlag);

        if (receiveFlag != null) {
            String parts[] = receiveFlag.split("=");
            if (parts != null && parts.length == 2) {
                return parts[1];
            }
        }
        return "";

    }

    private boolean receiveAck(InputStream inputStream, String flag, String maskAck) {
        if (flag == null) return false;
        byte flagLengthByte[] = new byte[4];
        try {
            inputStream.read(flagLengthByte);
            int flagLength = binary2Decimal(flagLengthByte);

            byte flagContent[] = new byte[flagLength];
            inputStream.read(flagContent);

            String receiveFlag = new String(flagContent, "utf-8");
            LogUtil.infor(TAG, "method->receiveAck,flagLength: " + flagLength + " receiveFlag:"
                    + receiveFlag);
            if (receiveFlag != null) {
                String parts[] = receiveFlag.split("=");
                if (parts != null && parts.length == 2) {
                    if (flag.equals(parts[1])) {

                        String content = handleContent(inputStream);
                        LogUtil.infor(TAG, "method->receiveAck,content: " + content + " maskAck: " + maskAck);
                        if (flag.equals(HEARTBIT_FLAG)) {
                            if (content.equals(maskAck)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return true;
                        }

                    } else {
                        LogUtil.error(TAG, "method->receiveAck," + parts[1] + " is not equal to " + flag);
                    }
                } else {
                    LogUtil.error(TAG, "method->receiveAck,parts is null or parts.length is not 2");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->receiveAck,IOException,reason: " + e.getMessage());

        }

        return false;
    }

    private String handleContent(InputStream inputStream) throws IOException {
        byte contentLengthByte[] = new byte[4];
        inputStream.read(contentLengthByte);

        int contentLength = binary2Decimal(contentLengthByte);
        byte contentByte[] = new byte[contentLength];
        int realReadLength = inputStream.read(contentByte);

        String content_str = new String(contentByte, "utf-8");
        LogUtil.infor(TAG, "method->handleContent, contentLength: " + contentLength + " realReadLength: " + realReadLength + " content_str: " + content_str);
        return content_str;
    }

    protected void closeStream(InputStream is, OutputStream os) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                LogUtil.error(TAG, "fail to close is stream,reason: " + e1.getMessage());
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                LogUtil.error(TAG, "fail to close os stream,reason: " + e1.getMessage());
            }
        }
    }

    protected boolean sendHeartBeat(OutputStream outputStream) {
        byte content[] = null;
        try {
            content = HEARTBIT_FLAG_SENDER.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->sendHeartBite,UnsupportedEncodingException,reason: " + e.getMessage());
            return false;
        }
        return sendBytes(outputStream, content, HEARTBIT_FLAG, 0);
    }

    protected boolean sendHeartBeatAck(OutputStream outputStream) {
        byte content[] = null;
        try {
            content = HEARTBIT_FLAG_ACK.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->sendHeartBite,UnsupportedEncodingException,reason: " + e.getMessage());
            return false;
        }
        return sendBytes(outputStream, content, HEARTBIT_FLAG, 0);
    }

    protected boolean sendContent(OutputStream outputStream, byte realContent[]) {
        return sendBytes(outputStream, realContent, CONTENT_FLAG, 0);
    }

    protected boolean sendContentAck(OutputStream outputStream) {
        byte realContent[] = null;
        try {
            realContent = CONTENT_FLAG_ACK.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->sendContentAck,reason: " + e.getMessage());
            return false;
        }
        return sendBytes(outputStream, realContent, CONTENT_FLAG, 100);
    }

    private boolean sendBytes(OutputStream outputStream, byte realContent[], String flag, int extraLength) {
        if (outputStream == null || realContent == null || realContent.length == 0 || flag == null)
            return false;

        byte flagContent[] = null;
        try {
            flagContent = getFlagBytes(flag);
            byte flagLengthByte[] = decimal2Binary(flagContent.length);
            byte contentLengthByte[] = decimal2Binary(realContent.length + extraLength);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(flagLengthByte);
            bos.write(flagContent);
            bos.write(contentLengthByte);
            bos.write(realContent);

            outputStream.write(bos.toByteArray());
            outputStream.flush();
            bos.close();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->sendBytes,UnsupportedEncodingException,reason: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(TAG, "method->sendBytes,IOException reason: " + e.getMessage());
        }

        return false;
    }

    private byte[] getFlagBytes(String flag) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append("flag=").append(flag);
        String str = builder.toString();
        return str.getBytes("utf-8");
    }

    protected void closeSocket(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                LogUtil.error(TAG, "fail to close socket,reason: " + e1.getMessage());
            }
        }
    }
}
