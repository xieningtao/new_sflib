package com.basesmartframe.baseview.newhttpview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by xieningtao on 15-9-15.
 */
abstract public class BaseHttpView {
    private final ViewGroup mRootView;
    private final Context mContext;
    private View mContentView;

    public BaseHttpView(Context context, ViewGroup rootView) {
        this.mContext = context;
        this.mRootView = rootView;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mContentView = onCreateView(layoutInflater);
    }

    protected abstract View onCreateView(LayoutInflater layoutInflater);

    boolean showView() {
        if (mContentView.getParent() == null) {
            mRootView.addView(mContentView);
        }
        mRootView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.VISIBLE);

        return true;
    }

    boolean dismissView() {
        mContentView.setVisibility(View.GONE);
        return true;
    }

    boolean removeView() {
        mRootView.removeView(mContentView);
        return true;
    }

    boolean isShowing() {
        boolean isRootViewVisible = mRootView.getVisibility() == View.VISIBLE ? true : false;
        boolean isContentViewAdded = false;
        ViewParent parent = mContentView.getParent();
        if (parent != null && parent == mRootView) {
            isContentViewAdded = true;
        }
        boolean isContentVisible = mContentView.getVisibility() == View.VISIBLE ? true : false;
        return isContentViewAdded && isContentVisible && isRootViewVisible;
    }

}
