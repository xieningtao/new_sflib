package com.basesmartframe.basevideo;

/**
 * Created by xieningtao on 15-5-6.
 */
public interface VideoShowLifeCycle {
    void onCreate();
    void onDestory();
    void onStart();
    void onStop();
    void onResume();
    void onPause();
}
