package com.basesmartframe.baseui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.basesmartframe.baseview.newhttpview.HttpViewManager;
import com.basesmartframe.log.L;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

public class BaseActivity extends Activity {
    protected final String TAG = getClass().getName();

    /**
     * it has problem
     */
    private HttpViewManager mTVHttpViewManager;

    protected View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        EventBus.getDefault().register(this);
        L.info(this, getClass().getName() + " register evenBus onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        EventBus.getDefault().unregister(this);
        L.info(this, getClass().getName() + " unregister evenBus onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onEvent(Integer a) {

    }

    public void onEvent(BaseAjaxCallBack.HttpResult result) {
        result.mCallBack.onResult(result.bean, result.params);
    }

    //---http view doRefresh

    protected void showHttpLoadingView(boolean hasData) {
        if (mRootView instanceof ViewGroup) {
//            final ViewGroup viewGroup = (ViewGroup) mRootView;
//            if (mTVHttpViewManager == null) {
//                mTVHttpViewManager = new HttpViewManager(this, viewGroup);
//            }
//            if (!hasData) {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.LOADING_VIEW);
//                L.info(TAG, "method->showHttpLoadingView loading_view result: " + httpResult);
//            } else {
//                //TODO use this view ,change view later
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.LOADING_VIEW);
//                L.info(TAG, "method->showHttpLoadingView,loading_view result: " + httpResult);
//            }
        } else {
            L.error(TAG, "method->showHttpLoadingView,mrootView is not viewGroup");
        }
    }

    protected void updateHttpView(boolean hasData) {
        if (mRootView instanceof ViewGroup) {
//            final ViewGroup viewGroup = (ViewGroup) mRootView;
//            if (mTVHttpViewManager == null) {
//                mTVHttpViewManager = new HttpViewManager(this, viewGroup);
//            }
//            if (hasData) {
//                mTVHttpViewManager.dismissAllHttpViews();
//            } else if (NetWorkManagerUtil.isNetworkAvailable(this)) {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.NO_DATA_VIEW);
//                L.info(TAG, "method->showHttpViewNOData" + " no_data_view result: " + httpResult);
//            } else {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.NO_NETWORK_VIEW);
//                L.info(TAG, "method->showHttpViewNOData,no_network_view result: " + httpResult);
//            }
        } else {
            L.error(TAG, "method->changeViewNoNetwork,mrootView is not viewGroup");
        }
    }

    protected void showHttpViewNoNetwork(boolean hasData) {
        if (mRootView instanceof ViewGroup) {
//            final ViewGroup viewGroup = (ViewGroup) mRootView;
//            if (mTVHttpViewManager == null) {
//                mTVHttpViewManager = new HttpViewManager(this, viewGroup);
//            }
//
//            if (hasData) {
//                mTVHttpViewManager.dismissAllHttpViews();
//            } else if (!NetWorkManagerUtil.isNetworkAvailable(this)) {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.NO_NETWORK_VIEW);
//                L.info(TAG, "method->showHttpViewNOData,no_network_view result: " + httpResult);
//            }

        } else {
            L.error(TAG, "method->changeViewNoNetwork,mrootView is not viewGroup");
        }
    }
    //---http view end

}
