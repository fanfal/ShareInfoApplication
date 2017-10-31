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
import com.au.shareinfoapplication.signin.contract.SignInView;
import com.au.shareinfoapplication.signin.interactor.SignInInteractor;
import com.au.shareinfoapplication.widget.SIDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.au.shareinfoapplication.utils.ValidateUtil.validatePassword;
import static com.au.shareinfoapplication.utils.ValidateUtil.vlidatePhoneNumber;


public class SignInFragment extends Fragment implements SignInView {
    public static final String TAG = "SignInFragment";
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
    private SignInInteractor signInInteractor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_fragment_layout, null);
        ButterKnife.bind(this, view);
        SIApplication.getSiComponent().inject(this);
        signInInteractor = new SignInInteractor(siHttpUtil, serviceConfig, this);
        return view;
    }

    @OnClick(R.id.sign_up_link)
    public void onSignUpLinkClicked() {
        ((AuthenticationActivity) getActivity()).showSignUpFragment();
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        if (validate()) {
            signInInteractor.signIn(inputPhoneNum.getText().toString(), inputPassword.getText().toString());
        }
    }

    public boolean validate() {
        String num = inputPhoneNum.getText().toString();
        String password = inputPassword.getText().toString();

        if (vlidatePhoneNumber(num)) {
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
    public void signInError() {
        new SIDialogFragment.Builder(getContext())
                .setTitle(R.string.error_title)
                .setMessage(R.string.sign_in_error_message)
                .setPositiveButton(R.string.close, null)
                .show(getFragmentManager());

    }

    @Override
    public void signInFailed() {
        new SIDialogFragment.Builder(getContext())
                .setTitle(R.string.failed_title)
                .setMessage(R.string.sign_in_failed_message)
                .setPositiveButton(R.string.close, null)
                .show(getFragmentManager());
    }

    @Override
    public void signInSuccess(SIAccount account) {
        siAccountManager.addUserAccount(account);
        getActivity().finish();
    }
}
