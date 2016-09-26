package com.sf.Roboletic.sfhttptest;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.Roboletic.BuildConfig;
import com.sf.Roboletic.LoginActivity;
import com.sf.Roboletic.LoginPresenter;
import com.sf.Roboletic.LoginTask;
import com.sf.Roboletic.R;
import com.sf.Roboletic.SFPowerRunner;
import com.sf.Roboletic.SFRoboletricTestRunner;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.loglib.L;
import com.sflib.reflection.core.ThreadHelp;

import junit.framework.Assert;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.Shadow;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.httpclient.DefaultRequestDirector;
import org.robolectric.shadows.httpclient.FakeHttp;
import org.robolectric.shadows.httpclient.FakeHttpLayer;
import org.robolectric.shadows.httpclient.ShadowDefaultRequestDirector;
import org.robolectric.shadows.httpclient.Shadows;
import org.robolectric.shadows.httpclient.TestHttpResponse;
import org.robolectric.util.Strings;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by NetEase on 2016/7/21 0021.
 */
@RunWith(CustomSFHttpTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PowerMockLoginTest {
    private final String TAG = getClass().getName();


    @Test
    public void testLoginActivity() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText userNameEt = (EditText) loginActivity.findViewById(R.id.username_et);
        EditText passWordEt = (EditText) loginActivity.findViewById(R.id.password_et);

        userNameEt.setText("xieningtao");
        passWordEt.setText("123456");

        Assert.assertEquals(userNameEt.getText().toString(), "xieningtao");
        Assert.assertEquals(passWordEt.getText().toString(), "123456");

        Button loginBt = (Button) loginActivity.findViewById(R.id.login);
        loginBt.performClick();
    }

    public void testLoginPresenter(){
        LoginPresenter loginPresenter=Mockito.spy(new LoginPresenter(null));
        loginPresenter.login("xieningtao","123456");
    }
}
