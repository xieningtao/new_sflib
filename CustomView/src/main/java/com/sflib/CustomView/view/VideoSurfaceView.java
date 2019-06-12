package com.sflib.CustomView.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xieningtao on 15-11-16.
 */
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG = getClass().getName();
    private AtomicBoolean isHolderValid = new AtomicBoolean(false);

    private VideoView mVideoView;
    private CanvasRender mCanvasRender;

    public VideoSurfaceView(Context context) {
        super(context);
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        getHolder().setKeepScreenOn(true);

        mCanvasRender = new CanvasRender();
        new Thread(mCanvasRender, "canvasRender").start();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "method->surfaceCreated");
        isHolderValid.set(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "method->surfaceChanged,params-> width: " + width + " height: " + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "method->surfaceDestroyed");
        isHolderValid.set(false);
    }


    class CanvasRender implements Runnable {

        @Override
        public void run() {
            if (isHolderValid.get()) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    //TODO draw something here
                }
                getHolder().unlockCanvasAndPost(canvas);

                //TODO you have to limit the fps
            } else {
                //TODO you can use object.wait and notify to wake the thread
            }
        }
    }
}
