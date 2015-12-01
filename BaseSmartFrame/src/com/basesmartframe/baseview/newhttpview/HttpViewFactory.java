package com.basesmartframe.baseview.newhttpview;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by xieningtao on 15-11-26.
 */
public abstract class HttpViewFactory {

    public abstract BaseHttpView createLoadingHttpView(Context context, ViewGroup rootView);

    public abstract BaseHttpView createNoNetworkView(Context context, ViewGroup rootView);

    public abstract BaseHttpView createNoDataView(Context context, ViewGroup rootView);
}
