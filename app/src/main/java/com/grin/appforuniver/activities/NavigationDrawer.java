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
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.ConsultationFragment;
import com.grin.appforuniver.fragments.HomeFragment;
import com.grin.appforuniver.fragments.ScheduleFragment;
import com.grin.appforuniver.fragments.UserAccountFragment;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final String TAG = NavigationDrawer.class.getSimpleName();

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private User mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        navigationDrawer();

        PreferenceUtils.setContext(getApplicationContext());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).commit();
            mNavigationView.setCheckedItem(R.id.nav_schedule);
        }

        mUser = PreferenceUtils.getSaveUser();

        userInfo();

    }


    private void navigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @SuppressLint("SetTextI18n")
    public void userInfo() {
        if (mUser != null) {
            TextView nvHeaderFirstLastNameTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_firstName_lastName);
            nvHeaderFirstLastNameTv.setVisibility(View.VISIBLE);
            nvHeaderFirstLastNameTv.setText(mUser.getFirstName() + " " + mUser.getLastName());

            // Set user icon data
            setUserIconData(mUser.getFirstName(), mUser.getLastName());

            TextView nvHeaderUserEmailTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
            nvHeaderUserEmailTv.setVisibility(View.VISIBLE);
            nvHeaderUserEmailTv.setText(mUser.getEmail());

            mNavigationView.getHeaderView(0).findViewById(R.id.nav_log_or_registr).setVisibility(View.GONE);
            mNavigationView.getMenu().findItem(R.id.nav_personal_area).setVisible(true);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setUserIconData(String firstName, String secondName) {
        char userFirstNameLetter = firstName.charAt(0);
        char userSecondNameLetter = secondName.charAt(0);
        TextView userIcon = mNavigationView.getHeaderView(0).findViewById(R.id.user_name_icon);

        // Set up new data
        userIcon.setText(userFirstNameLetter + "" + userSecondNameLetter);

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
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

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
