package com.au.shareinfoapplication.me;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.base.BasePresenter;

public class MeFragmentPresenter extends BasePresenter {
    private SIAccountManager siAccountManager;
    private MeFragmentViewInterface view;

    public MeFragmentPresenter(SIAccountManager siAccountManager, MeFragmentViewInterface view) {
        this.siAccountManager = siAccountManager;
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        if (siAccountManager.isUserLogin()) {
            view.updateAccountName(siAccountManager.getUserPhoneNum());
        }

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewCreated() {

    }
}
