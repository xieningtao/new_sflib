package com.sf.SFSample.nybao;

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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseFragment;
import com.maxleap.GetCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYNewsDetailBean;
import com.sf.utils.baseutil.GsonUtil;
import com.sf.utils.baseutil.NetWorkManagerUtil;
import com.sflib.CustomView.newhttpview.HttpViewManager;

/**
 * Created by NetEase on 2016/10/10 0010.
 */
public class NYFragmentNewsDetail extends BaseFragment {
    public static final String NEWS_ID = "news_id";
    private WebView mWebView;
    private FrameLayout mErrorFl;
    private HttpViewManager mHttpViewManager;
    private TextView mTitleTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ny_topic_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            mHttpViewManager.showHttpLoadingView(false);
            doRequest();
        } else {
            mHttpViewManager.showHttpViewNoNetwork(false);
        }
    }

    private void initViews(View view) {
        mWebView = (WebView) view.findViewById(R.id.webview);
        mErrorFl = (FrameLayout) view.findViewById(R.id.error_fl);
        mTitleTv = (TextView) view.findViewById(R.id.news_detail_title_tv);
        mHttpViewManager = HttpViewManager.createManagerByDefault(getActivity(), mErrorFl);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
        mWebView.setBackgroundResource(R.drawable.transparent);

        webSettings.setBuiltInZoomControls(false);
        webSettings.setTextZoom(350);
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


    private void doRequest() {
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYNewsDetail");
        newsQuery.whereEqualTo("newsId", getNewsId());
        MLQueryManager.getFirstInBackground(newsQuery, new GetCallback<MLObject>() {
            @Override
            public void done(MLObject mlObject, MLException e) {
                if (e != null) {
                    mHttpViewManager.showHttpViewNOData(false);
                } else {
                    if (mlObject != null) {
                        String detailContent = mlObject.getString("detailContent");
                        NYNewsDetailBean detailBean = GsonUtil.parse(detailContent, NYNewsDetailBean.class);
                        mTitleTv.setText(detailBean.getTitle());
                        mWebView.loadDataWithBaseURL(null, detailBean.getContent(), "text/html", "utf-8", null);
                    }
                    mHttpViewManager.dismissAllHttpView();
                }
            }
        });
    }

    private String getNewsId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString(NEWS_ID);
        }
        return "";
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

}
