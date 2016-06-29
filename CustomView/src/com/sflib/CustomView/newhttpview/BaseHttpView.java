package com.sflib.CustomView.newhttpview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by xieningtao on 15-9-15.
 */
abstract public class BaseHttpView {
    protected final ViewGroup mRootView;
    protected final Context mContext;
    private final View mContentView;

    public BaseHttpView(Context context, ViewGroup rootView) {
        this.mContext = context;
        this.mRootView = rootView;
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        this.mContentView = createContentView(layoutInflater);

    }

    protected abstract View createContentView(LayoutInflater layoutInflater);

    boolean showView() {
        if (mContentView.getParent() == null) {
//            mRootView.removeView(mContentView);
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
