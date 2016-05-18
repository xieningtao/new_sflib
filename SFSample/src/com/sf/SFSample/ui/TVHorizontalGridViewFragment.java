package com.sf.SFSample.ui;

import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseutil.UnitHelp;
import com.example.androidtv.module.bean.TVHomeDataModel;
import com.example.androidtv.presenter.HomeHeadPresenter;
import com.example.androidtv.presenter.PairFixedPresenter;
import com.sf.SFSample.R;

/**
 * Created by xieningtao on 15-12-23.
 */
public class TVHorizontalGridViewFragment extends TVLogicGidViewFragment {

    private HorizontalGridView mHorizontalGridView;

    public TVHorizontalGridViewFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.example.androidtv.R.layout.fragment_animation_horizontalgridview, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mHorizontalGridView = (HorizontalGridView) view.findViewById(R.id.horizontal_gv);
//        View headView = LayoutInflater.from(getActivity()).inflate(
//                R.layout.banner_test_fragment, null);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mHorizontalGridView;
    }

    @Override
    protected int getColumOrRowNumber() {
        return 1;
    }

    @Override
    protected PresenterSelector createPresenterSelector() {

        return new PresenterSelector() {

            public Presenter getPresenter(Object item) {
                Bundle bundle = getArguments();
                int verticalExtra = 0;
                int horizontalExtra = 0;
                if (bundle != null) {
                    verticalExtra = bundle.getInt(TVBaseGridViewActivity.VERTICAL_EXTRA_SPACE);
                    horizontalExtra = bundle.getInt(TVBaseGridViewActivity.HORIZONTAL_EXTRA_SPACE);
                }
                verticalExtra += UnitHelp.dip2px(getActivity(), 16) * 2;
                horizontalExtra += UnitHelp.dip2px(getActivity(), 16) * 2;
                if (item instanceof TVHomeDataModel.TVDetailListBean) {
                    return new HomeHeadPresenter(getActivity(), verticalExtra, horizontalExtra);
                } else {
                    return new PairFixedPresenter(getActivity(), verticalExtra, horizontalExtra);
                }
            }
        };
    }
}
