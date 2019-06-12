package com.basesmartframe.pickphoto;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseActivity;
import com.sf.loglib.L;

/**
 * Created by NetEase on 2016/8/31 0031.
 * it is only for PickPhotosPreviewFragment now.refactor it later
 */
public class ActivityFragmentContainer extends BaseActivity {
    public static final String FRAGMENT_CLASS_NAME = "fragment_class_name";
    public static final String BUNDLE_CONTAINER = "bundle_container";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_photo_preview);
        Intent intent = getIntent();
        if (intent != null) {
            String className = intent.getStringExtra(FRAGMENT_CLASS_NAME);
            Bundle bundle = intent.getBundleExtra(BUNDLE_CONTAINER);
            if (!TextUtils.isEmpty(className)) {
                Fragment fragment = Fragment.instantiate(this, className, bundle);
                getFragmentManager().beginTransaction().replace(R.id.fg_container, fragment).commit();
                getFragmentManager().executePendingTransactions();
            }
        }
    }

}
