package com.sf.SFSample.nybao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.basesmartframe.baseui.BaseActivity;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.SignUpCallback;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFToast;
import com.sflib.CustomView.baseview.EditTextClearDroidView;

/**
 * Created by NetEase on 2016/11/29 0029.
 */

public class NYRegisterActivity extends BaseActivity {
    private EditTextClearDroidView mUserName;
    private EditTextClearDroidView mPwd;

    private Button mRegister;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_register_activity);
        initial();
    }

    private void initial() {
        mUserName = (EditTextClearDroidView) findViewById(R.id.phoneNum_atv);
        mPwd = (EditTextClearDroidView) findViewById(R.id.pwd_cdv);
        mRegister = (Button) findViewById(R.id.register_bt);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mUserName.getEditText().getText())) {
                    SFToast.showToast(R.string.user_name_input_hint);
                    return;
                }

                if (TextUtils.isEmpty(mPwd.getEditText().getText())) {
                    SFToast.showToast(R.string.pwd_input_hint);
                    return;
                }
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(NYRegisterActivity.this);
                }
                mProgressDialog.show();
                String userName = mUserName.getEditText().getText().toString();
                String pwd = mPwd.getEditText().getText().toString();
                doRegister(userName, pwd);
            }
        });
    }

    private void doRegister(String userName, String pwd) {
        MLUser user = new MLUser();
        user.setUserName(userName);
        user.setPassword(pwd);
        MLUserManager.signUpInBackground(user, new SignUpCallback() {
            public void done(MLException e) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                if (e == null) {
                    // 注册成功
                    SFToast.showToast(R.string.register_success_tip);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // 注册失败
                    SFToast.showToast(R.string.register_fail_tip);
                    L.error(TAG, "doRegister exception: " + e);
                }
            }
        });
    }
}
