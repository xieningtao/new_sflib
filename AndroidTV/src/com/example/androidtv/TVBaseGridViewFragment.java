package com.example.androidtv;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseview.newhttpview.HttpViewManager;
import com.basesmartframe.log.L;
import com.example.androidtv.module.home.TVGameModule;
import com.sf.httpclient.core.AjaxParams;

;


/**
 * Created by xieningtao on 15-9-11.
 */
abstract public class TVBaseGridViewFragment extends ZoomBaseGridFragment {

    protected final String TAG = getClass().getName();
    protected ArrayObjectAdapter mArrayObjectAdapter;
    protected View mRootView;
    private HttpViewManager mTVHttpViewManager;


    protected void initGridPresent() {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView instanceof VerticalGridView) {
            VerticalGridView verticalGridView = (VerticalGridView) recyclerView;
            verticalGridView.setNumColumns(getColumOrRowNumber());
        } else if (recyclerView instanceof HorizontalGridView) {
            HorizontalGridView horizontalGridView = (HorizontalGridView) recyclerView;
            horizontalGridView.setNumRows(getColumOrRowNumber());
        }
    }

    protected int getColumOrRowNumber() {
        return 1;
    }

    abstract protected PresenterSelector createPresenterSelector();


    @Override
    public void onResume() {
        super.onResume();

    }

    public void onNetworkStatusChanged(Boolean hasNetwork) {

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected int getPageIndex() {
        Bundle bundle = getArguments();
        int pageIndex = 1;
        if (bundle != null) {
            pageIndex = bundle.getInt("page_index") + 1;
        }
        return pageIndex;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRootView = view.findViewById(R.id.control_fl);
        initGridPresent();
        L.info(TAG, "method->onViewCreated,index: " + getPageIndex() + " mRootView addr: " + mRootView);
        PresenterSelector presenterSelector = createPresenterSelector();
        if (presenterSelector == null) {
            throw new NullPointerException("createPresenterSelector() method return null");
        }
        mArrayObjectAdapter = new ArrayObjectAdapter(presenterSelector);
        setAdapter(mArrayObjectAdapter);
    }

    public void notifyDatasetChange() {
        if (null != mArrayObjectAdapter) {
            mArrayObjectAdapter.notifyArrayItemRangeChanged(0, mArrayObjectAdapter.size());
        }
    }

    protected void showHttpLoadingView(boolean hasData) {
        if (mRootView instanceof ViewGroup) {
//            final ViewGroup viewGroup = (ViewGroup) mRootView;
//            if (mTVHttpViewManager == null) {
//                mTVHttpViewManager = new HttpViewManager(getActivity(), viewGroup);
//            }
//            if (!hasData) {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.LOADING_VIEW);
//                L.info(TAG, "method->showHttpLoadingView,index: " + getPageIndex() + " loading_view result: " + httpResult);
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
//                mTVHttpViewManager = new HttpViewManager(getActivity(), viewGroup);
//            }
//            if (hasData) {
//                mTVHttpViewManager.();
//            } else if (NetWorkManagerUtil.isNetworkAvailable()) {
//                boolean httpResult = mTVHttpViewManager.showHttpViewNOData(hasData);
//                L.info(TAG, "method->showHttpViewNOData,index: " + getPageIndex() + " no_data_view result: " + httpResult);
//            } else {
//                boolean httpResult = mTVHttpViewManager.showHttpViewNoNetwork(HttpViewManager.HttpViewType.NO_NETWORK_VIEW);
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
//                mTVHttpViewManager = new HttpViewManager(getActivity(), viewGroup);
//            }
//
//            if (hasData) {
//                mTVHttpViewManager.dismissAllHttpViews();
//            } else if (!NetWorkManagerUtil.isNetworkAvailable()) {
//                boolean httpResult = mTVHttpViewManager.showOnlyThisHttpView(HttpViewManager.HttpViewType.NO_NETWORK_VIEW);
//                L.info(TAG, "method->showHttpViewNOData,no_network_view result: " + httpResult);
//            }

        } else {
            L.error(TAG, "method->changeViewNoNetwork,mrootView is not viewGroup");
        }
    }

    protected boolean isTheCurrentPageResponse(AjaxParams params) {
        String responseIndex = parsePageIndex(params);
        String intentIndex = getPageIndex() + "";
        if (responseIndex.equals(intentIndex)) {
            return true;
        } else {
            return false;
        }
    }

    private String parsePageIndex(AjaxParams params) {
        if (params != null && params.getUrlParams() != null) {
            return params.getUrlParams().get(TVGameModule.PAGE);
        } else {
            return TVGameModule.FIRST_PAGE;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetData();
    }

    private void resetData() {
        if (mArrayObjectAdapter != null) {
            mArrayObjectAdapter.clear();
        }
        mArrayObjectAdapter = null;
        mRootView = null;
        mTVHttpViewManager = null;
    }
}
