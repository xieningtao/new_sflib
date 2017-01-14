package com.sf.SFSample.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.utils.baseutil.CommanDateFormat;
import com.sflib.cameralib.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by NetEase on 2017/1/13 0013.
 */

public class ActivityCameraTest extends BaseActivity implements Camera.PreviewCallback,Camera.PictureCallback{

    private FrameLayout mCameraContainer;
    private Camera mCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCameraContainer= (FrameLayout) findViewById(R.id.camera_view);
        mCamera=getCameraInstance();
        mCamera.setOneShotPreviewCallback(this);
        CameraView cameraView=new CameraView(this,mCamera);
        mCameraContainer.addView(cameraView);
        findViewById(R.id.take_pic_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.setOneShotPreviewCallback(ActivityCameraTest.this);
//                mCamera.takePicture(null, null, ActivityCameraTest.this);
            }
        });
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public  Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            L.error(TAG,"getCameraInstance exception: "+e);
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        savePic(data);

    }

    private boolean savePic(byte[] data) {
        SimpleDateFormat dateFormat=new SimpleDateFormat(CommanDateFormat.YY_MM_SS_FORMAT);
        File pictureFile= SFFileCreationUtil.createFile(dateFormat,"camera",".png");

        YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, null);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pictureFile);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            int quality = 100;
            return bitmap.compress(format, quality, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.error(TAG,"onPreviewFrame exception: "+e);
        } catch (IOException e) {
            e.printStackTrace();
            L.error(TAG,"onPreviewFrame exception: "+e);
        }
        return false;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        savePic(data);
    }
}
