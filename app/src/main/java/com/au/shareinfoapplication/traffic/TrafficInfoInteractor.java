package com.au.shareinfoapplication.traffic;


import com.au.shareinfoapplication.Model.CarInfo;
import com.au.shareinfoapplication.Model.Location;
import com.au.shareinfoapplication.Model.ShareInfo;
import com.au.shareinfoapplication.network.HttpResponse;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.baidu.mapapi.map.MyLocationData;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import utils.JsonUtil;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class TrafficInfoInteractor {
    interface CallBack {
        void shareInfoSuccess();
    }

    private ServiceConfig serviceConfig;
    private SIHttpUtil httpUtil;
    private CallBack callBack;

    public TrafficInfoInteractor(ServiceConfig serviceConfig, SIHttpUtil httpUtil, CallBack callBack) {
        this.serviceConfig = serviceConfig;
        this.httpUtil = httpUtil;
        this.callBack = callBack;
    }

    public void shareCareInfo(final MyLocationData myLocationData) {
        Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                HttpResponse httpResponse = httpUtil.post(serviceConfig.generateShareCarInfoUrl(), JsonUtil.toJson(createShareInfo(myLocationData)));
                return httpResponse.isSuccessful();
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                callBack.shareInfoSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private ShareInfo createShareInfo(MyLocationData myLocationData) {
        CarInfo carInfo = new CarInfo();
        carInfo.setCarNumber("600");
        Location location = new Location();
        location.setLatitude(myLocationData.latitude);
        location.setLongitude(myLocationData.longitude);
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setCarInfo(carInfo);
        shareInfo.setLocation(location);
        return shareInfo;

    }
}