package com.sf.SFSample.increaseupdate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.sf.SFSample.R;
import com.zhy.utils.BsPatch;

import java.io.File;

public class IncreaseUpdateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_update);
        findViewById(R.id.increase_update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IncreaseUpdateActivity.this, "increase update...", Toast.LENGTH_LONG).show();
                doPatch();
            }
        });
    }

    private void doPatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");

        BsPatch.bspatch(ApkExtract.extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());

        if (destApk.exists())
            ApkExtract.install(this, destApk.getAbsolutePath());
    }
}
