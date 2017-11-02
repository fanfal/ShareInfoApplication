package com.au.shareinfoapplication.traffic;


import android.net.Uri;

import com.au.shareinfoapplication.model.CarInfo;
import com.au.shareinfoapplication.model.Location;
import com.au.shareinfoapplication.model.ShareInfo;
import com.au.shareinfoapplication.network.HttpResponse;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.au.shareinfoapplication.traffic.model.ShareBusInfoResponse;
import com.au.shareinfoapplication.traffic.model.TrafficInfoResponse;
import com.au.shareinfoapplication.utils.JsonUtil;
import com.baidu.mapapi.map.MyLocationData;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class TrafficInfoInteractor {
    private final String CAR_NUMBER_QUERY_PARAMETER = "carNumber";
    private final String UUID = "uuid";

    interface CallBack {
        void shareInfoSuccess(ShareBusInfoResponse response);

        void obtainTrafficInfoSuccess(List<ShareInfo> shareInfos);

        void removeShareBusInfoSuccess();
    }

    private ServiceConfig serviceConfig;
    private SIHttpUtil httpUtil;
    private CallBack callBack;

    public TrafficInfoInteractor(ServiceConfig serviceConfig, SIHttpUtil httpUtil, CallBack callBack) {
        this.serviceConfig = serviceConfig;
        this.httpUtil = httpUtil;
        this.callBack = callBack;
    }

    public void shareBusInfo(final String busNumber, final MyLocationData myLocationData) {
        Single.fromCallable(new Callable<ShareBusInfoResponse>() {
            @Override
            public ShareBusInfoResponse call() throws Exception {
                HttpResponse httpResponse = httpUtil.post(serviceConfig.generateShareBusInfoUrl(),
                        JsonUtil.toJson(createShareInfo(busNumber, myLocationData)),
                        httpUtil.createTokenHeader());
                return JsonUtil.parseJson(httpResponse.getResponseString(), ShareBusInfoResponse.class);
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<ShareBusInfoResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ShareBusInfoResponse response) {
                callBack.shareInfoSuccess(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void queryTrafficInfoWithCarNumber(final String inputCarNumber) {
        Single.fromCallable(new Callable<List<ShareInfo>>() {
            @Override
            public List<ShareInfo> call() throws Exception {
                Uri uri = Uri.parse(serviceConfig.generateObtainBusInfoUrl())
                        .buildUpon()
                        .appendQueryParameter(CAR_NUMBER_QUERY_PARAMETER, inputCarNumber)
                        .build();
                HttpResponse httpResponse = httpUtil.get(uri.toString());
                return JsonUtil.parseJson(httpResponse.getResponseString(), TrafficInfoResponse.class).getResult();
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<List<ShareInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<ShareInfo> shareInfos) {
                callBack.obtainTrafficInfoSuccess(shareInfos);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void removeSharedBusInfo(final String sharedBusInfoUuid) {
        Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Uri uri = Uri.parse(serviceConfig.generateRemoveBusInfoUrl())
                        .buildUpon()
                        .appendQueryParameter(UUID, sharedBusInfoUuid)
                        .build();
                HttpResponse httpResponse = httpUtil.get(uri.toString(), httpUtil.createTokenHeader());
                return httpResponse.isSuccessful();
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean)
                    callBack.removeShareBusInfoSuccess();
            }

            @Override
            public void onError(Throwable throwable) {

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
