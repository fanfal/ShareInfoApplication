package com.au.shareinfoapplication.signin.contract;


import com.au.shareinfoapplication.account.model.SIAccount;

public interface SignInView extends BaseAuthenticationView {
    void signInError();

    void signInFailed();

    void signInSuccess(SIAccount account);
}
