package com.au.shareinfoapplication.dagger;


import com.au.shareinfoapplication.MainActivity;
import com.au.shareinfoapplication.me.MeContentFragment;
import com.au.shareinfoapplication.me.MeFragment;
import com.au.shareinfoapplication.signin.SignInFragment;
import com.au.shareinfoapplication.signin.SignUpFragment;
import com.au.shareinfoapplication.traffic.BaseMapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SIModel.class)
public interface SIComponent {

    void inject(BaseMapFragment baseMapFragment);

    void inject(SignInFragment signInFragment);

    void inject(MainActivity mainActivity);

    void inject(SignUpFragment signUpFragment);

    void inject(MeContentFragment meFragment);

    void inject(MeFragment meFragment);
}
