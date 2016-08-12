package com.example.sfchat;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.sf.utils.baseutil.FDToastUtil;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public class SFCameraCommand implements SFBaseChatCommand {
    private Uri mPhotoUri;
    private Fragment mFragment;

    private String mImagePath;

    public SFCameraCommand(Fragment fragment){
        this.mFragment=fragment;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public Uri getPhotoUri() {
        return mPhotoUri;
    }
    @Override
    public void executeCommand() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoUri = Uri.parse("file://" + mImagePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        try {
            mFragment.startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            FDToastUtil.show(mFragment.getActivity(),R.string.no_camera_installed);
        }
    }
}
