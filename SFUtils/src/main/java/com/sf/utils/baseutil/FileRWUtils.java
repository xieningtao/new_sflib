package com.sf.utils.baseutil;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.sf.loglib.L;
import com.sf.loglib.StringUtils;
import com.sf.loglib.file.SFFileHelp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import java.nio.channels.ServerSocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

/**
 * 文件读写效率对比，主要是对写文件进行对比
 * 1、标准io
 * 2、NIO
 * 3、内存映射
 * 对比数据
 * standard write size: 1536000,duration: 5595
 * nio write size: 1536000,duration: 2809
 * mmap write size: 1536000,duration: 592
 */
public class FileRWUtils {
    private static final String TAG = "FileRWUtils";
    //    private static int count = 500 * 1024;
    private static int count = 3;

    public interface WriteFileAbs {
        void writeToFile(String filePath, String content);
    }

    public static long writeFileByBufferStandard(String filePath, String content) {
        if (SFFileHelp.isSDCardMounted()) {
            if (StringUtils.isNullOrEmpty(filePath) || StringUtils.isNullOrEmpty(content)) {
                return -1;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    L.error(TAG, "create file fail, e = " + e);
                    return -1;
                }
            }
            long curSize = 0;
            if (file.isFile()) {
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);
                    if (ostream != null) {

                        //跟nio差不多
                        BufferedOutputStream bufferedOutputStream =
                                new BufferedOutputStream(ostream);
//                        OutputStreamWriter outputWriter = new OutputStreamWriter(
//                                bufferedOutputStream);
//                        PrintWriter writer = new PrintWriter(outputWriter);
                        //比标准io还慢
//                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        //循环写
                        for (int i = 0; i < count; i++) {
                            byte curContent[] = content.getBytes("utf-8");
                            curSize += curContent.length;
                            bufferedOutputStream.write(curContent);
                            bufferedOutputStream.flush();
//                            writer.write(content);
//                            writer.flush();
                        }
//                        bufferedOutputStream.close();
                    }
                } catch (Exception e) {
                    L.error(TAG, "read fail, e = " + e);
                    return -1;
                } finally {
//                    if (ostream != null) {
//                        try {
//                            ostream.close();
//                        } catch (Exception e) {
//                            L.error(TAG, " fail to close, e = " + e);
//                        }
//                    }
                }
            }
            return curSize;
        } else {
            return -1;
        }
    }

    public static long writeFileByStandard(String filePath, String content) {
        if (SFFileHelp.isSDCardMounted()) {
            if (StringUtils.isNullOrEmpty(filePath) || StringUtils.isNullOrEmpty(content)) {
                return -1;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    L.error(TAG, "create file fail, e = " + e);
                    return -1;
                }
            }
            long curSize = 0;
            if (file.isFile()) {
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);
                    if (ostream != null) {
                        OutputStreamWriter outputWriter = new OutputStreamWriter(
                                ostream);
//                        PrintWriter writer = new PrintWriter(outputWriter);
                        //循环写
                        for (int i = 0; i < count; i++) {
                            byte curContent[] = content.getBytes("utf-8");
                            curSize += curContent.length;
                            outputWriter.write(content);
                            outputWriter.flush();
                        }
//                        outputWriter.close();
                    }
                } catch (Exception e) {
                    L.error(TAG, "read fail, e = " + e);
                    return -1;
                } finally {
//                    if (ostream != null) {
//                        try {
//                            ostream.close();
//                        } catch (Exception e) {
//                            L.error(TAG, " fail to close, e = " + e);
//                        }
//                    }
                }
            }
            return curSize;
        } else {
            return -1;
        }
    }

    public static String readFileByNio(String filePath) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        //写文件通道
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        FileChannel wChannel = fileInputStream.getChannel();

        //读文件通道
//        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
//        FileChannel rChannel = fileInputStream.getChannel();

        ByteBuffer byteBufferRead = ByteBuffer.allocate(1024);//从堆中分配缓冲区
        long curSize = 0;
        byteBufferRead.clear();
        StringBuilder finalContent = new StringBuilder();
        byte contentByte[] = new byte[1024];
        int curPos = 0;
        while ((curSize = wChannel.read(byteBufferRead)) != -1) {
            Log.i(TAG, "readFileByNio curSize: " + curSize);
            byteBufferRead.flip();//写到读取
            curPos = 0;
            while (byteBufferRead.hasRemaining()) {
                byte content = byteBufferRead.get();
                if (content != 0) {
                    contentByte[curPos] = content;
                    curPos++;
                } else {
                    if (curPos > 0) {
                        String decodeContent = new String(contentByte, 0, curPos, "utf-8");
                        finalContent.append(decodeContent);
                    }
                    break;
                }

            }
            byteBufferRead.compact();
        }
        Log.i(TAG, "readFileByNio curSize: " + curSize + " finalContent: " + finalContent);
        wChannel.close();
        return finalContent.toString();
    }

    public static long writeFileByNio(String filePath, String content) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            return -1;
        }
        //写文件通道
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        FileChannel wChannel = fileOutputStream.getChannel();

        //读文件通道
