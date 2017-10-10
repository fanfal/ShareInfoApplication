package com.au.shareinfoapplication.dagger;


import com.au.shareinfoapplication.traffic.BaseMapFragment;

import dagger.Component;

@Component(modules = SIModel.class)
public interface SIComponent {

    void inject(BaseMapFragment baseMapFragment);
}
