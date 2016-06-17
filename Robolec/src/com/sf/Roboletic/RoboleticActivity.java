package com.sf.Roboletic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;

/**
 * Created by NetEase on 2016/6/17 0017.
 */
public class RoboleticActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roboletic_activity);

        final View button = findViewById(R.id.click_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoboleticActivity.this, LoginActivity.class));
            }
        });
    }
}
