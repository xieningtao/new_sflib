package com.example.androidtv.module.home;


import com.example.androidtv.module.bean.TVHomeDataModel;

import java.util.List;

/**
 * Created by xieningtao on 15-9-15.
 */
public class TVGameDataProvider {
    private static final TVGameDataProvider mCategoryDataProvider = new TVGameDataProvider();

    private TVGameDataProvider() {

    }

    public static TVGameDataProvider getInstance() {
        return mCategoryDataProvider;
    }

    public List<TVHomeDataModel.TVCategoryBean> getCategoryList(int pageIndex) {
        return TVGameManager.getInstance().getCategoryMap(pageIndex + "");
    }

    public int getTotalPageCount() {
        return TVGameManager.getInstance().getTotalPageCount();
    }

    public List<TVHomeDataModel.TVDetailListBean> getDetailList(int pageIndex) {
        return TVGameManager.getInstance().getDetailListMap(pageIndex + "");
    }
}
