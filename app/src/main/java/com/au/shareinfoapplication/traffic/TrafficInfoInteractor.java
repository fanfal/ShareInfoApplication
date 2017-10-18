package com.au.shareinfoapplication.traffic;


import android.net.Uri;

import com.au.shareinfoapplication.Model.CarInfo;
import com.au.shareinfoapplication.Model.Location;
import com.au.shareinfoapplication.Model.ShareInfo;
import com.au.shareinfoapplication.network.HttpResponse;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.baidu.mapapi.map.MyLocationData;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import utils.JsonUtil;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class TrafficInfoInteractor {
    final String CAR_NUMBER_QUERY_PARAMETER = "carNumber";

    interface CallBack {
        void shareInfoSuccess();

        void obtainTrafficInfoSuccess(ArrayList<ShareInfo> shareInfos);
    }

    private ServiceConfig serviceConfig;
    private SIHttpUtil httpUtil;
    private CallBack callBack;

    public TrafficInfoInteractor(ServiceConfig serviceConfig, SIHttpUtil httpUtil, CallBack callBack) {
        this.serviceConfig = serviceConfig;
        this.httpUtil = httpUtil;
        this.callBack = callBack;
    }

    public void shareCareInfo(final String carNumber, final MyLocationData myLocationData) {
        Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                HttpResponse httpResponse = httpUtil.post(serviceConfig.generateShareCarInfoUrl(), JsonUtil.toJson(createShareInfo(carNumber, myLocationData)));
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

    public void queryTrafficInfoWithCarNumber(final String inputCarNumber) {
        Single.fromCallable(new Callable<ArrayList<ShareInfo>>() {
            @Override
            public ArrayList<ShareInfo> call() throws Exception {
                Uri uri = Uri.parse(serviceConfig.generateObtainCarInfoUrl())
                        .buildUpon()
                        .appendQueryParameter(CAR_NUMBER_QUERY_PARAMETER, inputCarNumber)
                        .build();
                HttpResponse httpResponse = httpUtil.get(uri.toString());
                return JsonUtil.parseJson(httpResponse.getResponseString(), new TypeToken<ArrayList<ShareInfo>>() {
                }.getType());
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<ArrayList<ShareInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ArrayList<ShareInfo> shareInfos) {
                callBack.obtainTrafficInfoSuccess(shareInfos);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    private ShareInfo createShareInfo(String carNumber, MyLocationData myLocationData) {
        CarInfo carInfo = new CarInfo();
        carInfo.setCarNumber(carNumber);
        Location location = new Location();
        location.setLatitude(myLocationData.latitude);
        location.setLongitude(myLocationData.longitude);
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setCarInfo(carInfo);
        shareInfo.setLocation(location);
        return shareInfo;

    }
}
