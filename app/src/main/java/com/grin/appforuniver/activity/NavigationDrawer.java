package com.grin.appforuniver.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.UserInterface;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.ConsultationFragment;
import com.grin.appforuniver.fragments.HomeFragment;
import com.grin.appforuniver.fragments.UserAccountFragment;
import com.grin.appforuniver.fragments.ScheduleFragment;

import java.io.Serializable;
import java.util.Objects;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public final String TAG = NavigationDrawer.class.getSimpleName();

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private User mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        navigationDrawer();

        PreferenceUtils.context = this;
        PreferenceUtils.saveUserToken(null);

        getMe();
//        mUser = PreferenceUtils.getSaveUser();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            mNavigationView.setCheckedItem(R.id.nav_home);
        }

        userInfo();

        mNavigationView.getHeaderView(0).findViewById(R.id.nav_log_or_registr).setOnClickListener(this);

    }

    private void getMe() {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

//        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken());
        Call<User> call = userInterface.getMe();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken().isEmpty()) {
                        PreferenceUtils.saveUser(response.body());
                        PreferenceUtils.saveUserRoles(response.body().getRoles());
                        mUser = response.body();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getApplicationContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
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

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
    }

    @SuppressLint("SetTextI18n")
    public void userInfo() {
        if(mUser != null) {
            TextView nvHeaderFirstLastNameTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_firstName_lastName);
            nvHeaderFirstLastNameTv.setVisibility(View.VISIBLE);
            nvHeaderFirstLastNameTv.setText(mUser.getFirstName() + " " + mUser.getLastName());

            TextView nvHeaderUserEmailTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
            nvHeaderUserEmailTv.setVisibility(View.VISIBLE);
            nvHeaderUserEmailTv.setText(mUser.getEmail());

            if (PreferenceUtils.getUserRoles().contains("ROLE_ADMIN")) { mNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(true); }

            mNavigationView.getHeaderView(0).findViewById(R.id.nav_log_or_registr).setVisibility(View.GONE);
            mNavigationView.getMenu().findItem(R.id.nav_personal_area).setVisible(true);
        } else {
            mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_firstName_lastName).setVisibility(View.GONE);
            mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_email).setVisibility(View.GONE);
            mNavigationView.getHeaderView(0).findViewById(R.id.nav_log_or_registr).setVisibility(View.VISIBLE);
            mNavigationView.getMenu().findItem(R.id.nav_personal_area).setVisible(false);
        }
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


        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_log_or_registr:
                startActivityForResult(new Intent(this, LoginActivity.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            if(requestCode == 1) {
                mUser = PreferenceUtils.getSaveUser();
                userInfo();
//                Serializable serializableData = data.getSerializableExtra("person");
//                User person = (User) serializableData;
//                checkLogin(person);
            }
        }
    }
}
