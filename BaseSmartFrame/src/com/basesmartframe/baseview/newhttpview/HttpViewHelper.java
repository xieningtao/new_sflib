package com.basesmartframe.baseview.newhttpview;

class HttpViewHelper {
    private final HttpViewFactory mTVSimpleHttpViewFactory;

    public HttpViewHelper(HttpViewFactory mTVSimpleHttpViewFactory) {
        this.mTVSimpleHttpViewFactory = mTVSimpleHttpViewFactory;
    }

    public boolean isViewShowing(HttpViewManager.TVHttpViewType viewType) {
        if (viewType == null) return false;
        BaseHttpView httpView = mTVSimpleHttpViewFactory.createOrGetHttpView(viewType);
        if (httpView == null) return false;
        return httpView.isShowing();
    }

    public boolean showHttpView(HttpViewManager.TVHttpViewType viewType) {
        if (isViewShowing(viewType)) return true;
        if (viewType == null || viewType == HttpViewManager.TVHttpViewType.NONE) return false;
        BaseHttpView httpView = mTVSimpleHttpViewFactory.createOrGetHttpView(viewType);

//        L.info(mTVSimpleHttpViewFactory.getTAG(), "httpView object addr: " + httpView);
        return httpView.showView();
    }

    public boolean showOnlyThisHttpView(HttpViewManager.TVHttpViewType viewType) {
        if (isViewShowing(viewType)) return true;
        mTVSimpleHttpViewFactory.mRootView.removeAllViews();
        return showHttpView(viewType);
    }//refactor dismissHttpView function and dismissAllHttpViews

    public boolean dismissHttpView(HttpViewManager.TVHttpViewType viewType) {
        if (!isViewShowing(viewType)) return true;
        if (viewType == null) return false;
        BaseHttpView httpView = mTVSimpleHttpViewFactory.createOrGetHttpView(viewType);
        if (httpView.isShowing()) {
            return httpView.dismissView();
        } else {
            return false;
        }

    }

    boolean dismissHtppViewHelper(HttpViewManager.TVHttpViewType viewType) {
        BaseHttpView httpView = mTVSimpleHttpViewFactory.createOrGetHttpView(viewType);
        if (httpView == null) {
            return true;
        }
        return httpView.dismissView();
    }

    public void dismissAllHttpViews() {
        for (HttpViewManager.TVHttpViewType viewType : HttpViewManager.TVHttpViewType.values()) {
            if (isViewShowing(viewType)) {
                dismissHtppViewHelper(viewType);
            }
        }
    }

    public boolean removeHttpView(HttpViewManager.TVHttpViewType viewType) {
        if (viewType == null) return false;
        BaseHttpView httpView = mTVSimpleHttpViewFactory.createOrGetHttpView(viewType);
        if (httpView == null) return true;
        return httpView.removeView();
    }

    public void removeAllHttpViews() {
        for (HttpViewManager.TVHttpViewType viewType : HttpViewManager.TVHttpViewType.values()) {
            removeHttpView(viewType);
        }
    }
}