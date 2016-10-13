package com.sf.SFSample.tiantu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;
import com.sf.SFSample.R;

import java.util.ArrayList;

/**
 * Created by NetEase on 2016/10/13 0013.
 */
public class ActivityPhotoPreview extends BaseActivity {
    public static final String IMAGE_BEAN_LIST = "image_bean_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        Intent intent = getIntent();
        if (intent != null) {
            int position = intent.getIntExtra(PickPhotosPreviewFragment.INDEX, 0);
            ArrayList<ImageBean> imageBeanArrayList = (ArrayList<ImageBean>) intent.getSerializableExtra(IMAGE_BEAN_LIST);
            Fragment fragment = new PickPhotosPreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(PickPhotosPreviewFragment.CAN_CHOOSE_IMAGE, false);
            bundle.putInt(PickPhotosPreviewFragment.INDEX, position);
            fragment.setArguments(bundle);
            PickPhotosPreviewFragment.setImageListData(imageBeanArrayList);
            getFragmentManager().beginTransaction().replace(R.id.photo_preview_fl, fragment).commitAllowingStateLoss();
        }
    }
}
