//package com.example.sfchat.media;
//
//import android.content.Context;
//import android.media.AudioFormat;
//import android.media.AudioRecord;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Looper;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//
//
//import com.sf.loglib.L;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
///**
// * Created by NetEase on 2016/8/12 0012.
// */
//public class AudioRecorderManager {
//
//    public static final String TAG = "AudioRecoder";
//
//    public enum State {
//        INITIALIZING, READY, RECORDING, ERROR, STOPPED
//    };
//
//    protected String mOutputFormat;
//
//    protected int mSampleRates;
//
//    protected short mChannel;
//
//    protected static AudioRecord mAudioRecord = null;
//
//    protected State mState;
//
//    protected String mOutputFilePath;
//
//    // 判断CPU类型是否能够进行降噪
//    protected boolean mCanNoiseSuppressor;
//
//    private Context mContext;
//
//    private AudioRecorderManager thiz = null;
//
//    private OnRecordListener mListener = null;
//
//    private static AsyncTask<Void, Void, Boolean> mStartAsyncTask = null;
//
//    private AsyncTask<Void, Void, Boolean> mWaitEndAsyncTask = null;
//
//    // use seconds to calculate
//    private int mMaxRecordTime = 60;
//
//    // use millisecond to calculate
//    private static final int TIMER_INTERVAL = 120;
//
//    private int mFramePeriod;
//
//    private int mAmplitude = 0;
//
//    private int mAudioSource;
//
//    private int mChannelConfig;
//
//    private short mSamples;
//
//    private int mAudioFormat;
//
//    private int mBufferSizeInBytes;
//
//    private long mDuration;
//
//    private byte[] mBuffer;
//
//    private static AudioBlockingQueue mSpeechBlockingQueue = new AudioBlockingQueue();
//
//    private static final int WHAT_RESTART_RECORD = 0x000;
//
//    private static final int WHAT_START_RECORD = 0x001;
//
//    private static final int WHAT_END_RECORD = 0x002;
//
//    private static final int WHAT_COUNT_RECORD = 0x003;
//
//    public String getOutputFormat() {
//        return mOutputFormat;
//    }
//
//    // use CC.FileSuffix.*
//    public void setOutputFormat(String output_format) {
//        mOutputFormat = output_format;
//    }
//
//    public void setOutPutFilePath(String path) {
//        this.mOutputFilePath = path;
//    }
//
//    public void setAudioSamplingRate(int samplingRate) {
//        mSampleRates = samplingRate;
//    }
//
//    public State getState() {
//        State state;
//        if (mAudioRecord == null)
//            state = State.STOPPED;
//        else {
//            state = mState;
//        }
//        return state;
//    }
//
//    public int getSampleRates() {
//        return mSampleRates;
//    }
//
//    public int getChannel() {
//        return mChannel;
//    }
//
//    public boolean getCanNoiseSuppressor() {
//        return mCanNoiseSuppressor;
//    }
//
//    public void setMaxDuration(int maxTime) {
//        mMaxRecordTime = maxTime;
//    }
//
//    private void initHandler() {
//        mRecorderHandler = new RecorderHandler(mContext.getMainLooper());
//    }
//
//    private RecorderHandler mRecorderHandler;
//
//    private static HandlerThread mHandlerThread;
//
//    class RecorderHandler extends Handler {
//
//        public RecorderHandler(Looper looper) {
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case WHAT_START_RECORD:
//                    if (mAudioRecord != null) {
//                        final int maxAmplitude = thiz.getMaxAmplitude();
//                        if (mListener != null) {
//                            mListener.updateForeground(maxAmplitude);
//                        }
//                        sendEmptyMessageDelayed(WHAT_RESTART_RECORD, 100L);
//                    }
//                    break;
//                case WHAT_RESTART_RECORD:
//                    if (mListener != null) {
//                        mListener.updateForeground(0);
//                    }
//                    sendEmptyMessageDelayed(WHAT_START_RECORD, 50L);
//                    break;
//
//                case WHAT_END_RECORD:
//                    L.info(this, "end record");
//                    removeMessages(WHAT_START_RECORD);
//                    removeMessages(WHAT_RESTART_RECORD);
//                    removeMessages(WHAT_COUNT_RECORD);
//                    break;
//                case WHAT_COUNT_RECORD:
//                    L.info(this, "update time");
//                    int time = msg.arg1;
//                    if (time >= mMaxRecordTime) {
//                        time = mMaxRecordTime;
//                    }
//                    final int currentTime = time;
//                    if (mListener != null) {
//                        mListener.updateTime(currentTime, mMaxRecordTime);
//                    }
//                    Message mMessage = Message.obtain(this, WHAT_COUNT_RECORD);
//                    mMessage.arg1 = ++time;
//                    if (mAudioRecord != null) {
//                        this.sendMessageDelayed(mMessage, 950);
//                    } else {
//                        mMessage.arg1 = 0;
//                    }
//                    break;
//            }
//        }
//    }
//
//    /**
//     * @param context
//     * @param audioSource   the recording source. See MediaRecorder.AudioSource for
//     *                      recording source definitions.
//     * @param channelConfig describes the configuration of the audio channels.
//     * @param sampleRates   the sample rate expressed in Hertz.
//     * @param audioFormat   the format in which the audio data is represented. Warning:
//     *                      Current not call the method,please call
//     *                      AudioRecorder(Context).
//     */
//    public AudioRecorderManager(Context context, int audioSource, short channelConfig, int sampleRates, int audioFormat) {
//        super();
//        this.mContext = context;
//        this.mAudioSource = audioSource;
//        this.mChannelConfig = channelConfig;
//        this.mSampleRates = sampleRates;
//        this.mAudioFormat = audioFormat;
//        if (mAudioFormat == AudioFormat.ENCODING_PCM_16BIT) {
//            mSamples = 16;
//        } else {
//            mSamples = 8;
//        }
//        if (mChannelConfig == AudioFormat.CHANNEL_IN_MONO) {
//            mChannel = 1;
//        } else {
//            mChannel = 2;
//        }
//        mOutputFormat = C.FileSuffix.AAC;
//        mCanNoiseSuppressor = AudioUtil.canNoiseSuppressor();
//        thiz = this;
//    }
//
//    /**
//     * @param context Important: construct AudioRecorder can only call this method.
//     */
//    public AudioRecorderManager(Context context, String filePath) {
//        super();
//        this.mContext = context;
//        this.mOutputFilePath = filePath;
//        this.mAudioSource = MediaRecorder.AudioSource.MIC;
//        this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//        this.mSampleRates = 8000;
//        this.mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
//        this.mSamples = 16;
//        this.mChannel = 1;
//        mOutputFormat = C.FileSuffix.AAC;
//        mCanNoiseSuppressor = AudioUtil.canNoiseSuppressor();
//        thiz = this;
//    }
//
//    private void initAudioRecoder() {
//        Log.i(TAG, "initAudioRecoder() called");
//        try {
//            mFramePeriod = mSampleRates * TIMER_INTERVAL / 1000;
//            this.mBufferSizeInBytes = mFramePeriod * 2 * mSamples * mChannel / 8;
//            int minBufferSize = AudioRecord.getMinBufferSize(mSampleRates, mChannelConfig, mAudioFormat);
//            if (mBufferSizeInBytes < minBufferSize) {
//                mBufferSizeInBytes = minBufferSize;
//                mFramePeriod = mBufferSizeInBytes / (2 * mSamples * mChannel / 8);
//                L.error(TAG, "Increasing buffer size to " + Integer.toString(mBufferSizeInBytes));
//            }
//
//            mAudioRecord = new AudioRecord(mAudioSource, mSampleRates, mChannelConfig, mAudioFormat, mBufferSizeInBytes);
//            if (mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED)
//                throw new Exception("AudioRecord initialization failed");
//            mAudioRecord.setRecordPositionUpdateListener(mUpdateListener);
//            mAudioRecord.setPositionNotificationPeriod(mFramePeriod);
//            mAudioRecord.setNotificationMarkerPosition(mSampleRates * mMaxRecordTime);
//            mAmplitude = 0;
//            if (mSpeechBlockingQueue == null) {
//                mSpeechBlockingQueue = new AudioBlockingQueue();
//            } else {
//                mSpeechBlockingQueue.reset();
//            }
//            if (TextUtils.isEmpty(mOutputFilePath)) {
//                mState = State.ERROR;
//                L.error(TAG, "initializing recording state : ERROR");
//            } else {
//                mState = State.INITIALIZING;
//                Log.i(TAG, "initializing recording state : INITIALIZING");
//            }
//        } catch (Exception e) {
//            if (e != null) {
//                L.error(TAG, "initializing recording error : " + e);
//            } else {
//                L.error(TAG, "Unknown error occured while initializing recording");
//            }
//            mState = State.ERROR;
//            L.error(TAG, "initializing recording state : ERROR");
//        }
//    }
//
//    private void prepare() {
//        L.error(this, "audio recore start 444");
//        Log.i(TAG, "prepare() called");
//        if (mState == State.INITIALIZING) {
//            if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED && !TextUtils.isEmpty(mOutputFilePath)) {
//                boolean result = mSpeechBlockingQueue.initSpeechQueue(this, mOutputFilePath);
//                if (result) {
//                    mBuffer = new byte[mFramePeriod * mSamples / 8 * mChannel];
//                    mState = State.READY;
//                    Log.i(TAG, "prepare state : READY");
//                } else {
//                    L.error(TAG, "mSpeechBlockingQueue.initSpeechQueue error");
//                    mState = State.ERROR;
//                }
//            } else {
//                L.error(TAG, "prepare() method called on uninitialized recorder");
//                mState = State.ERROR;
//            }
//        } else {
//            L.error(TAG, "prepare() method called on illegal state");
//            release();
//            mState = State.ERROR;
//        }
//    }
//
//    private void start() {
//        L.info(this, "start() called");
//        if (mState == State.READY) {
//            mAudioRecord.startRecording();
//            mState = State.RECORDING;
//
//            Message startMsg = new Message();
//            startMsg.what = WHAT_START_RECORD;
//            startMsg.setTarget(mRecorderHandler);
//            startMsg.sendToTarget();
//
//            Message countMsg = new Message();
//            countMsg.what = WHAT_COUNT_RECORD;
//            countMsg.arg1 = 0;
//            countMsg.setTarget(mRecorderHandler);
//            countMsg.sendToTarget();
//
//            mDuration = System.currentTimeMillis();
//            // 这个地方存在部分手机上不能触发回调,先read一把,读多少有待商榷.
//            int length = 0;
//            while (true && !mStartAsyncTask.isCancelled()) {
//                int readLength = mAudioRecord.read(mBuffer, 0, mBuffer.length);
//                length += readLength;
//                if (length >= mFramePeriod * mSamples / 8 * mChannel)
//                    break;
//            }
//
//        } else {
//            L.error(this, "start() called on illegal state");
//            mState = State.ERROR;
//        }
//    }
//
//    public void startRecord() {
//        L.error(this, "audio recore start");
//        if (mStartAsyncTask != null && mStartAsyncTask.getStatus() == AsyncTask.Status.RUNNING && !mStartAsyncTask.isCancelled()) {
//            L.error(this, "audio recore start 111");
//            if (mListener != null)
//                mListener.onError(null);
//            return;
//        }
//
//        if (mAudioRecord != null
//                || (mSpeechBlockingQueue != null && mSpeechBlockingQueue.getTask() != null
//                && AsyncTask.Status.RUNNING == mSpeechBlockingQueue.getTask().getStatus() && !mSpeechBlockingQueue.getTask()
//                .isCancelled())) {
//            L.error(this, "audio recore start 222");
//            if (mListener != null)
//                mListener.onError(null);
//            return;
//        }
//
//        mStartAsyncTask = new AsyncTask<Void, Void, Boolean>() {
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                L.error(this, "asynctask doinbackground");
//                try {
//                    start();
//                } catch (Throwable e) {
//                    L.error(this, "start recording error:" + e);
//                    release();
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                L.error(this, "async task on pre excute");
//            }
//
//            @Override
//            protected void onPostExecute(Boolean result) {
//                if (result && mState == State.RECORDING) {
//                    if (mListener != null) {
//                        mListener.onStartRecord();
//                    }
//                } else {
//                    if (mListener != null) {
//                        mListener.onError(null);
//                        stop();
//                    }
//                }
//            }
//
//            @Override
//            protected void onCancelled() {
//                L.error(this, "start audiorecorder : cancelled");
//            }
//        };
//
//        initHandler();
//
//        if (mOutputFormat.equalsIgnoreCase(C.FileSuffix.AAC)) {
//            if (mCanNoiseSuppressor) {
//                this.mSampleRates = 44100;
//                this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//                this.mChannel = 1;
//                initAudioRecoder();
//                if (mState != State.INITIALIZING) {
//                    this.mSampleRates = 16000;
//                    this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//                    this.mChannel = 1;
//                    initAudioRecoder();
//                }
//                if (mState != State.INITIALIZING) {
//                    this.mSampleRates = 8000;
//                    this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//                    this.mChannel = 1;
//                    initAudioRecoder();
//                }
//            } else {
//                this.mSampleRates = 8000;
//                this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//                this.mChannel = 1;
//                initAudioRecoder();
//            }
//        } else {
//            this.mSampleRates = 8000;
//            this.mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
//            this.mChannel = 1;
//            initAudioRecoder();
//        }
//
//        if (mState == State.ERROR) {
//            L.error(this, "audio recore start 333");
//            mListener.onError(null);
//            release();
//            return;
//        }
//        prepare();
//        if (mState == State.ERROR) {
//            L.error(this, "audio recore start 555");
//            mListener.onError(null);
//            release();
//            return;
//        }
//        L.error(this, "audio recore start 666");
//        VersionCompat.getAsyncTaskCompat().executeOnExecutor(mStartAsyncTask,
//                null);
//        // mStartAsyncTask.execute();
//        L.error(this, "audio recore start 777");
//    }
//
//    public void stopRecord() {
//        if (mStartAsyncTask != null && AsyncTask.Status.RUNNING == mStartAsyncTask.getStatus() && !mStartAsyncTask.isCancelled()) {
//            L.error(this, "audio stop async task");
//            mStartAsyncTask.cancel(true);
//
//            if (mAudioRecord == null) {
//                if (mListener != null) {
//                    mListener.onEndRecord(false, 0);
//                }
//                return;
//            }
//
//            if (mRecorderHandler != null) {
//                L.error(this, "end record 11");
//                Message endMsg = new Message();
//                endMsg.what = WHAT_END_RECORD;
//                endMsg.setTarget(mRecorderHandler);
//                endMsg.sendToTarget();
//            }
//
//            try {
//                mAudioRecord.stop();
//                mAudioRecord.setRecordPositionUpdateListener(null);
//                mState = State.STOPPED;
//            } catch (Throwable e) {
//                L.error(TAG, "method->stopRecord,exception: " + e);
//            } finally {
//                release();
//            }
//
//            if (mAudioRecord == null) {
//                if (mListener != null) {
//                    mListener.onError("录音太短");
//                    mListener.onEndRecord(false, 0);
//                }
//            }
//        } else {
//            if (mRecorderHandler != null) {
//                L.error(this, "end record 22");
//                Message endMsg = new Message();
//                endMsg.what = WHAT_END_RECORD;
//                endMsg.setTarget(mRecorderHandler);
//                endMsg.sendToTarget();
//            }
//            stop();
//        }
//        release();
//    }
//
//    private void stop() {
//        Log.i(TAG, "stop()called");
//        if (mState == State.RECORDING) {
//
//            if (mWaitEndAsyncTask != null && mWaitEndAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
//                return;
//
//            if (mAudioRecord != null) {
//                mAudioRecord.stop();
//                mAudioRecord.setRecordPositionUpdateListener(null);
//            }
//            mDuration = System.currentTimeMillis() - mDuration;
//
//            mWaitEndAsyncTask = new AsyncTask<Void, Void, Boolean>() {
//                @Override
//                protected Boolean doInBackground(Void... params) {
//                    boolean result;
//                    result = true;
//                    try {
//                        if (mSpeechBlockingQueue != null) {
//                            if (mSpeechBlockingQueue.getTask() != null)
//                                if (AsyncTask.Status.FINISHED != mSpeechBlockingQueue.getTask().getStatus())
//                                    L.error(this, "wait for mSpeechBlockingQueue task ");
//                            try {
//                                mSpeechBlockingQueue.getTask().get(6, TimeUnit.SECONDS);
//                            } catch (TimeoutException e) {
//
//                                L.error(this, "wait for mSpeechBlockingQueue task time out");
//                                mSpeechBlockingQueue.getTask().cancel(true);
//                                result = false;
//                            }
//                        }
//                    } catch (InterruptedException e) {
//                        L.error(TAG, "method->stop,exception: " + e);
//                        result = false;
//                    } catch (ExecutionException e) {
//                        L.error(TAG, "method->stop,exception: " + e);
//                        result = false;
//                    } catch (Exception e) {
//                        result = false;
//                    }
//                    return result;
//                }
//
//                @Override
//                protected void onPostExecute(Boolean result) {
//                    mState = State.STOPPED;
//                    if (result) {
//                        int mediaLen = 0;
//                        if (android.os.Build.VERSION.SDK_INT < 12) {
//                            mediaLen = (int) mDuration;
//                        } else {
//                            MediaPlayer mMediaPlayer = MediaPlayer.create(mContext, Uri.fromFile(AttachmentStore.create(mOutputFilePath)));
//                            if (mMediaPlayer == null) {
//                                if (mListener != null) {
//                                    mListener.onEndRecord(false, 0);
//                                }
//                                return;
//                            }
//                            mediaLen = mMediaPlayer.getDuration();
//                            mMediaPlayer.release();
//                            mMediaPlayer = null;
//                        }
//
//                        if (mediaLen <= 500) {
//                            if (mListener != null) {
//                                mListener.onError("录音太短");
//                                mListener.onEndRecord(false, 0);
//                            }
//                            return;
//                        }
//
//                        if (mListener != null) {
//                            mListener.onEndRecord(true, (int) mDuration);
//                        }
//                    } else {
//                        if (mListener != null) {
//                            mListener.onError("录音出错");
//                        }
//                    }
//                }
//            };
//            VersionCompat.getAsyncTaskCompat().executeOnExecutor(mWaitEndAsyncTask, null);
//            // mWaitEndAsyncTask.execute();
//        } else {
//            L.error(TAG, "stop() called on illegal state");
//            mState = State.ERROR;
//        }
//    }
//
//    private void release() {
//        Log.d(TAG, "release() called");
//        if (mState == State.RECORDING) {
//            stop();
//        }
//        if (mAudioRecord != null) {
//            mAudioRecord.setRecordPositionUpdateListener(null);
//            mAudioRecord.release();
//            mAudioRecord = null;
//        }
//    }
//
//    /**
//     * UpdateListener
//     */
//    private AudioRecord.OnRecordPositionUpdateListener mUpdateListener = new AudioRecord.OnRecordPositionUpdateListener() {
//
//        @Override
//        public void onPeriodicNotification(AudioRecord recorder) {
//            int bufferReadResult = recorder.read(mBuffer, 0, mBuffer.length);
//            if (bufferReadResult > 0)
//                mSpeechBlockingQueue.add(mBuffer, bufferReadResult);
//
//            if (mSamples == 16) {
//                for (int i = 0; i < mBuffer.length / 2; i++) {
//                    short curSample = AudioUtil.getShort(mBuffer[i * 2], mBuffer[1 + i * 2]);
//                    if (curSample > mAmplitude) {
//                        mAmplitude = curSample;
//                    }
//                }
//            } else {
//                for (int i = 0; i < mBuffer.length; i++) {
//                    if (mBuffer[i] > mAmplitude) {
//                        mAmplitude = mBuffer[i];
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void onMarkerReached(AudioRecord recorder) {
//            L.error(this, "audoi record up time stop");
//            Log.d(TAG, "MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
//            if (mListener != null) {
//                mListener.updateTime(mMaxRecordTime, mMaxRecordTime);
//            }
//            stopRecord();
//        }
//    };
//
//    public void setOnRecordListener(OnRecordListener listener) {
//        mListener = listener;
//    }
//
//    public int getMaxAmplitude() {
//        if (mState == State.RECORDING) {
//            int result = mAmplitude;
//            mAmplitude = 0;
//            return result;
//        } else {
//            return 0;
//        }
//    }
//
//    public static interface OnRecordListener {
//        public void onStartRecord();
//
//        public void onEndRecord(boolean isSuccess, final int duration);
//
//        public void onError(String error);
//
//        public void updateForeground(int maxAmplitude);
//
//        public void updateTime(int currentTime, int maxTime);
//    }
//}
