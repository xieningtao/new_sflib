package com.sf.baidulib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.basesmartframe.baseui.BaseFragment;

/**
 * Created by NetEase on 2016/8/12 0012.
 */

public class SFBaiduMapLocationFragment extends BaseFragment {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sf_baidu_map, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        showMyLocation();
    }

    private void showMyLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mBaiduMap.getMaxZoomLevel() - 2));
        BDLocation location = SFBaiduLocationManager.getInstance().getBDLocation();
        if (location != null) {
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
// 设置定位数据
            mBaiduMap.setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_geo);
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfiguration(config);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }
}