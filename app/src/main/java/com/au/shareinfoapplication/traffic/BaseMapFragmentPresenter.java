package com.au.shareinfoapplication.traffic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.base.BasePresenter;
import com.au.shareinfoapplication.traffic.contract.BaseMapFragmentView;
import com.au.shareinfoapplication.utils.PreUtil;
import com.baidu.mapapi.map.MyLocationData;


public class BaseMapFragmentPresenter extends BasePresenter {
    private TrafficInfoInteractor infoInteractor;
    private PreUtil preUtil;
    private BaseMapFragmentView view;
    private SIAccountManager siAccountManager;

    public BaseMapFragmentPresenter(TrafficInfoInteractor infoInteractor, PreUtil preUtil, BaseMapFragmentView view, SIAccountManager siAccountManager) {
        this.infoInteractor = infoInteractor;
        this.preUtil = preUtil;
        this.view = view;
        this.siAccountManager = siAccountManager;
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

    void shareBusInfo(String busNumber, MyLocationData myLocationData) {
        if (!siAccountManager.isUserLogin()) {
            view.showUserShouldLoginAlertDialog();
            return;
        }
        infoInteractor.shareBusInfo(busNumber, myLocationData);
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

    public void removeSharedBusInfo() {
        if (!siAccountManager.isUserLogin()) {
            view.showUserShouldLoginAlertDialog();
            return;
        }
        if (preUtil.getSharedBusMessageUuid() == null)
            return;
        infoInteractor.removeSharedBusInfo(preUtil.getSharedBusMessageUuid());
    }
}
