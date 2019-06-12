package com.basesmartframe.okhttp;

import android.text.TextUtils;

import com.basesmartframe.BuildConfig;
import com.sf.loglib.L;
import com.xnt.sfhttp.StringConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuhengye g10475 on 2018/7/9.
 **/
public class HTHttpManager {

    private static OkHttpClient OKHTTP_CLIENT;
    private static OkHttpClient HT_OKHTTP_CLIENT;

    private static final long TIME_OUT_SHORT = BuildConfig.DEBUG ? 30_000 : 20_000;

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                L.info("OkHttpLog", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        HT_OKHTTP_CLIENT = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new HTHttpInterceptor())
                //建议使用默认的超时时间不要设置为60s太长
                .connectTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .build();
        OKHTTP_CLIENT = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new CommonHttpInterceptor())
                .connectTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT_SHORT, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 用于构造普通网络请求的httpClient，不需要对token，header等信息做额外处理
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        return OKHTTP_CLIENT;
    }

    /**
     * 用于构造花田网络请求的httpClient，增加了对token，header等信息的拦截处理
     *
     * @return
     */
    public static OkHttpClient getHTOkHttpClient() {
        return HT_OKHTTP_CLIENT;
    }


    private static Retrofit HT_Retrofit;

    public static Retrofit getHTRetrofit() {
        if (HT_Retrofit == null) {
            //Base URLs should always end in /
            getHTRetrofit("");
        }
        return HT_Retrofit;
    }

    public static Retrofit getHTRetrofit(String host) {
        if (TextUtils.isEmpty(host)) {
            throw new IllegalArgumentException("host must not null");
        }
        if (HT_Retrofit == null) {
            if (host == null) {
                host = "";
            } else if (host.length() > 1 && !"/".equals(host.substring(host.length() - 1, host.length()))) {
                host += "/";
            }

            HT_Retrofit = new Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(StringConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(HTHttpManager.getHTOkHttpClient())
                    .build();
        }
        return HT_Retrofit;
    }
}
