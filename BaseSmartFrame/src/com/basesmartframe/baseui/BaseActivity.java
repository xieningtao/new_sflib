package com.basesmartframe.baseui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.basehttp.BaseAjaxCallBack;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFBus;
import com.sflib.CustomView.newhttpview.HttpViewManager;
import com.umeng.analytics.MobclickAgent;


public class BaseActivity extends Activity {
    protected final String TAG = getClass().getName();

    /**
     * it has problem
     */
    private HttpViewManager mTVHttpViewManager;

    protected View mRootView;

    private OnBackPressListener mOnBackPressListener;

    public void setmOnBackPressListener(OnBackPressListener mOnBackPressListener) {
        this.mOnBackPressListener = mOnBackPressListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        SFBus.register(this);
        L.info(this, getClass().getName() + " register evenBus onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        SFBus.unregister(this);
        L.info(this, getClass().getName() + " unregister evenBus onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        boolean handle = false;
        if (mOnBackPressListener != null) {
            handle = mOnBackPressListener.onBackPressListener();
        }
        if (!handle) {
            super.onBackPressed();
        }
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
