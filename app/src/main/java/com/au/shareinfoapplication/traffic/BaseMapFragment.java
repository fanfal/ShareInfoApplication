package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.base.BaseFragment;
import com.au.shareinfoapplication.base.BasePresenter;
import com.au.shareinfoapplication.model.ShareInfo;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.au.shareinfoapplication.traffic.contract.BaseMapFragmentView;
import com.au.shareinfoapplication.traffic.model.ShareBusInfoResponse;
import com.au.shareinfoapplication.utils.PreUtil;
import com.au.shareinfoapplication.widget.SIDialogFragment;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaseMapFragment extends BaseFragment<BasePresenter> implements TrafficInfoInteractor.CallBack, BaseMapFragmentView {
    public static final String TAG = "BaseMapFragment";
    private static final String LOCATION_TYPE = "bd09ll";
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.share_info_operation_view)
    View shareInfoOperationView;
    @BindView(R.id.car_number_input)
    AppCompatEditText inputEditText;
    @BindView(R.id.operate_button)
    TextView operateButton;
    @BindView(R.id.get_off_button)
    TextView getOffButton;
    @Inject
    SIHttpUtil httpUtil;
    @Inject
    ServiceConfig serviceConfig;
    @Inject
    PreUtil preUtil;
    @Inject
    SIAccountManager siAccountManager;

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
        initButton();
        presenter.updateButton();
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
            presenter = new BaseMapFragmentPresenter(new TrafficInfoInteractor(serviceConfig, httpUtil, this), preUtil, this, siAccountManager);
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

    private void initButton() {
        getOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeSharedBusInfo();
            }
        });
        getOffButton.setSelected(true);

        operateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShareBusInfoView();
            }
        });
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
    public void shareInfoSuccess(ShareBusInfoResponse response) {
        preUtil.setShareBusMessageUuid(response.getUuid());
        presenter.updateButton();
        Toast.makeText(getContext(), "Share success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void obtainTrafficInfoSuccess(List<ShareInfo> shareInfos) {
        if (shareInfos == null || shareInfos.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.no_traffic_info_message), Toast.LENGTH_SHORT).show();
            return;
        }
        baiduMap.clear();
        baiduMap.addOverlays(createTrafficInfoOverlay(shareInfos));
        toggleShareBusInfoView();
    }

    @Override
    public void removeShareBusInfoSuccess() {
        Toast.makeText(getActivity(), "removeShareBusInfoSuccess", Toast.LENGTH_LONG).show();
        preUtil.setShareBusMessageUuid(null);
        presenter.updateButton();
    }


    public void toggleShareBusInfoView() {
        if (shareInfoOperationView.getVisibility() == View.VISIBLE) {
            hideShareBusInfoView();
            operateButton.setSelected(false);
        } else {
            showShareBusInfoView();
            operateButton.setSelected(true);
        }
    }

    private void hideShareBusInfoView() {
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.top_to_bottom_anim);
        shareInfoOperationView.startAnimation(animation);
        shareInfoOperationView.setVisibility(View.GONE);
    }

    private void showShareBusInfoView() {
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_to_top_anim);
        shareInfoOperationView.startAnimation(animation);
        shareInfoOperationView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.boarding_button)
    public void onShareTrafficInfoButtonClicked() {
        if (!getInputCarNumber().isEmpty()) {
            shouldShare = true;
            hideShareBusInfoView();
        }
    }

    @OnClick(R.id.obtain_button)
    public void onObtainTrafficInfoButtonClicked() {
        presenter.queryTrafficInfoWithCarNumber(getInputCarNumber());
    }

    @Override
    public void showRemoveInfoButton() {
        getOffButton.setVisibility(View.VISIBLE);
        operateButton.setVisibility(View.GONE);
    }

    @Override
    public void showShareInfoButton() {
        getOffButton.setVisibility(View.GONE);
        operateButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void showUserShouldLoginAlertDialog() {
        new SIDialogFragment.Builder(getActivity())
                .setTitle(R.string.error_title)
                .setMessage(R.string.need_sign_in__dialog_alert)
                .setPositiveButton(R.string.close, null)
                .show(getActivity().getSupportFragmentManager());
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
            presenter.shareBusInfo(getInputCarNumber(), myLocationData);
        }
        shouldShare = false;
    }

    private String getInputCarNumber() {
        return inputEditText.getText().toString();
    }


    @NonNull
    private List<OverlayOptions> createTrafficInfoOverlay(List<ShareInfo> shareInfos) {
        List<OverlayOptions> optionList = new ArrayList();
        for (ShareInfo info :
                shareInfos) {
            LatLng point = new LatLng(info.getLocation().getLatitude(), info.getLocation().getLongitude());

            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.bus_icon);
            OverlayOptions markerOptions = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            optionList.add(markerOptions);

            OverlayOptions textOption = new TextOptions()
                    .text(getContext().getString(R.string.number_of_people_in_bus, info.getNumOfPeople()))
                    .position(point)
                    .fontSize(40);
            optionList.add(textOption);
        }
        return optionList;
    }

}