//        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
//        FileChannel rChannel = fileInputStream.getChannel();

        ByteBuffer byteBufferRead = ByteBuffer.allocate(1024);//从堆中分配缓冲区
        long curSize = 0;
        byteBufferRead.clear();
        for (int i = 0; i < count; i++) {
            byte curContent[] = content.getBytes("utf-8");
            curSize += curContent.length;
            byteBufferRead.put(curContent);
            byteBufferRead.flip();
            while (byteBufferRead.hasRemaining()) {
                wChannel.write(byteBufferRead);
            }
            byteBufferRead.compact();
        }
//        wChannel.close();
        return curSize;
    }

    public static String readFileByMMap(String filePath) throws IOException {
        File file = new File(filePath);
        Log.i(TAG, "readFileByMMap fileSize: " + file.length());
//        FileOutputStream outputStream = new FileOutputStream(file);
//        FileChannel wChannel = outputStream.getChannel();

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel wChannel = randomAccessFile.getChannel();

        Log.i(TAG, "readFileByMMap fileSize: " + file.length());
        int size = (int) file.length();
        MappedByteBuffer buffer = wChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        StringBuilder builder = new StringBuilder();
        byte contentByte[] = new byte[100];

//        while (buffer.hasRemaining()) {
//            int beforeReadPos = buffer.position();
//            int nextPos = beforeReadPos + 100;
//            if(nextPos < size) {
//                buffer.get(contentByte);
//            }else {
//                buffer.get(contentByte,0, size - beforeReadPos);
//            }
//            String finalContent = new String(contentByte,"utf-8");
//            builder.append(finalContent);
//            int afterReadPos = buffer.position();
//
//            Log.i(TAG,String.format("readFileByMMap: beforeReadPos %d finalContent: %s
//            afterReadPos: %d",beforeReadPos,finalContent,afterReadPos));
//
//        }
        int pos = buffer.position();
        int limit = buffer.limit();

        Log.i(TAG, "params pos: " + pos + " limit: " + limit);
        while (buffer.hasRemaining()) {
            byte content = buffer.get();
            if (content != 0) {
                contentByte[buffer.position() - 1] = content;
            } else {
                break;
            }
//           pos = buffer.position();
            Log.i(TAG, "byte content: " + content + " pos: " + pos);
        }
        pos = buffer.position();
        String decodeContent = new String(contentByte, 0, pos - 1, "utf-8");
        Log.i(TAG, "byte final pos: " + pos + " final content:" + decodeContent);
        wChannel.close();
        randomAccessFile.close();
//        outputStream.close();
        return builder.toString();
    }

    public static long writeFileByMMap(String filePath, String content) throws IOException {
        File file = new File(filePath);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel wChannel = randomAccessFile.getChannel();

//        FileChannel wChannel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            Path path = Paths.get(filePath);
//            wChannel = FileChannel.open(path);
//        }
//        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath), true);
//        FileChannel wChannel = fileOutputStream.getChannel();
        long startTime = System.currentTimeMillis();
        long length = file.length() == 0 ? 1024 * 2 : file.length();//2M
        boolean alreadyHasContent = file.length() != 0;
        MappedByteBuffer buffer = wChannel.map(FileChannel.MapMode.READ_WRITE, 0, length);
        long duration = System.currentTimeMillis() - startTime;
        Log.i(TAG, "mmap time: " + duration);

        //先读取目前文件的长度，第一行的四个字节为文字的长度
        int countPosition = (int) (length - 4);
        byte lineBreak[] = System.lineSeparator().getBytes("utf-8");
        int realSize = 0;
        buffer.position(countPosition);
        if (alreadyHasContent) {
            realSize = buffer.getInt();
            buffer.position(realSize);
        }else {
            buffer.putInt(0);
            buffer.position(0);
        }
        Log.i(TAG, "realSize: " + realSize);

        int curSize = realSize;
        for (int i = 0; i < count; i++) {
            byte curContent[] = content.getBytes("utf-8");
            curSize = curSize + curContent.length + lineBreak.length;
            Log.i(TAG, "cur pos:" + buffer.position());

            if(curSize >= countPosition){
                Log.i(TAG,"it is full curSize: "+curSize);
                break;
            }
            buffer.put(curContent);
            buffer.put(lineBreak);
            //修改大小
            buffer.position(countPosition);
            buffer.putInt(curSize);
            buffer.position(curSize);
        }

        Log.i(TAG, "final realSize: " + curSize);

//        buffer.force();
        unmap(buffer);
        wChannel.close();
        randomAccessFile.close();
        Log.i(TAG, "mmp finish write fileSize: " + file.length());
//        if (file.delete()) {
//            Log.i(TAG, "remove file successful");
//        } else {
//            Log.e(TAG, "fail to remove file");
//        }
        return curSize;
    }

    private static void unmap(MappedByteBuffer buffer) {
        try {
            Class<?> clazz = Class.forName("sun.nio.ch.FileChannelImpl");
            Method m = clazz.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(null, buffer);
        } catch (Throwable e) {
            L.exception(e);
        }
    }

}
