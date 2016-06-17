package com.sf.robotium;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.basesmartframe.baseui.BaseActivity;

/**
 * Created by NetEase on 2016/6/17 0017.
 */
public class LoginActivity extends BaseActivity{

    private String mContent="11";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final EditText input1= (EditText) findViewById(R.id.input1);
        final EditText input2= (EditText) findViewById(R.id.input2);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1=input1.getText().toString();
                String str2=input2.getText().toString();
                mContent=str1+str2;
            }
        });
    }

    public String getmContent(){
        return mContent;
    }
}
