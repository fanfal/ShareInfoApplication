package com.au.shareinfoapplication.dagger;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class SIModel {
    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
