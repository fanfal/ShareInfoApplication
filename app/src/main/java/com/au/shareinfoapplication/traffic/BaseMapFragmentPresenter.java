package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.au.shareinfoapplication.base.BasePresenter;
import com.au.shareinfoapplication.traffic.contract.BaseMapFragmentView;
import com.au.shareinfoapplication.utils.PreUtil;
import com.baidu.mapapi.map.MyLocationData;


public class BaseMapFragmentPresenter extends BasePresenter {
    private TrafficInfoInteractor infoInteractor;
    private PreUtil preUtil;
    private BaseMapFragmentView view;

    public BaseMapFragmentPresenter(TrafficInfoInteractor infoInteractor, PreUtil preUtil, BaseMapFragmentView view) {
        this.infoInteractor = infoInteractor;
        this.preUtil = preUtil;
        this.view = view;
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

    void shareCarInfo(String carNumber, MyLocationData myLocationData) {
        infoInteractor.shareCarInfo(carNumber, myLocationData);
    }

    void queryTrafficInfoWithCarNumber(String inputCarNumber) {
        if (!inputCarNumber.isEmpty()) {
            infoInteractor.queryTrafficInfoWithCarNumber(inputCarNumber);
        }

    }

    void updateButton() {
        if (preUtil.getSharedBusMessageUuid() != null) {
            view.showRemoveInfoButton();
        } else {
            view.showShareInfoButton();
        }
    }

    public void removeSharedCarInfo() {
        infoInteractor.removeCarInfo(preUtil.getSharedBusMessageUuid());
    }
}
