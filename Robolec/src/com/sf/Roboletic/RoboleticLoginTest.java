package com.sf.Roboletic;

import com.basesmartframe.request.SFHttpRequestImpl;
import com.basesmartframe.request.SFRequest;
import com.basesmartframe.request.SFResponse;
import com.basesmartframe.request.SFResponseCallback;
import com.nostra13.universalimageloader.utils.L;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.regex.Matcher;

import static org.mockito.Mockito.*;

/**
 * Created by NetEase on 2016/7/18 0018.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class RoboleticLoginTest {
    private String TAG=getClass().getName();

    @Test
    @PrepareForTest(LoginPresenter.class)
    public void testLogin(){
        MockitoAnnotations.initMocks(this);
        LoginPresenter loginPresenter=new LoginPresenter(new LoginTask.UpdateLoginView() {
            @Override
            public void updateView(LoginTask.TestBean testBean) {
                System.out.println("updateView: "+testBean);
            }
        });
        LoginPresenter mockloginPresenter= PowerMockito.spy(loginPresenter);

        try {
            SFHttpRequestImpl sfHttpRequest=PowerMockito.mock(SFHttpRequestImpl.class);
            PowerMockito.whenNew(SFHttpRequestImpl.class).withNoArguments().thenReturn(sfHttpRequest);

            PowerMockito.doAnswer(new Answer<String>() {
                @Override
                public String answer(InvocationOnMock invocation) throws Throwable {
                    System.out.println("InvocationOnMock");
                    Object object=invocation.getArguments()[1];
                    SFResponseCallback callback= (SFResponseCallback) object;
                    LoginTask.TestBean testbean=new LoginTask.TestBean();
                    testbean.setmName("this is a test");
                    callback.onResult(true,testbean);
                    return null;
                }
            }).when(sfHttpRequest).getData((SFRequest) Mockito.anyObject(),(SFResponseCallback)Mockito.anyObject());
            mockloginPresenter.login("123","456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
