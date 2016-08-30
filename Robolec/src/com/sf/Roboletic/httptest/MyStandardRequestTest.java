package com.sf.Roboletic.httptest;

/**
 * Created by NetEase on 2016/8/16 0016.
 */

import com.sf.Roboletic.BuildConfig;
import com.sf.Roboletic.SFRoboletricTestRunner;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.tools.ant.taskdefs.Sleep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.httpclient.FakeHttp;
import org.robolectric.util.Strings;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by maragues on 12/02/14.
 */
@RunWith(SFRoboletricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MyStandardRequestTest {
    MyStandardRequest request;

    private static final String WORD = "LEON";
    private static final String REVERSE_WORD = "NOEL";
    private static final String MOCKED_WORD = "MOCKED";

//    @Test
//    public void assertRealRequestReturnsExpectedResult() throws Exception {
//        String result = request.loadDataFromNetwork();
//
//        assertEquals(REVERSE_WORD, result);
//    }

    @Test
    public void httpRequestWasSent_ReturnsTrueIfRequestWasSent() throws IOException, HttpException {
//        makeRequest("http://example.com");

        assertTrue(FakeHttp.httpRequestWasMade());
    }

    private void makeRequest(String uri,String response) throws HttpException, IOException {
        FakeHttp.addPendingHttpResponse(200, response);

//        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
//            @Override
//            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
//                return 0;
//            }
//
//        };
//        org.apache.http.impl.client.DefaultRequestDirector requestDirector = new org.apache.http.impl.client.DefaultRequestDirector(null, null, null, connectionKeepAliveStrategy, null, null, null, null, null, null, null, null);
//
//        HttpResponse httpResponse=requestDirector.execute(null, new HttpGet(uri), null);
//        String result= Strings.fromStream(httpResponse.getEntity().getContent());
//        System.out.println("result: "+result);
    }



    @Test
    public void mockedRequestUsingRobolectric() throws Exception {
//        FakeHttp.getFakeHttpLayer().setDefaultHttpResponse(200, MOCKED_WORD);
//        makeRequest("http://robospice-sample.appspot.com/reverse",MOCKED_WORD);
//        FakeHttp.getFakeHttpLayer().interceptResponseContent(true);
//        FakeHttp.getFakeHttpLayer().interceptHttpRequests(true);
        FakeHttp.addPendingHttpResponse(200, MOCKED_WORD, new BasicHeader("Content-Type", "text/plain"));
        FakeHttp.getFakeHttpLayer().interceptResponseContent(false);
        FakeHttp.getFakeHttpLayer().interceptHttpRequests(true);
        request.loadDataWithDefaultClient();
        Thread.sleep(3000);
        String result =request.getResult();
        assertEquals(MOCKED_WORD, result);
    }

    @Before
    public void setUp() throws Exception {

        request = new MyStandardRequest(WORD);
    }

    @After
    public void tearDown() throws Exception {
    }
}
