package com.sf.SFSample.nybao;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sflib.CustomView.baseview.EditTextClearDroidView;

/**
 * Created by NetEase on 2016/11/29 0029.
 */

public class NYLoginActivity extends BaseActivity {

    private Button mLoginBt;
    private EditTextClearDroidView mUserName;
    private EditTextClearDroidView mPwd;
    private TextView mRegisterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_login_activity);
        initView();
    }

    private void initView() {
        mLoginBt = (Button) findViewById(R.id.login_bt);
        mUserName = (EditTextClearDroidView) findViewById(R.id.login_atv);
        mPwd = (EditTextClearDroidView) findViewById(R.id.pwd_cdv);
        mRegisterTv = (TextView) findViewById(R.id.register_tv);

        mLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NYLoginActivity.this, NYRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mPwd.getEditText().setHint(R.string.pwd_input_hint);
        mPwd.getEditText().addTextChangedListener(mTextWatcher);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            updateLoginState();
        }
    };

    private void updateLoginState() {
        if (!TextUtils.isEmpty(mUserName.getEditText().getText()) && !TextUtils.isEmpty(mPwd.getEditText().getText())) {
            mLoginBt.setEnabled(true);
        } else {
            mLoginBt.setEnabled(false);
        }
    }

}
