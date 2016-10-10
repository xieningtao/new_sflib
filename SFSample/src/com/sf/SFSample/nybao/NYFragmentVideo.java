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
import com.sf.SFSample.nybao.bean.NYVideoBean;
import com.sf.SFSample.ui.VideoPlayActivity;
import com.sf.loglib.L;
import com.sf.utils.baseutil.UnitHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentVideo extends BasePullListFragment<NYVideoBean> {

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
        getPullToRefreshListView().getRefreshableView().setFastScrollAlwaysVisible(false);
    }

    @Override
    protected boolean onRefresh() {
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYVideo");
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "videos: " + list);
                List<NYVideoBean> nyNewsBeanLis = new ArrayList<NYVideoBean>();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        Gson gson = new Gson();
                        NYVideoBean videoBean = gson.fromJson(mlObject.getString("videoContent"), NYVideoBean.class);
                        nyNewsBeanLis.add(videoBean);
                    }
                }
                finishRefreshOrLoading(nyNewsBeanLis, false);
            }
        });
        return false;
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.ny_video_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, NYVideoBean bean) {
        help.setImageBuilder(R.id.video_iv, bean.getCover());
        help.setText(R.id.video_title_tv, bean.getTitle());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NYVideoBean videoBean = getPullItem(position);
        VideoPlayActivity.jump2VideoPlay(getActivity(), videoBean.getMp4_url());
    }
}
