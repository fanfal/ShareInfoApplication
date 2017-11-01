package com.au.shareinfoapplication.signin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.au.shareinfoapplication.R;
import com.au.shareinfoapplication.SIApplication;
import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.account.model.SIAccount;
import com.au.shareinfoapplication.network.SIHttpUtil;
import com.au.shareinfoapplication.network.ServiceConfig;
import com.au.shareinfoapplication.signin.contract.SignUpView;
import com.au.shareinfoapplication.signin.interactor.SignUpInteractor;
import com.au.shareinfoapplication.widget.SIDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.au.shareinfoapplication.utils.ValidateUtil.validatePassword;
import static com.au.shareinfoapplication.utils.ValidateUtil.validatePhoneNumber;

public class SignUpFragment extends Fragment implements SignUpView {
    public static final String TAG = "SignUpFragment";
    @BindView(R.id.input_phone_num)
    EditText inputPhoneNum;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Inject
    SIAccountManager siAccountManager;
    @Inject
    SIHttpUtil siHttpUtil;
    @Inject
    ServiceConfig serviceConfig;
    private SignUpInteractor signUpInteractor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment_layout, null);
        ButterKnife.bind(this, view);
        SIApplication.getSiComponent().inject(this);
        signUpInteractor = new SignUpInteractor(siHttpUtil, serviceConfig, this);
        return view;
    }

    @OnClick(R.id.sign_in_link)
    public void onSignUpLinkClicked() {
        ((AuthenticationActivity) getActivity()).showSignInFragment();
    }

    @OnClick(R.id.btn_sign_up)
    public void onLoginClicked() {
        if (validate()) {
            signUpInteractor.signUp(inputPhoneNum.getText().toString(), inputPassword.getText().toString());
        }
    }

    public boolean validate() {
        String num = inputPhoneNum.getText().toString();
        String password = inputPassword.getText().toString();

        if (validatePhoneNumber(num)) {
            inputPhoneNum.setError(null);
        } else {
            inputPhoneNum.setError(getString(R.string.phone_num_error_message));
            return false;
        }

        if (validatePassword(password)) {
            inputPassword.setError(null);
        } else {
            inputPassword.setError(getString(R.string.password_character_error_message));
            return false;
        }
        return true;

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void signUpError() {
        new SIDialogFragment.Builder(getContext())
                .setTitle(R.string.error_title)
                .setMessage(R.string.sign_up_error_message)
                .setPositiveButton(R.string.close, null)
                .show(getFragmentManager());
    }

    @Override
    public void signUpFailed() {
        new SIDialogFragment.Builder(getContext())
                .setTitle(R.string.failed_title)
                .setMessage(R.string.sign_in_failed_message)
                .setPositiveButton(R.string.close, null)
                .show(getFragmentManager());

    }

    @Override
    public void signUpSuccess(SIAccount account) {
        siAccountManager.addUserAccountExplicitly(account);
        getActivity().finish();
    }
}
