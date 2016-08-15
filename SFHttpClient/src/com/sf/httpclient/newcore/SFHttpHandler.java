package com.sf.httpclient.newcore;

import com.sf.httpclient.core.RetryHandler;
import com.sf.httpclient.entity.EntityCallBack;
import com.sf.loglib.L;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

/**
 * Created by NetEase on 2016/8/12 0012.
 */
public class SFHttpHandler<T> extends SFTaskHandler<T> implements EntityCallBack{

    private final AbstractHttpClient httpClient;
    private final HttpContext httpContext;
    private HttpUriRequest mUriRequest;
    private final BaseHttpClientManager<T> clientManager;

    public SFHttpHandler() {
        SFHttpConfig sfHttpConfig = SFHttpConfigFactory.createDefaultHttpConfig();
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, sfHttpConfig.socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(sfHttpConfig.maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);

        HttpConnectionParams.setSoTimeout(httpParams, sfHttpConfig.socketTimeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, sfHttpConfig.socketTimeout);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, SFHttpConfig.DEFAULT_SOCKET_BUFFER_SIZE);

        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);

        httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        httpClient = new DefaultHttpClient(cm, httpParams);
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader(SFHttpConfig.HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(SFHttpConfig.HEADER_ACCEPT_ENCODING, SFHttpConfig.ENCODING_GZIP);
                }
//                for (String header : clientHeaderMap.keySet()) {
//                    request.addHeader(header, clientHeaderMap.get(header));
//                }
            }
        });

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return;
                }
                final Header encoding = entity.getContentEncoding();
                if (encoding != null) {
//                    for (HeaderElement element : encoding.getElements()) {
//                        if (element.getName().equalsIgnoreCase(SFHttpConfig.ENCODING_GZIP)) {
//                            response.setEntity(new InflatingEntity(response.getEntity()));
//                            break;
//                        }
//                    }
                }
            }
        });

        httpClient.setHttpRequestRetryHandler(new RetryHandler(sfHttpConfig.maxRetries));
        clientManager = createHttpClientManager(httpClient,httpContext);
        clientManager.setEntityCallBack(this);
        onClientManagerCreated(clientManager);
    }

    protected void onClientManagerCreated(BaseHttpClientManager clientManager){

    }

    protected BaseHttpClientManager createHttpClientManager(AbstractHttpClient client, HttpContext context){
        return null;
    }

    protected void setUriRequest(HttpUriRequest uriRequest) {
        mUriRequest = uriRequest;
    }

    @Override
    public T doInBackground() {
        try {
            return clientManager.sendRequest(mUriRequest);
        } catch (Exception e) {
            L.error(TAG, TAG + ".doInBackground exception: " + e);
        }
        return null;
    }

    @Override
    public void taskDone(T t) {

    }

    @Override
    public void callBack(long count, long current, boolean mustNoticeUI) {

    }
}
