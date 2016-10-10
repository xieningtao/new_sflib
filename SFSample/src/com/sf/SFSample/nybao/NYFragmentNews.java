package com.sf.SFSample.nybao;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseui.BasePullListFragment;
import com.google.gson.Gson;
import com.maxleap.FindCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYNewsBean;
import com.sf.loglib.L;
import com.sf.utils.baseutil.UnitHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentNews extends BasePullListFragment<NYNewsBean> {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
    }

    private void initListView() {
        getPullToRefreshListView().setBackgroundResource(R.color.ny_main_bg);
        Drawable drawable = getResources().getDrawable(R.drawable.ny_gray_divider);
        getPullToRefreshListView().getRefreshableView().setDivider(drawable);
        getPullToRefreshListView().getRefreshableView().setDividerHeight(UnitHelp.dip2px(getActivity(), 1));
        getPullToRefreshListView().getRefreshableView().setScrollBarSize(0);
    }

    @Override
    protected boolean onRefresh() {
        getNews();
        return false;
    }

    private void getNews() {
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYNews");
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "news: " + list);
                List<NYNewsBean> nyNewsBeanLis = new ArrayList<NYNewsBean>();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        Gson gson = new Gson();
                        NYNewsBean newsBean = gson.fromJson(mlObject.getString("content"), NYNewsBean.class);
                        nyNewsBeanLis.add(newsBean);
                    }
                }
                finishRefreshOrLoading(nyNewsBeanLis, false);
            }
        });

    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.ny_news_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, NYNewsBean bean) {
        help.setImageBuilder(R.id.news_iv, bean.getImg());
        help.setText(R.id.news_label_tv, bean.getRecSource());
        help.setText(R.id.news_title_tv, bean.getTitle());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
