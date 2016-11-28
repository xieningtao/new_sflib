package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class LocationViewHolder extends UserAndIndicatorViewHolder {

    public final ImageView mMapIv;
    public final TextView mAddressTv;
    public final View mProgress;
    public final View mMapContainer;
    public LocationViewHolder(View rootView) {
        super(rootView);
        mMapIv = (ImageView) rootView.findViewById(R.id.map_image);
        mAddressTv = (TextView) rootView.findViewById(R.id.adress);
        mProgress = rootView.findViewById(R.id.loc_progress);
        mMapContainer=rootView.findViewById(R.id.map_container);
    }
}
