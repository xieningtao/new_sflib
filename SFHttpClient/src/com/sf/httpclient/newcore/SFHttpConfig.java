package com.sf.httpclient.newcore;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpConfig {

    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8 * 1024; //8KB
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ENCODING_GZIP = "gzip";

    public final int socketTimeout;
    public final int maxConnections;
    public final int maxRetries;


    public SFHttpConfig(int socketTimeout, int maxConnections, int maxRetries) {
        this.socketTimeout = socketTimeout;
        this.maxConnections = maxConnections;
        this.maxRetries = maxRetries;
    }
}
