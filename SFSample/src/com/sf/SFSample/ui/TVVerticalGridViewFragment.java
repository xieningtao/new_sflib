package com.sf.SFSample.ui;

import android.os.Bundle;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.SFSample.R;

/**
 * Created by xieningtao on 15-12-23.
 */
public class TVVerticalGridViewFragment extends TVLogicGidViewFragment {

    private VerticalGridView mVerticalGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.example.androidtv.R.layout.fragment_animation_verticalgridview, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mVerticalGridView= (VerticalGridView) view.findViewById(R.id.animation_grid);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mVerticalGridView;
    }

}
