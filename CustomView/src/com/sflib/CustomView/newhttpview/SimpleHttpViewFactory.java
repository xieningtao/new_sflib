package com.sflib.CustomView.newhttpview;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by xieningtao on 15-12-29.
 */
public class SimpleHttpViewFactory extends HttpViewFactory {
    public SimpleHttpViewFactory(Context context, ViewGroup rootView) {
        super(context, rootView);
    }

    @Override
    protected BaseHttpView createHttpView(HttpViewManager.TVHttpViewType viewType) {
        switch (viewType) {
            case NONE:
                //never run this;
                return null;
            case LOADING_VIEW:
                return new HttpLoadingView(mContext, mRootView);
            case NO_DATA_VIEW:
                return new HttpNoDataView(mContext, mRootView);
            case NO_NETWORK_VIEW:
                return new HttpNoNetworkView(mContext, mRootView);
            default:
                //never run this
                return null;
        }
    }
}
