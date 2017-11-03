package com.au.shareinfoapplication.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.signin.model.SignInAlertFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MeFragment extends Fragment {
    public static final String TAG = "MeFragment";
    @Inject
    SIAccountManager siAccountManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment_layout, null);
        SIApplication.getSiComponent().inject(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (siAccountManager.isUserLogin()) {
            showMeContentFragment();
        } else {
            showSignInAlertFragment();
        }
    }

    public void showSignInAlertFragment() {
        replaceChildFragment(new SignInAlertFragment(), SignInAlertFragment.TAG);
    }

    private void showMeContentFragment() {
        replaceChildFragment(new MeContentFragment(), MeContentFragment.TAG);
    }

    private void replaceChildFragment(Fragment fragment, String tag) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.me_fragment_content, fragment)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
    }
}
