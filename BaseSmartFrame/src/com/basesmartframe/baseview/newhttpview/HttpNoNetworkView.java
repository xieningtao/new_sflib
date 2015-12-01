package com.basesmartframe.baseview.newhttpview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.R;


/**
 * Created by xieningtao on 15-9-16.
 */
public class HttpNoNetworkView extends BaseHttpView {
    public HttpNoNetworkView(Context context, ViewGroup rootView) {
        super(context, rootView);
    }

    @Override
    protected View onCreateView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.nonetwork_view,null);
    }
}
