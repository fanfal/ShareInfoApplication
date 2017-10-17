package com.au.shareinfoapplication.dagger;


import com.au.shareinfoapplication.traffic.BaseMapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SIModel.class)
public interface SIComponent {

    void inject(BaseMapFragment baseMapFragment);
}
