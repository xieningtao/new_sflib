package com.sf.SFSample.nybao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.pickphoto.ActivityFragmentContainer;
import com.maxleap.FindCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYNewsBean;
import com.sf.loglib.L;
import com.sf.utils.baseutil.GsonUtil;
import com.sf.utils.baseutil.SFToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentNews extends NYBasePullListFragment<NYNewsBean> {

    private final int PAGE_SIZE = 10;

    @Override
    protected boolean onRefresh() {
        getNews(true);
        return false;
    }

    private void getNews(boolean refresh) {
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYNews");
        newsQuery.setLimit(PAGE_SIZE);
        newsQuery.setSkip(refresh ? 0 : getDataSize());
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "news: " + list);
                boolean hasMoreData = false;
                List<NYNewsBean> nyNewsBeanLis = new ArrayList<NYNewsBean>();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        NYNewsBean newsBean = GsonUtil.parse(mlObject.getString("content"), NYNewsBean.class);
                        nyNewsBeanLis.add(newsBean);
                    }
                    if (list.size() >= PAGE_SIZE) {
                        hasMoreData = true;
                    }
                }
                finishRefreshOrLoading(nyNewsBeanLis, hasMoreData);
            }
        });

    }

    @Override
    protected boolean onLoadMore() {
        getNews(false);
        return false;
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.ny_news_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, NYNewsBean bean) {
        help.setImageBuilder(R.id.news_iv, bean.getImageUrl());
        help.setText(R.id.news_label_tv, bean.getLabel());
        help.setText(R.id.news_title_tv, bean.getTitle());
    }

    @Override
    protected boolean onRefreshNoNetwork() {
        SFToast.showToast(R.string.no_network);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int curPos = position - getHeadViewCount();
        NYNewsBean bean = getPullItem(curPos);
        Intent intent = new Intent(getActivity(), ActivityFragmentContainer.class);
        intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, NYFragmentNewsDetail.class.getName());
        Bundle bundle = new Bundle();
        bundle.putString(NYFragmentNewsDetail.NEWS_ID, bean.getId());
//        bundle.putString(NYFragmentNewsDetail.NEWS_ID, bean.getId());
        intent.putExtra(ActivityFragmentContainer.BUNDLE_CONTAINER, bundle);
        startActivity(intent);
    }
}
