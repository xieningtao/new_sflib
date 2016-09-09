package com.sf.SFSample.ui;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.request.SFHttpFileHandler;
import com.basesmartframe.updateutil.UpdateService;
import com.sf.SFSample.R;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpFileCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;

import java.io.File;

/**
 * Created by NetEase on 2016/7/26 0026.
 */
public class ActivityUpgrade extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_layout);
        findViewById(R.id.download_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateService updateService = new UpdateService(ActivityUpgrade.this);
                String url = "http://img.boqiicdn.com/Data/BK/A/1607/25/imagick14651469425193_y.jpg";//"https://raw.githubusercontent.com/xieningtao/documents/master/emoji/emojiFileBeen.txt"
                updateService.start(url);
            }
        });
        findViewById(R.id.download_file_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://img.boqiicdn.com/Data/BK/A/1607/25/imagick14651469425193_y.jpg";
                SFRequest downloadRequest = new SFRequest(url, MethodType.GET) {
                    @Override
                    public Class getClassType() {
                        return null;
                    }
                };

                File file = SFFileHelp.createFileOnSD("sf_download", "test.jpg");
                if (file != null) {
                    SFHttpFileHandler fileHandler = new SFHttpFileHandler(downloadRequest, file.getAbsolutePath(), new SFHttpFileCallback<File>() {
                        @Override
                        public void onStart(SFRequest request) {
                            super.onStart(request);
                            L.info(TAG, "onStart");
                        }

                        @Override
                        public void onSuccess(SFRequest request, File g) {
                            L.info(TAG, "onSuccess ");
                        }

                        @Override
                        public void callBack(SFRequest request, long count, long current) {
                            super.callBack(request, count, current);
                            L.info(TAG, "callBack count: " + count + " current: " + current);
                        }

                        @Override
                        public void onFailed(SFRequest request, Exception e) {
                            L.info(TAG, "onFailed exception: " + e);
                        }
                    });
                    fileHandler.start();
                }
            }
        });
    }
}
