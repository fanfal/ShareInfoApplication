package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.au.shareinfoapplication.BaseUI.BaseFragment;
import com.au.shareinfoapplication.BaseUI.BasePresenter;
import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
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
import butterknife.OnClick;


public class BaseMapFragment extends BaseFragment<BasePresenter> implements TrafficInfoInteractor.CallBack {
    public static final String TAG = "BaseMapFragment";
    private static final String LOCATION_TYPE = "bd09ll";
    @BindView(R.id.map_view)
    MapView mapView;

    @Inject
    SIHttpUtil httpUtil;
    @Inject
    ServiceConfig serviceConfig;
    protected BaiduMap baiduMap;
    protected LocationClient locationClient = null;

    private MyLocationData myLocationData = null;

    public BDAbstractLocationListener listener = new LocationListener();

    private BaseMapFragmentPresenter presenter;

    private boolean shouldShare = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SIApplication.getSiComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_map_layout, null);
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
        if (presenter == null) {
            presenter = new BaseMapFragmentPresenter(new TrafficInfoInteractor(serviceConfig, httpUtil, this));
        }
        return presenter;
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

    @Override
    public void shareInfoSuccess() {
        Toast.makeText(getContext(), "Share success", Toast.LENGTH_SHORT).show();
    }



    private class LocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();

            baiduMap.setMyLocationData(myLocationData);
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(mapStatusUpdate);
            shareTrafficInfo();
        }
    }

    private void shareTrafficInfo() {
        if (shouldShare) {
            presenter.shareCareInfo(myLocationData);
            shouldShare = false;
        }
    }


}
