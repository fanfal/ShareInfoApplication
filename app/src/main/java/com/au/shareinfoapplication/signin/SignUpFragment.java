package com.au.shareinfoapplication.signin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.au.shareinfoapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static utils.ValidateUtil.validatePassword;
import static utils.ValidateUtil.vlidatePhoneNumber;

public class SignUpFragment extends Fragment {
    public static final String TAG = "SignUpFragment";
    @BindView(R.id.input_phone_num)
    EditText inputPhoneNum;
    @BindView(R.id.input_password)
    EditText inputPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.sign_in_link)
    public void onSignUpLinkClicked() {
        ((AuthenticationActivity) getActivity()).showSignInFragment();
    }

    @OnClick(R.id.btn_sign_up)
    public void onLoginClicked() {
        validate();
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
}
