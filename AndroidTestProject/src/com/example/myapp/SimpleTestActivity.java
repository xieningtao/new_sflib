package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;

public class SimpleTestActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.next_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimpleTestActivity.this, HttpActivityTest.class);
                startActivity(intent);
            }
        });
    }
}
