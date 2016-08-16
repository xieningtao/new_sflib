package com.basesmartframe.request;

import com.sf.httpclient.core.AjaxParams;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by NetEase on 2016/8/15 0015.
 */
public class SFHttpHelper {

    public static String getUrlWithQueryString(String url, AjaxParams params) {
        if (params != null) {
            String paramString = params.getParamString();
            url += "?" + paramString;
        }
        return url;
    }

    public static HttpEntity paramsToEntity(AjaxParams params) {
        HttpEntity entity = null;
        if (params != null) {
            entity = params.getEntity();
        }
        return entity;
    }

    public static HttpUriRequest getHttpUriRequest(SFRequest request) {
        if (request == null) {
            return null;
        }
        HttpUriRequest httpUriRequest = null;
        if (request.getMethodType() == MethodType.GET) {
            HttpGet httpGet = new HttpGet(SFHttpHelper.getUrlWithQueryString(request.mUrl, request.getAjaxParams()));
            httpUriRequest = httpGet;
        } else if (request.getMethodType() == MethodType.POST) {
            HttpPost httpPost = new HttpPost(request.mUrl);
            httpPost.setEntity(SFHttpHelper.paramsToEntity(request.getAjaxParams()));
            httpUriRequest = httpPost;
        }
        return httpUriRequest;
    }
}
