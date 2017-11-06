package com.au.shareinfoapplication.signin.contract;


import com.au.shareinfoapplication.account.model.SIAccount;

public interface SignUpView extends BaseAuthenticationView {
    void signUpError();

    void signUpFailed(String message);

    void signUpSuccess(SIAccount account);
}
