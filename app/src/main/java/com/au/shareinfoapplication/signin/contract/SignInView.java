package com.au.shareinfoapplication.signin.contract;


public interface SignInView extends BaseAuthenticationView {
    void signInError();

    void signInFailed();

    void signInSuccess(String token);
}
