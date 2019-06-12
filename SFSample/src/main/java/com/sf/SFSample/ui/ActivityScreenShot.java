package com.sf.SFSample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFToast;

/**
 * Created by mac on 17/1/17.
 */

public class ActivityScreenShot extends BaseActivity {

    private TextView mBikeNumberTv;
    private EditText mBikePasswordEt;
    private String mBikeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceent_shot);
        mBikeNumberTv = (TextView) findViewById(R.id.bike_number_tv);
        mBikePasswordEt= (EditText) findViewById(R.id.bike_pwd_et);
        Intent intent = getIntent();
        if (intent != null) {
            mBikeNumber = intent.getStringExtra(OfOLanucher.BIKE_NUMBER);
            mBikeNumberTv.setText(mBikeNumber);
        }

        findViewById(R.id.upload_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBikePasswordEt.getText())){
                    SFToast.showToast("请输入ofo的密码");
                    return;
                }
                uploadBikeInfo(mBikeNumber,mBikePasswordEt.getText().toString());
            }
        });
    }

    private void uploadBikeInfo(String bikeNumber,String pwd){
        MLObject pwdObject=MLObject.create("BikePwd");
        pwdObject.put("bikeNumber",bikeNumber);
        pwdObject.put("password",pwd);
        MLDataManager.saveInBackground(pwdObject, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if(e==null){
                    SFToast.showToast("保存数据成功");
                }else {
                    L.error(TAG,"exception: "+e);
                    SFToast.showToast("保存数据失败");
                }
            }
        });
    }
}
