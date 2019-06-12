package com.basesmartframe.updateutil.download;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class DownloadThread extends Thread {
    private static final String TAG = "DownloadThread";

    private File saveFile;

    private URL downUrl;

    private long block;

    /* 下载开始位置 */
    private int threadId = -1;

    private long downLength;

    private boolean finish = false;

    private boolean error = false;

    private boolean pause = false;

    private FileDownloader downloader;

    private int buffersize = 1024 * 1024;

    public DownloadThread(FileDownloader downloader, URL downUrl, File saveFile, long block, long downLength, int threadId) {
        this.downUrl = downUrl;
        this.saveFile = saveFile;
        this.block = block;
        this.downloader = downloader;
        this.threadId = threadId;
        this.downLength = downLength;
        print("" + downUrl);
    }

    public void pause() {
        pause = true;
    }

    @Override
    public void run() {
        if (downLength < block) {// 未下载完成
            HttpURLConnection http = null;
            try {

                http = (HttpURLConnection) downUrl.openConnection();
                http.setConnectTimeout(30 * 1000);
                http.setRequestMethod("GET");
                http.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                http.setRequestProperty("Accept-Language", "zh-CN");
                http.setRequestProperty("Referer", downUrl.toString());
                http.setRequestProperty("Charset", "UTF-8");
                long startPos = block * (threadId - 1) + downLength;// 开始位置
                long endPos = block * threadId - 1;// 结束位置
                http.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);// 设置获取实体数据的范围
                http.setRequestProperty(
                        "User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                http.setRequestProperty("Connection", "Keep-Alive");

                int rspncode = http.getResponseCode();

                if (rspncode == HttpURLConnection.HTTP_PARTIAL) {
                    InputStream inStream = http.getInputStream();
                    byte[] buffer = new byte[buffersize];
                    int offset = 0;
                    RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
                    threadfile.seek(startPos);

                    while ((offset = inStream.read(buffer)) != -1 && (!this.pause)) {
                        threadfile.write(buffer, 0, offset);
                        downLength += offset;
                        downloader.update(this.threadId, downLength);
                        downloader.append(offset);
                        sleep(100);
                    }
                    threadfile.close();
                    inStream.close();

                }
                this.finish = true;
                print("Thread " + this.threadId + " is Finish. Code=" + rspncode);

            } catch (Exception e) {
                this.downLength = -1;
                print("Thread " + this.threadId + ":" + e);
            } finally {
                if (null != http) {
                    http.disconnect();
                    http = null;
                }
            }
        }
    }

    private static void print(String msg) {
        // Log.i(TAG, msg);
    }

    /**
     * 下载是否完成
     *
     * @return
     */
    public boolean isFinish() {
        return finish;
    }

    /**
     * 下载是否异常
     *
     * @return
     */
    public boolean isError() {
        return error;
    }

    /**
     * 已经下载的内容大小
     *
     * @return 如果返回值为-1,代表下载失败
     */
    public long getDownLength() {
        return downLength;
    }

    public static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            print(key + entry.getValue());
        }
    }

    /**
     * 获取Http响应头字段
     *
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }
}
