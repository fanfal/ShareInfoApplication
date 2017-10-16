package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.shareinfoapplication.BaseUI.BaseFragment;
import com.au.shareinfoapplication.BaseUI.BasePresenter;
import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseMapFragment extends BaseFragment<BasePresenter> {
    public static final String TAG = "BaseMapFragment";
    private static final String LOCATION_TYPE = "bd09ll";
    @BindView(R.id.map_view)
    MapView mapView;

    @Inject
    SIHttpUtil httpUtil;
    protected BaiduMap baiduMap;
    protected LocationClient locationClient = null;

    public BDAbstractLocationListener listener = new LocationListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_map_layout, null);
        SIApplication.getSiComponent().inject(this);
        ButterKnife.bind(this, view);
        initLocationClient();
        initMap();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        locationClient.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public BasePresenter getPresenter() {
        return new BaseMapFragmentPresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void initLocationClient() {
        locationClient = new LocationClient(getContext());
        locationClient.registerLocationListener(listener);
        locationClient.setLocOption(getLocationClientOption());
    }

    private void initMap() {
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setTrafficEnabled(true);
        baiduMap.setBuildingsEnabled(true);
    }

    @NonNull
    private LocationClientOption getLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType(LOCATION_TYPE); // 设置坐标类型
        option.setScanSpan(1000);
        return option;
    }

    private class LocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(mapStatusUpdate);
        }
    }
}
