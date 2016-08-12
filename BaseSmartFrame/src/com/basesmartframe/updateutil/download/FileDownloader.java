package com.basesmartframe.updateutil.download;

import android.content.Context;

import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件下载器
 */
public class FileDownloader {
    private static final String TAG = "FileDownloader";

    public Context context;

    // private FilePreferenceService fileService;
    /* 已下载文件长度 */
    private long downloadSize = 0;

    /* 原始文件长度 */
    private long fileSize = 0;

    /* 线程数 */
    public DownloadThread[] threads;

    /* 本地保存文件 */
    private File saveFile;

    /* 缓存各线程下载的长度 */
    private Map<Integer, Long> data = new ConcurrentHashMap<Integer, Long>();

    /* 每条线程下载的长度 */
    private long block;

    /* 下载路径 */
    private String downloadUrl;

    private boolean init = false;// 是否初始化成功

    private boolean pause = false;

    private boolean networkerr = false;

    private boolean notError = true;

    public boolean isNetworkerr() {
        return networkerr;
    }

    public boolean initStatus() {
        return init;
    }

    /**
     * 获取线程数
     */
    public int getThreadSize() {
        return threads.length;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 累计已下载大小
     *
     * @param size
     */
    protected synchronized void append(int size) {
        downloadSize += size;
    }

    /**
     * 更新指定线程最后下载的位置
     *
     * @param threadId 线程id
     * @param pos      最后下载的位置
     */
    protected void update(int threadId, long pos) {
        this.data.put(threadId, pos);
    }

    /**
     * 保存记录文件
     */

    public FileDownloader(Context context, String downloadUrl, long fsize, String dir, String filename, int threadNum) {

        this.context = context;
        this.downloadUrl = downloadUrl;

        File fileSaveDir = new File(dir);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdirs();

        this.threads = new DownloadThread[threadNum];

        this.fileSize = fsize;// 根据响应获取文件大小

        if (this.fileSize <= 0) {
            init = false;
        } else {
            this.saveFile = new File(fileSaveDir, filename);/* 保存文件 */

            for (int i = 0; i < threads.length; i++) {
                data.put(i + 1, 0l);
            }

            this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize / this.threads.length : this.fileSize
                    / this.threads.length + 1;
            if (this.data.size() == this.threads.length && SFFileHelp.isFileExisted(dir + filename)) {
                for (int i = 0; i < this.threads.length; i++) {
                    this.downloadSize += this.data.get(i + 1);
                }
                print("already download size " + this.downloadSize);
            } else {
                this.downloadSize = 0;
                print("already download size " + this.downloadSize);
            }
            init = true;
        }
    }

    /**
     * 构建文件下载器
     *
     * @param downloadUrl 下载路径
     * @param threadNum   下载线程数
     */
    public FileDownloader(Context context, String downloadUrl, String dir, String filename, int threadNum) {
        try {

            this.context = context;
            this.downloadUrl = downloadUrl;
            URL url = new URL(this.downloadUrl);

            File fileSaveDir = new File(dir);
            if (!fileSaveDir.exists())
                fileSaveDir.mkdirs();

            this.threads = new DownloadThread[threadNum];
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            printResponseHeader(conn);
            if (conn.getResponseCode() == 200) {
                this.fileSize = conn.getContentLength();// 根据响应获取文件大小

                if (this.fileSize <= 0) {
                    init = false;
                } else {
                    this.saveFile = new File(fileSaveDir, filename);/* 保存文件 */

                    for (int i = 0; i < threads.length; i++) {
                        data.put(i + 1, 0l);
                    }

                    this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize / this.threads.length : this.fileSize
                            / this.threads.length + 1;

                    if (this.data.size() == this.threads.length && SFFileHelp.isFileExisted(dir + filename)) {
                        for (int i = 0; i < this.threads.length; i++) {
                            this.downloadSize += this.data.get(i + 1);
                        }
                        print("already download size " + this.downloadSize);
                    } else {
                        this.downloadSize = 0;
                        print("already download size " + this.downloadSize);
                    }
                    init = true;
                }

            } else {
                init = false;

            }
        } catch (Exception e) {
            print(e.toString());
            init = false;
        }

        print("init = " + init);
    }

    public void pause() {

        for (int index = 0; null != threads && index < threads.length; index++) {
            if (null != threads[index]) {
                threads[index].pause();
            }

        }
        pause = true;
    }

    /**
     * 获取文件名
     */
    private String getFileName(HttpURLConnection conn) {
        String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
        if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
            for (int i = 0; ; i++) {
                String mine = conn.getHeaderField(i);
                if (mine == null)
                    break;
                if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if (m.find())
                        return m.group(1);
                }
            }
            filename = UUID.randomUUID() + ".tmp";// 默认取一个文件名
        }
        return filename;
    }

    /**
     * 开始下载文件
     *
     * @param listener 监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
     * @return 已下载文件大小
     * @throws Exception
     */
    public long download(DownloadProgressListener listener) throws Exception {
        try {
            if (init == false) {
                return 1L;
            }

            RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");

            if (this.fileSize > 0)
                randOut.setLength(this.fileSize);
            randOut.close();
            URL url = new URL(this.downloadUrl);
            if (this.data.size() != this.threads.length) {
                this.data.clear();
                for (int i = 0; i < this.threads.length; i++) {
                    this.data.put(i + 1, 0L);
                }
            }
            for (int i = 0; i < this.threads.length; i++) {
                long downLength = this.data.get(i + 1);
                if (downLength < this.block && this.downloadSize < this.fileSize) { // 该线程未完成下载时,继续下载
                    this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i + 1), i + 1);
                    this.threads[i].setPriority(7);
                    this.threads[i].start();
                } else {
                    this.threads[i] = null;
                }
            }
            // this.fileService.save(this.downloadUrl, this.data);
            boolean notFinish = true;// 下载未完成
            listener.onStartDownload(this.fileSize);
            while (notFinish && !pause) {// 循环判断是否下载完毕
                notFinish = false;// 假定下载完成
                Thread.sleep(10);
                int num = 0;
                for (int i = 0; i < this.threads.length; i++) {

                    if (this.threads[i] != null && !this.threads[i].isFinish()) {
                        notFinish = true;// 下载没有完成
                        if (this.threads[i].getDownLength() == -1) {// 如果下载失败,再重新下载
                            this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i + 1), i + 1);
                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                            notFinish = false;
                        }

                    }

                    if (!notFinish)
                        num++;
                }

                if (notFinish && listener != null) {
                    listener.onDownloadSize(this.downloadSize);
                }
                if (num == threads.length || downloadSize == fileSize)
                    notFinish = false;
                else
                    notFinish = true;
            }

            // 结束当前线程
            if (this.downloadSize == this.fileSize) {
                listener.onDownloadFinish();
            } else {

                if (!pause) {
                    if (listener != null)
                        listener.onErrorListener("error");
                }

            }

        } catch (Exception e) {

            if (listener != null)
                listener.onErrorListener(e.toString());
            print(e.toString());
            throw new Exception("file download fail");
        }
        return this.downloadSize;
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

    /**
     * 打印Http头字段
     *
     * @param http
     */
    public static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            print(key + entry.getValue());
        }
    }

    private static void print(String msg) {
        L.info(FileDownloader.class, msg);
    }

    public DownloadThread[] getThreads() {
        return threads;
    }

    public static void main(String[] args) {

    }

}
