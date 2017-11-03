package com.au.shareinfoapplication.signin.model;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.signin.AuthenticationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInAlertFragment extends Fragment {
    public static final String TAG = "SignInAlertFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_alert_fragment_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.sign_in_button)
    void onSignInClicked() {
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
    }
}
