package com.sf.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.sf.loglib.L;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Created by NetEase on 2016/6/17 0017.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo mSolo;
    private LoginActivity mLoginActivity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLoginActivity=getActivity();
        mSolo=new Solo(getInstrumentation(),mLoginActivity);
    }

    public void testLogin(){
        Button login= (Button) mSolo.getView(R.id.login);

        EditText input1= (EditText) mSolo.getView(R.id.input1);
        EditText input2= (EditText) mSolo.getView(R.id.input2);

        mSolo.clearEditText(input1);
        mSolo.clearEditText(input2);

        mSolo.enterText(input1,"hello");
        mSolo.enterText(input2,"xie");

        mSolo.clickOnView(login);

        synchronized (mSolo) {
            try {
                mSolo.wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //要用好wait函数，异步的问题
        String content=mLoginActivity.getmContent();
        L.info("LoginActivityTest",content);
        Assert.assertTrue("helloxie".equals(content));

    }
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
