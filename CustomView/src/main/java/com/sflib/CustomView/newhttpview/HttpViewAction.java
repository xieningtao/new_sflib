package com.sflib.CustomView.newhttpview;

/**
 * Created by xieningtao on 15-12-29.
 */
public interface HttpViewAction {

    void showHttpLoadingView(boolean hasData);

    void showHttpViewNOData(boolean hasData);

    void showHttpViewNoNetwork(boolean hasData);
}
