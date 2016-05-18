package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.nostra13.universalimageloader.utils.L;
import com.sf.SFSample.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xieningtao on 16-3-14.
 */
public class AdbShellActivity extends BaseActivity {

    private final String anrCmd = "cp /data/anr/traces_com.duowan.kiwitv.txt /sdcard";
    private final String catCmd = "/bin/sh cat data/anr/traces_com.duowan.kiwitv.txt > /sdcard/traces_com.duowan.kiwitv.txt";
    private final String lsCmd = "ls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adbshell_layout);

        findViewById(R.id.anr_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShellCmd.docmd(catCmd);
                String srcPath = "data/anr/traces_com.duowan.kiwitv.txt";
                String desPath = "/sdcard/traces_com.duowan.kiwitv.txt";
                copyFile(srcPath, desPath);
            }
        });
    }

    private void copyFile(String srcPath, String desPath) {
        if (srcPath == null || desPath == null) {
            L.e(TAG, "method->copyFile,srcPath is null or desPath is null");
            return;
        }
        L.i(TAG, "method->copyFile,scrPath: " + srcPath + " desPath: " + desPath);
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            L.e(TAG, "method->copyFile,there is no file named: " + srcPath);
            return;
        }
        File desFile = new File(desPath);
        if (!desFile.exists()) {
            try {
                desFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                L.w(TAG, "method->copyFile, fail to create file: " + desPath + " exception: " + e.getMessage());
            }
        }

        FileInputStream srcIn = null;
        FileOutputStream desOut = null;
        try {
            srcIn = new FileInputStream(srcFile);
            desOut = new FileOutputStream(desFile);
            byte content[] = new byte[1024];
            int length = 0;
            while ((length = srcIn.read(content)) != -1) {
                desOut.write(content, 0, length);
            }
            desOut.flush();
        } catch (Exception e) {

        } finally {
            if (srcIn != null) {
                try {
                    srcIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    L.w(TAG, "method->copyFile,fail to close file: " + srcPath);
                }
            }

            if (desOut != null) {
                try {
                    desOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    L.w(TAG, "method->copyFile,fail to close file: " + desPath);
                }
            }
        }

    }
}
