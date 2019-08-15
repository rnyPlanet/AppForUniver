package com.grin.appforuniver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.AdminFragment;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final String TAG = NavigationDrawer.class.getSimpleName();

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        navigationDrawer();

        user = PreferenceUtils.getSaveUser(this);

        userInfo();

    }

    private void navigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void userInfo() {
        TextView nvHeaderFirstLastNameTv = navigationView.getHeaderView(0).findViewById(R.id.nv_header_firstName_lastName_tv);
        nvHeaderFirstLastNameTv.setText(user.getFirstName() + " " + user.getLastName());

        TextView nvHeaderUserEmailTv = navigationView.getHeaderView(0).findViewById(R.id.nv_header_user_email_tv);
        nvHeaderUserEmailTv.setText(user.getEmail());

        if(PreferenceUtils.getUserRoles(this).contains("ROLE_ADMIN")) {
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
            Toasty.normal(this, "admin", Toasty.LENGTH_SHORT).show();
        }

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
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
            PreferenceUtils.saveUsername(null, getApplicationContext());
            PreferenceUtils.savePassword(null, getApplicationContext());
            PreferenceUtils.saveUser(null, getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
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
                this.setTitle(R.string.admin);
                break;
            }

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
