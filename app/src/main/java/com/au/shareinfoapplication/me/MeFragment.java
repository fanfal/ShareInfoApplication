package com.au.shareinfoapplication.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.au.shareinfoapplication.MainActivity;
import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends BaseFragment<MeFragmentPresenter> implements MeFragmentViewInterface {
    @BindView(R.id.account_name)
    TextView textView;
    @Inject
    SIAccountManager siAccountManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SIApplication.getSiComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public MeFragmentPresenter getPresenter() {
        return new MeFragmentPresenter(siAccountManager, this);
    }

    @Override
    public void updateAccountName(String name) {
        textView.setText(name);
    }

    @OnClick(R.id.account_sign_out)
    public void onSignOutClicked() {
        if (!siAccountManager.removeAccount())
            return;
        ((MainActivity) getActivity()).initDrawerTitle();
    }
}
