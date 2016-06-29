package com.sflib.CustomView.newhttpview;

import android.content.Context;
import android.view.ViewGroup;

import com.sf.utils.baseutil.NetWorkManagerUtil;


/**
 * Created by xieningtao on 15-9-15.
 */
public class HttpViewManager implements HttpViewAction {

    public static enum TVHttpViewType {
        NONE, LOADING_VIEW, NO_DATA_VIEW, NO_NETWORK_VIEW
    }

    private HttpViewHelper mActionHelper;

    private HttpViewManager(HttpViewFactory factory) {
        mActionHelper = new HttpViewHelper(factory);
    }

    public static HttpViewManager createManagerByDefault(Context context, ViewGroup rootView) {
        HttpViewFactory factory = new SimpleHttpViewFactory(context, rootView);
        return new HttpViewManager(factory);
    }

    public static HttpViewManager createManagerBy(HttpViewFactory tvHttpViewFactory) {
        return new HttpViewManager(tvHttpViewFactory);
    }

    @Override
    public void showHttpLoadingView(boolean hasData) {
        if (!hasData) {
            boolean httpResult = mActionHelper.showOnlyThisHttpView(HttpViewManager.TVHttpViewType.LOADING_VIEW);
        } else {
            mActionHelper.dismissAllHttpViews();
        }
    }

    @Override
    public void showHttpViewNOData(boolean hasData) {
        if (hasData) {
            mActionHelper.dismissAllHttpViews();
        } else if (NetWorkManagerUtil.isNetworkAvailable()) {
            boolean httpResult = mActionHelper.showOnlyThisHttpView(HttpViewManager.TVHttpViewType.NO_DATA_VIEW);
        } else {
            boolean httpResult = mActionHelper.showOnlyThisHttpView(HttpViewManager.TVHttpViewType.NO_NETWORK_VIEW);
        }
    }

    @Override
    public void showHttpViewNoNetwork(boolean hasData) {

        if (hasData) {
            mActionHelper.dismissAllHttpViews();
        } else if (!NetWorkManagerUtil.isNetworkAvailable()) {
            boolean httpResult = mActionHelper.showOnlyThisHttpView(HttpViewManager.TVHttpViewType.NO_NETWORK_VIEW);
        }

    }
}
