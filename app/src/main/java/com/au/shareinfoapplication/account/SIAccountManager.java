package com.au.shareinfoapplication.account;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;

import com.au.shareinfoapplication.BuildConfig;
import com.au.shareinfoapplication.account.model.SIAccount;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SIAccountManager {
    private android.accounts.AccountManager accountManager;

    @Inject
    public SIAccountManager(android.accounts.AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public boolean isUserLogin() {
        return getLoginAccount() != null;
    }

    //Only Implement Add Account Explicitly, if need Account account , please get the response sign in Activity and return the result
    public void addUserAccountExplicitly(SIAccount siAccount) {
        Account account = new Account(siAccount.getPhoneNum(), BuildConfig.ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, siAccount.getPassword(), null);
        accountManager.setAuthToken(account, BuildConfig.ACCOUNT_TOKEN, siAccount.getToken());
    }

    public String getUserPhoneNum() {
        return isUserLogin() ? getLoginAccount().name : null;
    }

    public String getUserToken() {
        return accountManager.peekAuthToken(getLoginAccount(), BuildConfig.ACCOUNT_TOKEN);
    }

    public void removeAccount(AccountManagerCallback<Boolean> callback) {
        Account account = getLoginAccount();
        if (account != null) {
            accountManager.removeAccount(account, callback, null);
        }
    }

    private Account getLoginAccount() {
        for (Account account : accountManager.getAccountsByType(BuildConfig.ACCOUNT_TYPE)) {
            if (account != null) {
                return account;
            }
        }
        return null;
    }

}
