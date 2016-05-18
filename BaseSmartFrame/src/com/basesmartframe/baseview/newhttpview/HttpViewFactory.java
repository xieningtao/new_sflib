package com.basesmartframe.baseview.newhttpview;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by xieningtao on 15-12-29.
 */
public abstract class HttpViewFactory {
    private final String TAG = getClass().getName();
    private final HttpViewHelper mTVHttpViewActionHelper = new HttpViewHelper(this);

    private SparseArray<BaseHttpView> mHttpViews = new SparseArray<>();

    public final ViewGroup mRootView;
    public final Context mContext;

    public HttpViewFactory(Context context, ViewGroup rootView) {
        this.mRootView = rootView;
        this.mContext = context;
    }

    public BaseHttpView createOrGetHttpView(HttpViewManager.TVHttpViewType viewType) {
        BaseHttpView httpView = mHttpViews.get(viewType.ordinal());
        if (httpView == null) {
            httpView = createHttpView(viewType);
            mHttpViews.put(viewType.ordinal(), httpView);
        }

        return httpView;
    }

    abstract protected BaseHttpView createHttpView(HttpViewManager.TVHttpViewType viewType);

}
