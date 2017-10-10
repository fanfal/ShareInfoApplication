package com.au.shareinfoapplication.dagger;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class SIModel {
    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
