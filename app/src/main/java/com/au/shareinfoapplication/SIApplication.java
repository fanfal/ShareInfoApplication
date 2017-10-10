package com.au.shareinfoapplication;

import android.app.Application;

import com.au.shareinfoapplication.dagger.DaggerSIComponent;
import com.au.shareinfoapplication.dagger.SIComponent;
import com.baidu.mapapi.SDKInitializer;


public class SIApplication extends Application {
    private static SIComponent siComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        initSIComponent();
    }

    private void initSIComponent() {
        siComponent = DaggerSIComponent.builder().build();
    }

    public static SIComponent getSiComponent() {
        return siComponent;
    }
}
