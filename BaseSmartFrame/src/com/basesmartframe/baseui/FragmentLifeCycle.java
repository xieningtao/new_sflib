package com.basesmartframe.baseui;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public interface FragmentLifeCycle extends BaseLifeCycle {

    void onViewCreate();

    void onViewDestroy();
}
