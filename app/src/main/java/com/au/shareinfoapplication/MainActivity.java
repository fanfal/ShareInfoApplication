package com.au.shareinfoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.au.shareinfoapplication.account.SIAccountManager;
import com.au.shareinfoapplication.me.MeContentFragment;
import com.au.shareinfoapplication.me.MeFragment;
import com.au.shareinfoapplication.signin.AuthenticationActivity;
import com.au.shareinfoapplication.traffic.BaseMapFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @Inject
    SIAccountManager siAccountManager;
    TextView drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SIApplication.getSiComponent().inject(this);
        initView();
        replaceFragment(new BaseMapFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDrawerTitle();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.traffic_info) {
            replaceFragment(new BaseMapFragment());
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_me) {
            onNavMeTabClicked();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        drawerTitle = navigationView.getHeaderView(0).findViewById(R.id.drawer_title);
        initDrawerTitle();
    }

    public void initDrawerTitle() {
        if (siAccountManager.isUserLogin()) {
            drawerTitle.setText(siAccountManager.getUserPhoneNum());
            drawerTitle.setOnClickListener(null);
        } else {
            drawerTitle.setText(R.string.sign_in_title);
            drawerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity();
                }
            });
        }
    }

    private void startActivity() {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commitNowAllowingStateLoss();
    }

    private void onNavMeTabClicked() {
        replaceFragment(new MeFragment());
    }
}
