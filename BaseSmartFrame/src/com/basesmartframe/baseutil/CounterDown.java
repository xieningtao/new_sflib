package com.basesmartframe.baseutil;

import android.os.CountDownTimer;

/**
 * 倒计时类
 *
 * @author xieningtao
 */
public class CounterDown {

    private CountDownTimer timer;

    private OnTickListener mOnTickListener;

    public CounterDown(int duration, int frequency) {
        if (duration <= 0 || frequency <= 0)
            throw new IllegalArgumentException(
                    "both duration and frequency should be more than 0");
        creatCountDown(duration, frequency);
    }


    private void creatCountDown(int duration, int frequency) {
        timer = new CountDownTimer(duration, frequency) {

            public void onTick(long millisUntilFinished) {
                if (mOnTickListener != null)
                    mOnTickListener.onTick(millisUntilFinished);
            }

            public void onFinish() {
                if (mOnTickListener != null) {
                    mOnTickListener.onFinish();
                }
            }
        };


    }

    public void setOnTickListener(OnTickListener listener) {
        this.mOnTickListener = listener;
    }

    public void start() {
        if (timer != null) {
            timer.start();
            if(mOnTickListener!=null){
                mOnTickListener.onStart();
            }
        } else {
            throw new NullPointerException("can't call stop function then call doRefresh ");
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    public static interface OnTickListener {

        /**
         * @param millisUntilFinished --还剩下多少时间
         */
        void onTick(long millisUntilFinished);

        void onFinish();

        void onStart();
    }

}
