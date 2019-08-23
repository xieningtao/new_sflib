/**
 * @(#)SimpleWebFragment.java, 2014年7月8日. Copyright 2014 netease, Inc. All
 * rights reserved. Netease
 * PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.basesmartframe.baseui;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.basesmartframe.R;

/**
 * @author huangyifei
 */
public class BaseWebFragment extends BaseFragment {
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    public void initViews(View view) {
        mWebView = view.findViewById(R.id.webview);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.clearFocus();
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.setVisibility(View.VISIBLE);

            }

        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }


}
