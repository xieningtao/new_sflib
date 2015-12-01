package com.basesmartframe.baseview.newhttpview;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.basesmartframe.log.L;


/**
 * Created by xieningtao on 15-9-15.
 */
public class HttpViewManager {

    public static enum HttpViewType {
        LOADING_VIEW, NO_DATA_VIEW, NO_NETWORK_VIEW
    }

    private final String TAG = getClass().getName();

    private SparseArray<BaseHttpView> mHttpViews = new SparseArray<>();

    private final ViewGroup mRootView;
    private final Context mContext;

    private HttpViewFactory mHttpViewFactory;

    private final HttpViewFactory mDefaultHttpViewFactory = new DefaultHttpViewFactory();
    ;

    public HttpViewManager(Context context, ViewGroup rootView) {
        this.mRootView = rootView;
        this.mContext = context;
    }

    public void setHttpViewFactory(HttpViewFactory factory) {
        this.mHttpViewFactory = factory;
    }

    public boolean isViewShowing(HttpViewType viewType) {
        if (viewType == null) return false;
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView == null) return false;
        return httpView.isShowing();
    }

    public boolean showHttpView(HttpViewType viewType) {
        if (isViewShowing(viewType)) return true;
        if (viewType == null) return false;
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView == null) {
            httpView = createHttpView(viewType);
            mHttpViews.put(viewType.ordinal(), httpView);
        }
        L.info(TAG, "httpView object addr: " + httpView);
        return httpView.showView();
    }

    public boolean showOnlyThisHttpView(HttpViewType viewType) {
        if (isViewShowing(viewType)) return true;
        mRootView.removeAllViews();
        return showHttpView(viewType);
    }

    //refactor dismissHttpView function and dismissAllHttpViews
    public boolean dismissHttpView(HttpViewType viewType) {
        if (!isViewShowing(viewType)) return true;
        if (viewType == null) return false;
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView.isShowing()) {
            return httpView.dismissView();
        } else {
            return false;
        }

    }

    private boolean dismissHtppViewHelper(HttpViewType viewType) {
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView == null) {
            return true;
        }
        return httpView.dismissView();
    }

    public void dismissAllHttpViews() {
        for (HttpViewType viewType : HttpViewType.values()) {
            if (isViewShowing(viewType)) {
                dismissHtppViewHelper(viewType);
            }
        }
    }

    public boolean removeHttpView(HttpViewType viewType) {
        if (viewType == null) return false;
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView == null) return true;
        return httpView.removeView();
    }

    public void removeAllHttpViews() {
        for (HttpViewType viewType : HttpViewType.values()) {
            removeHttpView(viewType);
        }
    }

    private BaseHttpView createHttpView(HttpViewType viewType) {
        if (mHttpViewFactory == null) {
            mHttpViewFactory = mDefaultHttpViewFactory;
        }
        switch (viewType) {
            case LOADING_VIEW:
                return mHttpViewFactory.createLoadingHttpView(mContext, mRootView);
            case NO_DATA_VIEW:
                return mHttpViewFactory.createNoDataView(mContext, mRootView);
            case NO_NETWORK_VIEW:
                return mHttpViewFactory.createNoNetworkView(mContext, mRootView);
            default:
                //never run this
                return null;
        }
    }

    public static class DefaultHttpViewFactory extends HttpViewFactory {

        @Override
        public BaseHttpView createLoadingHttpView(Context context, ViewGroup rootView) {
            return new HttpLoadingView(context, rootView);
        }

        @Override
        public BaseHttpView createNoNetworkView(Context context, ViewGroup rootView) {
            return new HttpNoNetworkView(context, rootView);
        }

        @Override
        public BaseHttpView createNoDataView(Context context, ViewGroup rootView) {
            return new HttpNoDataView(context, rootView);
        }
    }
}
