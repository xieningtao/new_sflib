package com.basesmartframe.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuhengye g10475 on 2018/6/1.
 **/
public class CommonHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        builder.header("User-agent", HTHttpConstants.getUserAgent());
        return chain.proceed(builder.build());
    }
}
