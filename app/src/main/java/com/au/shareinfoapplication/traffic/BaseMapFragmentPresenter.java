package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.au.shareinfoapplication.BaseUI.BasePresenter;
import com.baidu.mapapi.map.MyLocationData;


public class BaseMapFragmentPresenter extends BasePresenter {
    private TrafficInfoInteractor infoInteractor;

    public BaseMapFragmentPresenter(TrafficInfoInteractor infoInteractor) {
        this.infoInteractor = infoInteractor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewCreated() {

    }

    public void shareCareInfo(String carNumber, MyLocationData myLocationData) {
        infoInteractor.shareCareInfo(carNumber, myLocationData);
    }
}
