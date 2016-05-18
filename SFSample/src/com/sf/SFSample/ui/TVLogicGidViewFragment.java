package com.sf.SFSample.ui;

import android.os.Bundle;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.view.View;

import com.basesmartframe.baseutil.FP;
import com.basesmartframe.baseutil.NetWorkManagerUtil;
import com.basesmartframe.baseutil.SFBus;
import com.basesmartframe.baseutil.UnitHelp;
import com.basesmartframe.log.L;
import com.example.androidtv.TVBaseGridViewFragment;
import com.example.androidtv.module.bean.TVHomeDataModel;
import com.example.androidtv.module.home.TVGameDataProvider;
import com.example.androidtv.module.home.TVGameInterface;
import com.example.androidtv.presenter.WrapContentPresenter;

import java.util.List;

/**
 * Created by xieningtao on 15-9-11.
 */
public abstract class TVLogicGidViewFragment extends TVBaseGridViewFragment {

    private final int COLUME_NUMBER = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public TVLogicGidViewFragment() {

    }


    @Override
    protected int getColumOrRowNumber() {
        return COLUME_NUMBER;
    }

    @Override
    protected PresenterSelector createPresenterSelector() {
        return new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                Bundle bundle = getArguments();
                int verticalExtra = 0;
                int horizontalExtra = 0;
                if (bundle != null) {
                    verticalExtra = bundle.getInt(TVBaseGridViewActivity.VERTICAL_EXTRA_SPACE);
                    horizontalExtra = bundle.getInt(TVBaseGridViewActivity.HORIZONTAL_EXTRA_SPACE);
                }
                verticalExtra += UnitHelp.dip2px(getActivity(), 16) * 2;
                horizontalExtra += UnitHelp.dip2px(getActivity(), 16) * 2;
                return new WrapContentPresenter(getActivity(), verticalExtra, horizontalExtra);
            }
        };

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean hasData = isGridViewHasData();
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            doCategoryRequest(hasData);
        } else {
            forceUpdateView(false);
            boolean hasDataInModule = !FP.empty(TVGameDataProvider.getInstance().getCategoryList(getPageIndex()));
            showHttpViewNoNetwork(hasDataInModule);
        }

    }

    private boolean isGridViewHasData() {
        boolean hasData = false;
        if (mArrayObjectAdapter != null) {
            hasData = !FP.empty(mArrayObjectAdapter.unmodifiableList());
        }
        return hasData;
    }

    private void doCategoryRequest(boolean hasData) {
        int pageIndex = getPageIndex();
        TVGameInterface.CategoryRequest request = TVGameInterface.createCategoryRequest(pageIndex, 12);
        request.setTaskItem(createTaskId());
        SFBus.post(request);
        showHttpLoadingView(hasData);
    }

    @Override
    public void onNetworkStatusChanged(Boolean hasNetwork) {
        super.onNetworkStatusChanged(hasNetwork);
        if (NetWorkManagerUtil.isNetworkAvailable()) {
            boolean hasData = isGridViewHasData();
            if (!hasData) {
                doCategoryRequest(hasData);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onEvent(TVGameInterface.CategoryResponse response) {
        L.info(TAG, "method->updateData CategoryResponse,response: " + response);
        if (response == null) return;
        if (!isTheCurrentPageResponse(response.mParams)) return;
        if (mRootView != null) {
            forceUpdateView(true);
        }
    }

    private void updateCategoryGridView() {
        List<TVHomeDataModel.TVCategoryBean> categoryBeans = TVGameDataProvider.getInstance().getCategoryList(getPageIndex());
        boolean hasData = !FP.empty(categoryBeans);
        updateHttpView(hasData);
        mArrayObjectAdapter.clear();
        mArrayObjectAdapter.add(0,new TVHomeDataModel.TVDetailListBean());
        mArrayObjectAdapter.addAll(1, categoryBeans);
        notifyDatasetChange();
    }

    private boolean isDataChange(List<TVHomeDataModel.TVCategoryBean> modelBeans, List<TVHomeDataModel.TVCategoryBean> viewBeans) {
        if (FP.empty(modelBeans)) {
            return false;
        } else if (FP.empty(viewBeans)) {
            return true;
        } else {
            //only compare the first item
            TVHomeDataModel.TVCategoryBean modelBean = modelBeans.get(0);
            TVHomeDataModel.TVCategoryBean viewBean = viewBeans.get(0);
            if (modelBean.getItemId() == viewBean.getItemId()) {
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean forceUpdateView(boolean force) {
        if (force) {
            updateCategoryGridView();
            return true;
        } else {
            List<TVHomeDataModel.TVCategoryBean> modelBeans = TVGameDataProvider.getInstance().getCategoryList(getPageIndex());
            List<TVHomeDataModel.TVCategoryBean> viewBeans = mArrayObjectAdapter.unmodifiableList();
            if (isDataChange(modelBeans, viewBeans)) {
                updateCategoryGridView();
                return true;
            }
        }
        return false;
    }

    private TVGameInterface.TaskItem createTaskId() {
        return new TVGameInterface.TaskItem("categoryTask_" + getPageIndex());
    }


}
