package com.au.shareinfoapplication.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.au.shareinfoapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity {
    @BindView(R.id.authentication_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showSignInFragment();

    }

    protected void showSignInFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new SignInFragment())
                .addToBackStack(SignInFragment.TAG)
                .commitAllowingStateLoss();
        getSupportActionBar().setTitle(R.string.sign_in_title);
    }

    protected void showSignUpFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new SignUpFragment())
                .addToBackStack(SignUpFragment.TAG)
                .commitAllowingStateLoss();
        getSupportActionBar().setTitle(R.string.sign_up_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
