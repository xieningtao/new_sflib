package com.sf.SFSample.utils;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.loglib.SFLogUtils;
import com.sf.loglib.file.SFFileCreationUtil;
import com.sf.utils.ThreadHelp;
import com.sf.utils.baseutil.CommanDateFormat;
import com.sf.utils.baseutil.DateFormatHelp;
import com.sf.utils.baseutil.FileRWUtils;

import java.io.File;
import java.io.IOException;

public class FileWRActivity extends BaseActivity implements View.OnClickListener {
    private final String content = "ABC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filewr_layout);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.standard_bt:
                ThreadHelp.runInSingleBackThread(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        File file = SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT,
                                "file_standard", ".txt");
                        long size = FileRWUtils.writeFileByStandard(file.getPath(),content);
                        long duration = System.currentTimeMillis() - startTime;
                        L.info(TAG,String.format("standard write size: %d,duration: %d",size,duration));
                    }
                }, 0);

                break;
            case R.id.buffer_standard_bt:
                ThreadHelp.runInSingleBackThread(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        File file = SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT,
                                "file_buffer_standard", ".txt");
                        long size = FileRWUtils.writeFileByBufferStandard(file.getPath(),content);
                        long duration = System.currentTimeMillis() - startTime;
                        L.info(TAG,String.format("buffer_standard write size: %d,duration: %d",size,duration));
                    }
                }, 0);
                break;
            case R.id.nio_bt:
                ThreadHelp.runInSingleBackThread(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        File file = SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT,
                                "file_nio", ".txt");
                        try {
                           long size = FileRWUtils.writeFileByNio(file.getPath(),content);
                            long duration = System.currentTimeMillis() - startTime;
                            L.info(TAG,String.format("nio write size: %d,duration: %d",size,duration));
                        } catch (IOException e) {
                            L.exception(e);
                        }
                    }
                }, 0);
                break;
            case R.id.mmap_bt:
                ThreadHelp.runInSingleBackThread(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        File file = SFFileCreationUtil.createFile(CommanDateFormat.YYMMSS_FORMAT,
                                "file_mmp", ".txt");
                        try {
                            String filePath = file.getPath();
//                           String filePath = "/storage/emulated/0/file_mmp/200304180422.txt";
                           long size = FileRWUtils.writeFileByMMap(filePath,content);
                            long duration = System.currentTimeMillis() - startTime;
                            L.info(TAG,String.format("mmap write size: %d,duration: %d",size,duration));
//                            Thread.sleep(10000);
                            L.info(TAG,"read file");
//                            String content = FileRWUtils.readFileByMMap(filePath);
//                            String content = FileRWUtils.readFileByNio(filePath);

                        } catch (IOException e) {
                           L.exception(e);
                        }
                    }
                }, 0);
                break;
        }
    }
}
