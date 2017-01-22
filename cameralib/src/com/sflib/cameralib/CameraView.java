package com.sflib.cameralib;

/**
 * Created by NetEase on 2017/1/13 0013.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.sf.loglib.L;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.sflib.reflection.core.ThreadHelp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** A basic Camera preview class */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG=getClass().getName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private CameraDirection mCameraId=CameraDirection.CAMERA_BACK;
    private SensorControler mSensorControler;

    public CameraView(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setKeepScreenOn(true);
        mSensorControler=SensorControler.getInstance(context);
    }


    /**
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    protected boolean onFocus(Point point, Camera.AutoFocusCallback callback) {
        if (mCamera == null) {
            return false;
        }

        Camera.Parameters parameters = null;
        try {
            parameters = mCamera.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //不支持设置自定义聚焦，则使用自动聚焦，返回

        if(Build.VERSION.SDK_INT >= 14) {

            if (parameters.getMaxNumFocusAreas() <= 0) {
                return doFocus(callback);
            }

            L.info(TAG, "onCameraFocus:" + point.x + "," + point.y);

            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            int left = point.x - 300;
            int top = point.y - 300;
            int right = point.x + 300;
            int bottom = point.y + 300;
            left = left < -1000 ? -1000 : left;
            top = top < -1000 ? -1000 : top;
            right = right > 1000 ? 1000 : right;
            bottom = bottom > 1000 ? 1000 : bottom;
            areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
            parameters.setFocusAreas(areas);
            try {
                //本人使用的小米手机在设置聚焦区域的时候经常会出异常，看日志发现是框架层的字符串转int的时候出错了，
                //目测是小米修改了框架层代码导致，在此try掉，对实际聚焦效果没影响
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return false;
            }
        }


        return doFocus(callback);
    }

    private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
//            if (success) {
//                mFocusImageView.onFocusSuccess();
//            } else {
//                //聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
//                mFocusImageView.onFocusFailed();
//            }
            ThreadHelp.runInMain(new Runnable() {
                @Override
                public void run() {
                    //一秒之后才能再次对焦
                    mSensorControler.unlockFocus();
                }
            }, 1000);
        }
    };

    /**
     * 相机对焦
     *
     * @param point
     * @param needDelay 是否需要延时
     */
    public void onCameraFocus(final Point point, boolean needDelay) {
        int delayDuration = needDelay ? 300 : 0;

        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                if (!mSensorControler.isFocusLocked()) {
                    if (onFocus(point, autoFocusCallback)) {
                        mSensorControler.lockFocus();


                        //播放对焦音效
//                        if(mFocusSoundPrepared) {
//                            mSoundPool.play(mFocusSoundId, 1.0f, 0.5f, 1, 0, 1.0f);
//                        }
                    }
                }
            }
        }, delayDuration);
    }



    private boolean doFocus(Camera.AutoFocusCallback callback) {
        try {
            mCamera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 初始化相机
     */
    private void initCamera() {
        mParameters = mCamera.getParameters();
        mParameters.setPictureFormat(PixelFormat.JPEG);

        List<String> focusModes = mParameters.getSupportedFocusModes();

        //设置对焦模式
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        try {
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Camera.Size adapterSize = mCamera.getParameters().getPreviewSize();
        adapterSize = mCamera.getParameters().getPictureSize();
        L.info(TAG, "adpterSize Picture-->width:" + adapterSize.width + "  height:" + adapterSize.height);

        //调整控件的布局  防止预览被拉伸
        adjustView(adapterSize);
        determineDisplayOrientation();
    }

    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly
     */
    private void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId.ordinal(), cameraInfo);

        int rotation = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = 90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = 180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = 270;
                break;
            }
        }

        int displayOrientation;

        // Camera direction
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // Orientation is angle of rotation when facing the camera for
            // the camera image to match the natural orientation of the device
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

//        mDisplayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
//        mLayoutOrientation = degrees;

        mCamera.setDisplayOrientation(displayOrientation);

        L.info(TAG, "displayOrientation:" + displayOrientation);
    }

    /**
     * 调整SurfaceView的宽高
     * @param adapterSize
     */
    private void adjustView(Camera.Size adapterSize ){
        int width = SystemUIWHHelp.getScreenRealWidth((Activity) getContext());
        int height = width * adapterSize.width / adapterSize.height;

        //让surfaceView的中心和FrameLayout的中心对齐
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
//        params.topMargin = -(height - width) / 2;
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            initCamera();
            mCamera.setPreviewDisplay(holder);
//            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            L.debug(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            L.debug(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
