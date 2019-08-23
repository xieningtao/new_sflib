package com.basesmartframe.updateutil;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.basesmartframe.R;
import com.basesmartframe.updateutil.download.DownloadProgressListener;
import com.basesmartframe.updateutil.download.FileDownloader;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileHelp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//异步下载内部线程类
public class UpdateService {
    private final String TAG=UpdateService.class.getName();
    /**
     * 例子的链接：http://3g.163.com/links/4002?vs=38
     */
    private final String DOWNLOADER_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sf_down_load" + File.separator;
    public static final int MSG_DOWNLOAD = 1;

    public static final int MSG_ERROR = 2;
    public static final int MSG_FINISH = 3;
    public static final int MSG_START = 4;

    public static final String UPGRADE_URL = "upgrade_url";

    long mTotalSize = 0;

    int mAutoid = 10000;

    FileDownloader mDownLoader = null;

    private NotificationManager mNotificationManager;

    private RemoteViews remoteView;

    private Notification mNotification;

    private PendingIntent pIntent;

    private int mMessageCount = 0;
    private Context mContext;
//    private Dialog mDialog;
    private ProgressBar mUpgradePb;
    private TextView mRateTv;
    private TextView mTitleTv;

    public UpdateService(Context context) {
        this.mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        mNotification = new Notification(R.drawable.update_notify_big, context.getString(R.string.update_notify_title), System.currentTimeMillis());
        View upgradeView = LayoutInflater.from(context).inflate(R.layout.upgrade_layout, null);
        mUpgradePb = upgradeView.findViewById(R.id.sf_upgrade_pb);
        mRateTv= upgradeView.findViewById(R.id.rate_tv);
        mTitleTv= upgradeView.findViewById(R.id.title_tv);
    }

    public void start(String url){
        L.info(TAG,TAG+".start,url: "+url);
        if(TextUtils.isEmpty(url)){
            return;
        }
        UpdateThread t = new UpdateThread(url);
        t.start();
    }

    private void setNotification(int layoutId, int flags) {
        remoteView = new RemoteViews(mContext.getPackageName(), layoutId);

        mNotification.icon = R.drawable.update_notify_big;
        mNotification.contentView = remoteView;
        mNotification.contentIntent = pIntent;
        mNotification.flags = flags;

    }

    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public class UpdateThread extends Thread {

        private String mDir;

        private String mSaveFile;

        private String mUrl;

        private String mCurTime;

        public UpdateThread(String url) {
            mUrl = url;
            mSaveFile = getFileName(url);
            mDir = DOWNLOADER_FILE;

            Uri uri = Uri.parse("file://" + mDir + mSaveFile);
            SFFileHelp.removeDir(uri.getPath());
            mCurTime = new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()));
        }

        public void run() {

            mDownLoader = new FileDownloader(mContext, mUrl, mDir, mSaveFile, 1);
            if (!mDownLoader.initStatus()) {
                UIHandler.sendEmptyMessage(MSG_ERROR);
                return;
            }
            try {
                mDownLoader.download(new DownloadProgressListener() {

                    public void onDownloadSize(long size) {
                        DownloadBean bean = new DownloadBean();
                        bean.mLength = mDownLoader.getFileSize();
                        bean.mDownloadSize = size;
                        bean.mAutoid = mAutoid;

                        Message msg = new Message();
                        msg.what = MSG_DOWNLOAD;
                        msg.obj = bean;
                        UIHandler.sendMessage(msg);

                    }

                    @Override
                    public void onErrorListener(String ex) {
                        UIHandler.sendEmptyMessage(MSG_ERROR);
                    }

                    @Override
                    public void onDownloadFinish() {
                        UIHandler.sendEmptyMessage(MSG_FINISH);
                    }

                    @Override
                    public void onStartDownload(long size) {
                        UIHandler.sendEmptyMessage(MSG_START);
                    }
                });
            } catch (Exception e) {
                UIHandler.sendEmptyMessage(MSG_ERROR);
            }
        }

        private Handler UIHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_START:
                        startDownload();
                        break;
                    case MSG_DOWNLOAD:
                        DownloadBean bean = (DownloadBean) msg.obj;
                        long percent = 0;
                        if (bean != null && bean.mLength != 0)
                            percent = bean.mDownloadSize * 100 / bean.mLength;
                        int autoid = bean.mAutoid;
                        if (percent >= 100) {
                            finishDownloading();
                            setRemoteViewForDownloaded();
                            mNotificationManager.notify(autoid, mNotification);
                        } else if (mMessageCount % 5 == 0) {
                            setRemoteViewForDownloading(percent);
                            updateDownloadingProgress(percent);
                            mNotificationManager.notify(autoid, mNotification);
                        }
                        mMessageCount++;
                        break;

                    case MSG_ERROR:
                        failDownload();
                        break;

                }
            }
        };

        private void setRemoteViewForDownloaded() {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String filePath = DOWNLOADER_FILE + getFileName(mUrl);
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            pIntent = PendingIntent.getActivity(mContext, 0, i, 0);
            setNotification(R.layout.update_notify_downloaded, Notification.FLAG_AUTO_CANCEL);
            remoteView.setTextViewText(R.id.download_finish_notify_percenttext, mCurTime);
            remoteView.setTextViewText(R.id.download_finish_notify_text, "下载已完成,点击进行安装");
        }

        private void setRemoteViewForDownloading(Long percent) {
            pIntent = PendingIntent.getActivity(mContext, 0, new Intent(), 0);
            setNotification(R.layout.update_notify_downloding, Notification.FLAG_ONGOING_EVENT);
            remoteView.setProgressBar(R.id.download_notify_pb, 100, percent.intValue(), false);
            remoteView.setTextViewText(R.id.download_notify_percenttext, mCurTime);
        }

        private void failDownload(){
            mTitleTv.setText("下载失败");
        }
        private void updateDownloadingProgress(Long percent) {
            mUpgradePb.setProgress(percent.intValue());
            mRateTv.setText(percent+"%");
        }

        private void startDownload(){
            mUpgradePb.setProgress(0);
            mUpgradePb.setMax(100);
            mRateTv.setText("0%");
        }
        private void finishDownloading() {
            mUpgradePb.setProgress(100);
            mRateTv.setText("100%");
            Intent upgradeIntent = new Intent(Intent.ACTION_VIEW);
            String filePath = DOWNLOADER_FILE + getFileName(mUrl);
            upgradeIntent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            upgradeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            mContext.startActivity(upgradeIntent);
            mNotificationManager.cancel(mAutoid);
        }

    }

    private String getSoftwareSize(long size) {
        String rst = "0.00M";
        if (size < 1024 * 1024) {
            rst = "" + (size / 1024) + "K";
        } else {
            String temp = "" + size % 1048576;
            if (temp.length() > 2) {
                temp = temp.substring(0, 2);
            }
            rst = "" + (size / 1048576) + "." + temp + "M";
        }
        return rst;

    }

    class DownloadBean {
        public long mLength;

        public long mDownloadSize;

        public int mAutoid;
    }
}
