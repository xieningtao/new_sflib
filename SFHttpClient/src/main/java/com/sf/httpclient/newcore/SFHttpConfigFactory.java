package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpConfigFactory {
    public static int maxConnections = 10; //http请求最大并发连接数
    public static int socketTimeout = 10 * 1000; //超时时间，默认10秒
    public static int maxRetries = 1;//错误尝试次数，错误异常表请在RetryHandler添加
    public static int httpThreadCount = 3;//http线程池数量


    public static SFHttpConfig  createDefaultHttpConfig(){
        return new SFHttpConfig(socketTimeout,maxConnections,maxRetries);
    }
}
