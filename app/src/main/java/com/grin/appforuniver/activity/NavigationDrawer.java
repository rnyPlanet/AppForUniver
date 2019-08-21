package com.grin.appforuniver.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.PersonalAreaFragment;

import es.dmoral.toasty.Toasty;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final String TAG = NavigationDrawer.class.getSimpleName();

    public NavigationView getNavigationView() { return mNavigationView; }

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private User mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        navigationDrawer();

        mUser = PreferenceUtils.getSaveUser(this);

        userInfo();

        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

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

    private void userInfo() {
        TextView nvHeaderFirstLastNameTv = mNavigationView.getHeaderView(0).findViewById(R.id.nv_header_firstName_lastName_tv);
        nvHeaderFirstLastNameTv.setText(mUser.getFirstName() + " " + mUser.getLastName());

        TextView nvHeaderUserEmailTv = mNavigationView.getHeaderView(0).findViewById(R.id.nv_header_user_email_tv);
        nvHeaderUserEmailTv.setText(mUser.getEmail());

        if(PreferenceUtils.getUserRoles(this).contains("ROLE_ADMIN")) {
            mNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
            Toasty.normal(this, "admin", Toasty.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.nav_home: {
                break;
            }

            case R.id.nav_gallery: {
                break;
            }

            case R.id.nav_share: {
                break;
            }

            case R.id.nav_send: {
                break;
            }

            case R.id.nav_admin: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminFragment()).commit();
                break;
            }

            case R.id.nav_personal_area: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalAreaFragment()).commit();
                break;
            }

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
