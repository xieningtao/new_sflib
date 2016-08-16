package com.sf.Roboletic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.basesmartframe.baseui.BaseActivity;

/**
 * Created by NetEase on 2016/6/17 0017.
 */
public class LoginActivity extends BaseActivity implements LoginTask.UpdateLoginView{


    private LoginTask.LoginImpl mLoginImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mLoginImpl=new LoginPresenter(this);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userNameEt = (EditText) findViewById(R.id.username_et);
                EditText passWordEt = (EditText) findViewById(R.id.password_et);

                String userName = userNameEt.getText().toString();
                String passWord = passWordEt.getText().toString();
                mLoginImpl.login(userName,passWord);
            }

        });
    }

    @Override
    public void updateView(LoginTask.TestBean testBean) {

    }

}
