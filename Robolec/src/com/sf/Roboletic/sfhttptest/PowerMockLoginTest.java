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
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricGradleTestRunner.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@Config(constants = BuildConfig.class, sdk = 21)
@PrepareForTest({LoginPresenter.class})
public class PowerMockLoginTest {
    private final String TAG = getClass().getName();


    @Test
//    @PrepareForTest({LoginPresenter.class, TextUtils.class})
    public void testDoLogin() {
        LoginPresenter presenter = new LoginPresenter(PowerMockito.mock(LoginTask.UpdateLoginView.class));
        LoginPresenter mockPresent = PowerMockito.spy(presenter);
        final LoginPresenter.TestResponse response = presenter.getTestResponse();

        final LoginPresenter.TestResponse spyResponse = PowerMockito.spy(response);
        SFHttpGsonHandler handler = new SFHttpGsonHandler(null, response);
        SFHttpGsonHandler mockHandler = PowerMockito.mock(handler.getClass());
        try {
            PowerMockito.whenNew(SFHttpGsonHandler.class).withAnyArguments()
                    .thenReturn(mockHandler);

            PowerMockito.doAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    LoginTask.TestBean testBean = new LoginTask.TestBean();
                    testBean.setmName("ning tao");

                    spyResponse.onSuccess(null, testBean);

                    PowerMockito.verifyPrivate(spyResponse, Mockito.times(1)).invoke("isOk");
                    return null;
                }
            }).when(mockHandler).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception: " + e);
        }


        mockPresent.login("1111", "123456");
        Mockito.verify(mockHandler).start();
    }
//
//
//    @Test
//    public void testRoboletricHttp() {
//        FakeHttpLayer fakeHttpLayer = FakeHttp.getFakeHttpLayer();
//        fakeHttpLayer.addPendingHttpResponse(200, "OK");
//        System.out.println(
//                ((HttpUriRequest) fakeHttpLayer.getSentHttpRequestInfo(0)).getURI().toString()
//        );
//    }

    @Test
    public void testLoginActivity() {

        LoginPresenter presenter = new LoginPresenter(PowerMockito.mock(LoginTask.UpdateLoginView.class));
        LoginPresenter mockPresent = (LoginPresenter) PowerMockito.mock(presenter.getClass());
//        try {
//            PowerMockito.whenNew(LoginPresenter.class).withAnyArguments()
//                    .thenReturn(mockPresent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("exception: "+e);
//        }

        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText userNameEt = (EditText) loginActivity.findViewById(R.id.username_et);
        EditText passwordEt = (EditText) loginActivity.findViewById(R.id.password_et);
        Button submit= (Button) loginActivity.findViewById(R.id.login);

        loginActivity.setLoginImpl(mockPresent);
        userNameEt.setText("123456");
        passwordEt.setText("111111");

        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("it is invoked");
                return null;
            }
        }).when(mockPresent).login("123456","111111");

        submit.performClick();


    }
}
