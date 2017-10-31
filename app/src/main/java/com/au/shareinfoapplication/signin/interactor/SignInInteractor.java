package com.au.shareinfoapplication.signin.interactor;


import com.au.shareinfoapplication.network.HttpResponse;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.au.shareinfoapplication.signin.contract.SignInView;
import com.au.shareinfoapplication.signin.model.SignInResponse;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import utils.JsonUtil;

public class SignInInteractor {
    private SIHttpUtil siHttpUtil;
    private ServiceConfig serviceConfig;
    private SignInView signInView;

    public SignInInteractor(SIHttpUtil siHttpUtil, ServiceConfig serviceConfig, SignInView signInView) {
        this.siHttpUtil = siHttpUtil;
        this.serviceConfig = serviceConfig;
        this.signInView = signInView;
    }

    public void signIn(String phoneNum, String password) {
        signInView.showProgressBar();
        Single.fromCallable(new Callable<HttpResponse>() {
            @Override
            public HttpResponse call() throws Exception {
                return siHttpUtil.get(serviceConfig.generateSignInUrl());

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<HttpResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(HttpResponse httpResponse) {
                signInView.hideProgressBar();
                if (httpResponse.isSuccessful()) {
                    String token = JsonUtil.parseJson(httpResponse.getResponseString(), SignInResponse.class).getToken();
                    signInView.signInSuccess(token);
                } else {
                    signInView.signInFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                signInView.hideProgressBar();
                signInView.signInError();
            }
        });
    }
}
