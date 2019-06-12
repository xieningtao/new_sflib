package com.basesmartframe.okhttp;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 后端相关token校验逻辑wiki: http://wiki.y.163.com/pages/viewpage.action?pageId=3640697
 * 如果有涉及文件下载不能用这个拦截器，因为会读取返回结果解析是否token失效等，如果文件太大会有性能问题。
 * Created by yuhengye g10475 on 2018/5/25.
 **/
public class HTHttpInterceptor implements Interceptor {

    public static final int HTTP_ERROR_MESSAGE = 9999;
    public static final String HTTP_ERROR_KEY = "http_error_key";
    /**
     * 每次刷新token前更新refreshToken为false，避免多个请求同时请求获取token。
     */
    private static volatile boolean refreshToken = false;

    /**
     * 加锁处理，避免多个请求同时刷新token
     *
     * @return
     */
    private static synchronized boolean refreshToken() {
        if (refreshToken) {
            return true;
        }
        refreshToken = IHttpHandler.Default.getHandler().refreshToken();
        return refreshToken;
    }

    /**
     * @param originalRequest
     * @param builder
     * @param addToken        是否增加token
     * @param checkExistToken 检查是否已经存在token，true则检查，如果存在token则不再添加
     */
    private static void setupParam(Request originalRequest, Request.Builder builder, boolean addToken, boolean checkExistToken) {
        if ("GET".equals(originalRequest.method())) {
            //get
            if (addToken) {
                HttpUrl.Builder urlBuilder = originalRequest.url().newBuilder();

                if (!checkExistToken || originalRequest.url().queryParameter(HTHttpConstants.ACCESS_TOKEN) == null) {
                    urlBuilder.setQueryParameter(HTHttpConstants.ACCESS_TOKEN, httpToken());
                }
                builder.url(urlBuilder.build());
            }
        } else {
            //post
            RequestBody requestBody = originalRequest.body();
            if (requestBody == null) {
                return;
            }

            String token = accessToken();
            if (requestBody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBody;
                FormBody.Builder bodyBuilder = new FormBody.Builder();

                boolean hasDeviceInfo = false;
                boolean hasTokenInfo = false;
                String name;
                for (int i = 0; i < formBody.size(); i++) {
                    name = formBody.name(i);
                    if (!hasDeviceInfo && "deviceInfo".equals(name)) {
                        hasDeviceInfo = true;
                    }
                    if (HTHttpConstants.ACCESS_TOKEN.equals(name)) {
                        if (addToken && !checkExistToken) {
                            //如果是刷新token，这里不能继续添加以前的旧token
                            continue;
                        } else if (checkExistToken && !hasTokenInfo) {
                            hasTokenInfo = true;
                        }
                    }
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                builder.method(originalRequest.method(), bodyBuilder.build());
            } else if (requestBody instanceof MultipartBody) {

                //Multipart的请求方式暂未发现有用到

//                if (addToken) {
//                    MultipartBody multipartBody = (MultipartBody) requestBody;
//                    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
//                    for (int i = 0; i < multipartBody.size(); i++) {
//                        bodyBuilder.addPart(multipartBody.part(i));
//                    }
//                    bodyBuilder.addFormDataPart(HTHttpConstants.ACCESS_TOKEN, token);
//                    builder.method(originalRequest.method(), bodyBuilder.build());
//                }
            }

            if (addToken) {

                HttpUrl.Builder urlBuilder = originalRequest.url().newBuilder();

                if (!checkExistToken || originalRequest.url().queryParameter(HTHttpConstants.ACCESS_TOKEN) == null) {
                    urlBuilder.setQueryParameter(HTHttpConstants.ACCESS_TOKEN, accessToken());
                }
                builder.url(urlBuilder.build());

            }
        }

    }

    private static String accessToken() {
        return "";
    }

    private static String httpToken() {
        return "";
    }

    private static void setupHeader(Request originalRequest, Request.Builder builder) {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        //通过自定义header字段来标识是否需要处理token，默认处理
        boolean ignoreToken = Boolean.parseBoolean(originalRequest.header(HTHttpConstants.IGNORE_TOKEN));
        //填充基本参数
        setupParam(originalRequest, builder, !ignoreToken, true);
        //填充header
        setupHeader(originalRequest, builder);

        Response originalResponse = chain.proceed(builder.build());

        //HTTP status code
        //originalResponse.code()

        //token失效时的处理

        // 获取返回的数据字符串
        ResponseBody responseBody = originalResponse.body();
        if (responseBody == null) {
            return originalResponse;
        }

        BufferedSource source = responseBody.source();
        source.request(Integer.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = null;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        String result = buffer.clone().readString(charset == null ? Util.UTF_8 : charset);

        HashMap<String, Object> map = HTHttpUtils.getResultCode(result);
        boolean isTokenExpired = false;
        if (HTHttpUtils.getResultCode(map) == 521) {
            isTokenExpired = true;
        }

        if (isTokenExpired) {
            //Token过期处理
        }

        //token过期测试
//        isTokenExpired = true;

        // 如果 token 已经过期
        if (!ignoreToken && isTokenExpired) {
            //handle refresh token
            refreshToken = false;
            if (refreshToken()) {//重新刷新token
                setupParam(originalRequest, builder, true, false);

                //close old body
                responseBody.close();

                //return new request
                return chain.proceed(builder.build());
            } else {
                IHttpHandler.Default.getHandler().onTokenUnAvailable();
            }
        }
//        int resultCode = HTHttpUtils.getResultCode(map);
//        IHttpHandler.Default.getHandler().checkValidate(resultCode, HashMapUtils.getInt(map, HTHttpUtils.FORBIDDEN_STATUS, -1));

        return originalResponse;
    }

    /**
     * 后续想要跳过提醒的code可以在这里添加
     *
     * @param map
     * @return
     */
    private boolean isSpecialCode(HashMap<String, Object> map) {
        int code = HTHttpUtils.getResultCode(map);
        return false;
    }
}
