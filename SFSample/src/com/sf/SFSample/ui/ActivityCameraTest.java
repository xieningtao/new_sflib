package com.sf.SFSample.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.utils.baseutil.CommanDateFormat;
import com.sf.utils.baseutil.SFToast;
import com.sflib.cameralib.CameraView;
import com.sflib.cameralib.compute.ImageHashComputation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by NetEase on 2017/1/13 0013.
 */

public class ActivityCameraTest extends BaseActivity implements Camera.PreviewCallback, Camera.PictureCallback {

    private FrameLayout mCameraContainer;
    private Camera mCamera;

    private boolean mScan = false;
    private Bitmap mCurBitmap = null;
    private String sourceHangmingCode = "";
    private TextView mDiffTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCameraContainer = (FrameLayout) findViewById(R.id.camera_view);
        mCamera = getCameraInstance();
        mCamera.setOneShotPreviewCallback(this);
        CameraView cameraView = new CameraView(this, mCamera);
        mCameraContainer.addView(cameraView);
        findViewById(R.id.take_pic_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScan = false;
                mCamera.setOneShotPreviewCallback(ActivityCameraTest.this);

//                mCamera.takePicture(null, null, ActivityCameraTest.this);
            }
        });

        findViewById(R.id.scan_pic_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScan = true;
                mCamera.setOneShotPreviewCallback(ActivityCameraTest.this);
            }
        });
        mDiffTv = (TextView) findViewById(R.id.diff_tv);
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance() {
        try {
            if (mCamera == null) {
                mCamera = Camera.open();
            }// attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            L.error(TAG, "getCameraInstance exception: " + e);
        }
        return mCamera; // returns null if camera is unavailable
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (mScan) {
            Bitmap bitmap = tranformData(data, camera);
            String targetHangmingCode = ImageHashComputation.compute(bitmap);
            L.info(TAG, "onPreviewFrame targetCode: " + targetHangmingCode + " sourceCode: " + sourceHangmingCode);
            int diff = ImageHashComputation.getHangmingCode(sourceHangmingCode, targetHangmingCode);
            L.info(TAG, "diff:" + diff);
            mCamera.setOneShotPreviewCallback(ActivityCameraTest.this);
            mDiffTv.setText("当前DIFF值:" + diff);
            if(diff<=5){
                SFToast.showToast("成功获取红包");
            }
        } else {
            savePic(data, camera);
            sourceHangmingCode = ImageHashComputation.compute(mCurBitmap);
        }
    }

    private Bitmap tranformData(byte data[], Camera camera) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                previewSize.width,
                previewSize.height,
                null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte rawImage[] = baos.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
        return bitmap;
    }

    private void savePic(byte[] data, Camera camera) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommanDateFormat.YY_MM_SS_FORMAT);
        File pictureFile = SFFileCreationUtil.createFile(dateFormat, "camera", ".png");

        //处理data
        Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                previewSize.width,
                previewSize.height,
                null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte rawImage[] = baos.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        mCurBitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(rawImage, 0, rawImage.length);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            L.error(TAG, "savePic exception: " + e);
        }


    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        savePic(data, camera);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCamera.setOneShotPreviewCallback(null);
        mCamera.release();
    }
}

