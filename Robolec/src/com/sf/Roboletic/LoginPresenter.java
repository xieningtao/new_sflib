package com.sf.Roboletic;

import android.text.TextUtils;

import com.basesmartframe.request.SFHttpGsonHandler;
import com.sf.httpclient.newcore.MethodType;
import com.basesmartframe.request.SFHttpRequestImpl;
import com.sf.httpclient.newcore.SFHttpStringCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.basesmartframe.request.SFResponseCallback;
import com.sf.httpclient.core.AjaxParams;

/**
 * Created by NetEase on 2016/7/18 0018.
 */
public class LoginPresenter implements LoginTask.LoginImpl {

    private  LoginTask.UpdateLoginView updateLoginView;
    private TestResponse mTestResponse;

    public LoginPresenter(LoginTask.UpdateLoginView updateLoginView) {
        this.updateLoginView = updateLoginView;
        mTestResponse=new TestResponse();
    }


    public int login(String userName, String password) {

        if (TextUtils.isEmpty(userName)) return -1;
        if (TextUtils.isEmpty(password)) return -1;

        String httpUrl = "http://news.baidu.com/";
        SFRequest _request = new SFRequest(httpUrl, MethodType.GET) {
            @Override
            public Class getClassType() {
                return LoginTask.TestBean.class;
            }
        };
        SFHttpGsonHandler handler = new SFHttpGsonHandler(_request,mTestResponse);
        handler.start();
        return 1;
    }

    public TestResponse getTestResponse() {
        return mTestResponse;
    }

    public  class TestResponse extends SFHttpStringCallback<LoginTask.TestBean> {


        private void isEmpty() {

        }

        private void isOk() {

        }

        @Override
        public void onSuccess(SFRequest request, LoginTask.TestBean g) {
            updateLoginView.updateView(g);
            if (g == null) {
                isEmpty();
            } else {
                isOk();
            }
            System.out.println("onsuccess,g: " + g);
        }

        @Override
        public void onFailed(SFRequest request, Exception e) {
            System.out.println("onFailed");
        }
    }
}
