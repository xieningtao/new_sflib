package com.sf.Roboletic.httptest;

/**
 * Created by NetEase on 2016/8/16 0016.
 */

import android.net.Uri;


import com.sflib.reflection.core.ThreadHelp;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by miguel on 12/02/14.
 */
public class MyStandardRequest {
    public static final String TAG = MyStandardRequest.class.getSimpleName();

    private String word;

    public MyStandardRequest(String word) {
        this.word = word;
    }

    public String loadDataFromNetwork() throws Exception {
        // With Uri.Builder class we can build our url is a safe manner
        Uri.Builder uriBuilder = Uri.parse("http://robospice-sample.appspot.com/reverse").buildUpon();
        uriBuilder.appendQueryParameter("word", word);

        String url = uriBuilder.build().toString();

        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
                .openConnection();
        String result = new String(stream2Byte(urlConnection.getInputStream()), "utf-8");
        urlConnection.disconnect();

        return result;
    }

    private String mResult="";

    public String getResult() {
        return mResult;
    }

    public  void loadDataWithDefaultClient() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("loadDataWithDefaultClient");
                DefaultHttpClient client = new DefaultHttpClient();
                HttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());
                HttpResponse response = null;
                try {
                    response = client.execute(new HttpGet("http://robospice-sample.appspot.com/reverse?word=" + word), httpContext);
                    mResult = new String(stream2Byte(response.getEntity().getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("exception: "+e);
                }
            }
        }).start();
    }

    private byte[] stream2Byte(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inputStream.close();
        return data;
    }
}
