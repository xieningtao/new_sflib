package com.sf.SFSample.nybao;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.basesmartframe.baseui.BasePullListFragment;
import com.sf.SFSample.R;
import com.sf.utils.baseutil.UnitHelp;

/**
 * Created by NetEase on 2016/10/10 0010.
 */
abstract public class NYBasePullListFragment<T> extends BasePullListFragment<T> {
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
//        getPullToRefreshListView().getRefreshableView().setScrollBarSize(0);
    }
}
