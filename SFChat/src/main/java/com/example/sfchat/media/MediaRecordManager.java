package com.example.sfchat.media;

import android.media.MediaRecorder;

import com.sf.loglib.L;
import com.sf.utils.baseutil.DateFormatHelp;
import com.sf.utils.baseutil.SFFileCreationUtil;
import com.sf.utils.baseutil.SFToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by NetEase on 2016/8/30 0030.
 */
public class MediaRecordManager {
    private final String TAG = getClass().getName();
    private MediaRecorder mMediaRecorder;
    private boolean mIsRecording = false;

    private MediaRecorder.OnErrorListener mOnErrorListener;
    private MediaRecorder.OnInfoListener mOnInfoListener;
    private final String path = "sf_voice";
    private static MediaRecordManager mediaRecordManager = new MediaRecordManager();

    public static MediaRecordManager getInstance() {
        return mediaRecordManager;
    }

    private MediaRecordManager() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
        }
        mMediaRecorder = new MediaRecorder();
        initListener();
    }

    public void setOnErrorListener(MediaRecorder.OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(MediaRecorder.OnInfoListener onInfoListener) {
        mOnInfoListener = onInfoListener;
    }

    private void configMediaRecorder() {
        // 设置音频录入源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置录制音频的输出格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置音频的编码格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // 设置录制音频文件输出文件路径
        SimpleDateFormat format = new SimpleDateFormat(DateFormatHelp._YYYYMMDDHHMMSS);
        File file = SFFileCreationUtil.createFile(format, path, ".amr");
        mMediaRecorder.setOutputFile(file.getAbsolutePath());
    }

    private void initListener() {
        mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                L.info(TAG, TAG + ".initListener.onError what: " + what + " extra:" + extra);
                // 发生错误，停止录制
                mMediaRecorder.stop();
                mIsRecording = false;
                SFToast.showToast("录音发生错误");
                if (mOnErrorListener != null) {
                    mOnErrorListener.onError(mr, what, extra);
                }
            }
        });
        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                L.info(TAG, TAG + ".initListener.onInfo what: " + what + " extra:" + extra);
                if (mOnInfoListener != null) {
                    mOnInfoListener.onInfo(mr, what, extra);
                }
            }
        });
    }

    /**
     * 开始录音
     */

    public void start() throws IOException {
        if(!mIsRecording) {
            // 准备、开始
            mMediaRecorder.reset();
            configMediaRecorder();
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            SFToast.showToast("开始录音");
            mIsRecording = true;
        }
    }

    /**
     * 录音结束
     */
    public void stop() {
        if (mIsRecording) {
            // 如果正在录音，停止并释放资源
            mMediaRecorder.stop();
            mIsRecording = false;
            SFToast.showToast("录音结束");
        }
    }

    public void destroy() {
        mMediaRecorder.release();
    }
}
