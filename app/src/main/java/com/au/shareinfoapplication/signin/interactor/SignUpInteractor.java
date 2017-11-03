package com.au.shareinfoapplication.signin.interactor;

import com.au.shareinfoapplication.account.model.SIAccount;
import com.au.shareinfoapplication.network.HttpResponse;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.au.shareinfoapplication.signin.contract.SignUpView;
import com.au.shareinfoapplication.signin.model.SignUpRequest;
import com.au.shareinfoapplication.signin.model.SignUpResponse;
import com.au.shareinfoapplication.utils.JsonUtil;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;


public class SignUpInteractor {
    private SIHttpUtil siHttpUtil;
    private ServiceConfig serviceConfig;
    private SignUpView signUpView;

    public SignUpInteractor(SIHttpUtil siHttpUtil, ServiceConfig serviceConfig, SignUpView signUpView) {
        this.siHttpUtil = siHttpUtil;
        this.serviceConfig = serviceConfig;
        this.signUpView = signUpView;
    }

    public void signUp(final String phoneNum, final String password) {
        signUpView.showProgressBar();
        Single.fromCallable(new Callable<HttpResponse>() {
            @Override
            public HttpResponse call() throws Exception {
                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setPhoneNum(phoneNum);
                signUpRequest.setPassword(password);
                return siHttpUtil.post(serviceConfig.generateSignUpUrl(), JsonUtil.toJson(signUpRequest));
            }
        }).subscribeOn(Schedulers.io()).observeOn(mainThread()).subscribe(new SingleObserver<HttpResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(HttpResponse httpResponse) {
                signUpView.hideProgressBar();
                if (httpResponse.isSuccessful()) {
                    String token = JsonUtil.parseJson(httpResponse.getResponseString(), SignUpResponse.class).getToken();
                    SIAccount siAccount = new SIAccount.Builder()
                            .setPhoneNum(phoneNum)
                            .setPassWord(password)
                            .setToken(token).build();
                    signUpView.signUpSuccess(siAccount);
                } else {
                    signUpView.signUpFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                signUpView.hideProgressBar();
                signUpView.signUpError();
            }
        });
    }
}
