package com.au.shareinfoapplication.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BasePresenter {
    public abstract void onCreate(@Nullable Bundle savedInstanceState);

    public abstract void onResume();

    public abstract void onStop();

    public abstract void onDestroy();

    public abstract void onViewCreated();

}
