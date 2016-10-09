package com.sf.SFSample.nybao;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.baseui.BasePullListFragment;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYVideoBean;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentVideo extends BasePullListFragment<NYVideoBean> {
    @Override
    protected boolean onRefresh() {
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

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
