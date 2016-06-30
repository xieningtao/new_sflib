package com.basesmartframe.request;

import android.text.TextUtils;

import com.basesmartframe.basehttp.SFHttpClient;
import com.google.gson.Gson;
import com.sf.loglib.L;
import com.sf.httpclient.core.AjaxCallBack;
import com.sf.httpclient.core.AjaxParams;

/**
 * Created by xieningtao on 16-5-20.
 */
public class SFHttpRequestImpl implements SFRequestAction {

    private final String TAG = getClass().getName();

    private class SFNetworkCallback extends AjaxCallBack<String> {

        private final SFRequest mRequest;
        private final SFResponseCallback mCallback;

        public SFNetworkCallback(SFRequest request, SFResponseCallback callback) {
            this.mRequest = request;
            this.mCallback = callback;
        }

        @Override
        public void onStart() {
            super.onStart();
            L.debug(TAG, "onStart");
            if (mCallback != null) {
                mCallback.onStart(mRequest.getAjaxParams());
            }
        }

        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            L.info(TAG, "onSuccess");
            String tempResult = t == null ? "" : t;
            L.debug(TAG, "result: " + tempResult);
            Object bean = null;
            if (!TextUtils.isEmpty(t)) {
                try {
                    Gson gson = new Gson();
                    bean = gson.fromJson(t, mRequest.getClassType());
                } catch (Exception e) {
                    L.error(TAG, "fail to parse json : " + t);
                    bean = null;
                }
            }
            if (mCallback != null) {
                mCallback.onResult(true, bean);
            }
        }

        @Override
        public void onLoading(long count, long current) {
            super.onLoading(count, current);
            L.debug(TAG, "onLoading");
            if (mCallback != null) {
                mCallback.onLoading(count, current);
            }
        }

        @Override
        public void onFailure(Throwable t, int errorNo, String strMsg) {
            super.onFailure(t, errorNo, strMsg);
            L.error(TAG, "onFailure: exception: " + t + " errorNO: " + errorNo
                    + " strMsg: " + strMsg);
            if (mCallback != null) {
                mCallback.onResult(false, null);
            }
        }

    }

    /**
     * @param request
     * @param callback
     * @return -1 fail to call this function otherwise successful
     */
    @Override
    public int getData(SFRequest request, SFResponseCallback callback) {
        if (request == null) {
            L.error(TAG, "method->getData request is null");
            return -1;
        }
        SFNetworkCallback netCallback = new SFNetworkCallback(request, callback);
        String url = request.mUrl;
        AjaxParams params = request.getAjaxParams();
        if (TextUtils.isEmpty(url)) {
            L.error(TAG, "method->getData,url is null or empty");
            return -1;
        }
        if (request.getMethodType() == MethodType.GET) {
            SFHttpClient.get(url, params, netCallback);
        } else if (request.getMethodType() == MethodType.POST) {
            SFHttpClient.post(url, params, netCallback);
        } else {
            String methodName = "null";
            if (request.getMethodType() != null) {
                methodName = request.getMethodType().name();
            }
            L.error(TAG, "method->getData,methodType is illegal,methodType: " + methodName);
            return -1;
        }
        return 0;
    }

}
