package com.au.shareinfoapplication.account.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.au.shareinfoapplication.account.SIAccountAuthenticator;


public class AccountService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        SIAccountAuthenticator accountAuthenticator = new SIAccountAuthenticator(this);
        return accountAuthenticator.getIBinder();
    }
}
