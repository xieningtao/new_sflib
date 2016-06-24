package com.example.androidtv.module.home;

import android.text.TextUtils;

import com.sflib.reflection.core.ThreadHelp;
import com.basesmartframe.baseutil.SFBus;
import com.sf.loglib.L;
import com.example.androidtv.module.bean.TVHomeDataModel;
import com.sf.httpclient.core.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xieningtao on 15-9-15.
 * this class is package access
 */
class TVGameManager implements TVGameInterface.TVCategoryHttpRequest {


    private final String TAG = getClass().getName();

    private static final TVGameManager sManager = new TVGameManager();

    private Map<String, TVHomeDataModel.TVCategoryData> mCategoryMap;

    private Map<String, List<TVHomeDataModel.TVDetailListBean>> mDetailMap;

    private volatile int mTotalCount = -1;

    private TVGameManager() {
        mCategoryMap = new ConcurrentHashMap<>();
        mDetailMap = new ConcurrentHashMap<>();
    }

    public static TVGameManager getInstance() {
        return sManager;
    }

    @Override
    public void getCategory(final AjaxParams requestParams, final TVGameInterface.TaskItem taskItem) {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {

                TVHomeDataModel.TVCategoryData categoryData = new TVHomeDataModel.TVCategoryData();
                List<TVHomeDataModel.TVCategoryBean> beans = new ArrayList<>();
                int pageSize = Integer.valueOf(requestParams.getUrlParams().get(TVGameModule.PAGESIZE));
                for (int i = 0; i < pageSize; i++) {
                    TVHomeDataModel.TVCategoryBean bean = new TVHomeDataModel.TVCategoryBean();
                    bean.setItemId(i);
                    bean.setItemName("bean" + i);
                    bean.setImgUrl("http://c.hiphotos.baidu.com/image/h%3D300/sign=e4194a09f0246b60640eb474dbf81a35/b90e7bec54e736d1753e98529d504fc2d56269be.jpg");
                    beans.add(bean);

                }
                categoryData.setItemList(beans);

                if (isFirstPage(requestParams)) {
                    TVGameInterface.CategoryCountEvent countEvent = new TVGameInterface.CategoryCountEvent(100);
                    //TODO add post method
//                    SFBus.post(countEvent);
                }
                int pageIndex = getPageIndex(requestParams);
                mCategoryMap.put(pageIndex + "", categoryData);

                TVGameInterface.CategoryResponse categoryResponse = new TVGameInterface.CategoryResponse(requestParams);
                //TODO add post method
//                SFBus.post(categoryResponse);

                L.info(TAG, "categoryResponse: " + categoryResponse);
            }
        });

    }

    @Override
    public void getDetailList(final AjaxParams requestParams, final TVGameInterface.TaskItem taskItem) {


    }

    private boolean isFirstPage(final AjaxParams params) {
        int page = getPageIndex(params);
        return page == 1 ? true : false;
    }

    private int getPageIndex(AjaxParams params) {
        ConcurrentHashMap<String, String> urlParams = params.getUrlParams();
        String page_str = urlParams.get(TVGameModule.PAGE);
        return Integer.valueOf(page_str);
    }

    public List<TVHomeDataModel.TVCategoryBean> getCategoryMap(String pageIndex) {
        if (TextUtils.isEmpty(pageIndex)) return new ArrayList<>();
        TVHomeDataModel.TVCategoryData data = mCategoryMap.get(pageIndex);
        if (data != null) {
            return data.getCategoryBeans();
        }
        return new ArrayList<>();
    }

    public List<TVHomeDataModel.TVDetailListBean> getDetailListMap(String pageIndex) {
        if (TextUtils.isEmpty(pageIndex)) return new ArrayList<>();
        List<TVHomeDataModel.TVDetailListBean> data = mDetailMap.get(pageIndex);
        if (data == null) {
            return new ArrayList<>();
        } else {
            return data;
        }
    }

    public int getTotalPageCount() {
        return mTotalCount;
    }

}
