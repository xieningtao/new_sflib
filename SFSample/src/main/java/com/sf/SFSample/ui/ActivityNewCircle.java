package com.sf.SFSample.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.pickphoto.ActivityFragmentContainer;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;
import com.sf.SFSample.R;
import com.sf.circlelib.SFAddCircleEventFragment;

/**
 * Created by NetEase on 2016/9/5 0005.
 */
public class ActivityNewCircle extends BaseActivity {

    private ImageButton mRightBt;
    private final int ADD_CIRCLE_EVENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_circle);

        mRightBt = (ImageButton) findViewById(R.id.pop_right_ib);
        mRightBt.setVisibility(View.VISIBLE);
        mRightBt.setImageResource(R.drawable.plus);
        mRightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNewCircle.this, ActivityFragmentContainer.class);
                intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, SFAddCircleEventFragment.class.getName());
                startActivityForResult(intent,ADD_CIRCLE_EVENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getFragmentManager().findFragmentById(R.id.circle_fg);
        if (requestCode == ADD_CIRCLE_EVENT) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
