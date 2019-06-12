package com.sf.SFSample.nybao;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.maxleap.FindCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYVideoBean;
import com.sf.SFSample.ui.VideoPlayActivity;
import com.sf.loglib.L;
import com.sf.utils.baseutil.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentVideo extends NYBasePullListFragment<NYVideoBean> {

    private final int PAGE_SIZE=10;

    private void getVideos(boolean refresh){
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYVideo");
        newsQuery.setLimit(PAGE_SIZE);
        newsQuery.setSkip(refresh?0:getDataSize());
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "videos: " + list);
                boolean hasMoreData=false;
                List<NYVideoBean> nyNewsBeanLis = new ArrayList<NYVideoBean>();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        NYVideoBean videoBean = GsonUtil.parse(mlObject.getString("videoContent"), NYVideoBean.class);
                        nyNewsBeanLis.add(videoBean);
                    }
                    if(list.size()>=PAGE_SIZE){
                        hasMoreData=true;
                    }
                }
                finishRefreshOrLoading(nyNewsBeanLis, hasMoreData);
            }
        });
    }
    @Override
    protected boolean onRefresh() {
       getVideos(true);
        return false;
    }

    @Override
    protected boolean onLoadMore() {
        getVideos(false);
        return false;
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.ny_video_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, NYVideoBean bean) {
        help.setImageBuilder(R.id.video_iv, bean.getVideoCover());
        help.setText(R.id.video_title_tv, bean.getVideoTitle());
        help.setText(R.id.ny_video_diggest_tv,bean.getVideoDescription());
        help.setText(R.id.ny_video_label_tv,bean.getVideoLabel());
        help.setText(R.id.ny_video_count_tv,bean.getVideoCount()+"");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position=position-getHeadViewCount();
        NYVideoBean videoBean = getPullItem(position);
        VideoPlayActivity.jump2VideoPlay(getActivity(), videoBean.getVideoUrl(),videoBean.getVideoCover());
    }
}
