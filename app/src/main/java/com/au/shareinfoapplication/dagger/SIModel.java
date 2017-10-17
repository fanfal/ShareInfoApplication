package com.au.shareinfoapplication.dagger;

import android.content.Context;

import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class SIModel {
    protected Context context;

    public SIModel(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public ServiceConfig provideServiceConfig(SIHttpUtil httpUtil, Context context) {
        return new ServiceConfig(httpUtil, context.getString(R.string.service_config_url));
    }


}
