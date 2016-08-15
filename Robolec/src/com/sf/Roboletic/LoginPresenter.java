package com.sf.Roboletic;

import android.text.TextUtils;

import com.basesmartframe.request.MethodType;
import com.basesmartframe.request.SFHttpRequestImpl;
import com.basesmartframe.request.SFRequest;
import com.basesmartframe.request.SFResponseCallback;
import com.sf.httpclient.core.AjaxParams;

import static com.sf.Roboletic.LoginActivity.*;

/**
 * Created by NetEase on 2016/7/18 0018.
 */
public class LoginPresenter implements LoginTask.LoginImpl {

    private  static LoginTask.UpdateLoginView updateLoginView;

    public LoginPresenter(LoginTask.UpdateLoginView updateLoginView) {
        this.updateLoginView = updateLoginView;
    }


    public int login(String userName, String password) {

        if(TextUtils.isEmpty(userName))return -1;
        if(TextUtils.isEmpty(password))return -1;

        String httpUrl = "http://news.baidu.com/";

        SFHttpRequestImpl request = new SFHttpRequestImpl();

        SFRequest _request = new SFRequest(httpUrl, MethodType.GET) {
            @Override
            public Class getClassType() {
                return LoginTask.TestBean.class;
            }
        };
       return request.getData(_request, new TestResponse());

    }

      public static class TestResponse implements SFResponseCallback<LoginTask.TestBean> {

        @Override
        public void onResult(boolean success, LoginTask.TestBean bean) {
            updateLoginView.updateView(bean);
        }

        @Override
        public void onStart(AjaxParams params) {

        }

        @Override
        public void onLoading(long count, long current) {

        }
    }
}
