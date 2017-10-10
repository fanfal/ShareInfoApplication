package com.au.shareinfoapplication;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


public class SIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
