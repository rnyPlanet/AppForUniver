package com.grin.appforuniver.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.ConsultationFragment;
import com.grin.appforuniver.fragments.HomeFragment;
import com.grin.appforuniver.fragments.ScheduleFragment;
import com.grin.appforuniver.fragments.UserAccountFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = NavigationDrawer.class.getSimpleName();

    private Unbinder mUnbinder;

    //region BindView
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    private HeaderViewHolder mHeaderView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        mUnbinder = ButterKnife.bind(this);
        View header = navigationView.getHeaderView(0);
        mHeaderView = new HeaderViewHolder(header);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_schedule);
        }
        userInfo();
    }

    @SuppressLint("SetTextI18n")
    public void userInfo() {
        mHeaderView.userLastFirstName.setText(AuthManager.getInstance().getFirstName() + " " + AuthManager.getInstance().getLastName());
        mHeaderView.userIcon.setText(userIconText(AuthManager.getInstance().getFirstName(), AuthManager.getInstance().getLastName()));
        mHeaderView.email.setText(AuthManager.getInstance().getEmail());

        navigationView.getMenu().findItem(R.id.nav_personal_area).setVisible(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthManager.getInstance().requestPersonalProfile();
        userInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public String userIconText(String firstName, String secondName) {
        char userFirstNameLetter = firstName.charAt(0);
        char userSecondNameLetter = secondName.charAt(0);
        return userFirstNameLetter + "" + userSecondNameLetter;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_schedule:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ScheduleFragment())
                        .commit();
                break;


            case R.id.nav_consultation:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ConsultationFragment())
                        .commit();
                break;


            case R.id.nav_admin:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AdminFragment())
                        .commit();
                break;


            case R.id.nav_personal_area:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new UserAccountFragment())
                        .commit();
                break;

            case R.id.nav_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected static class HeaderViewHolder {
        @BindView(R.id.user_name_icon)
        protected TextView userIcon;
        @BindView(R.id.nav_header_firstName_lastName)
        protected TextView userLastFirstName;
        @BindView(R.id.nav_header_email)
        protected TextView email;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
