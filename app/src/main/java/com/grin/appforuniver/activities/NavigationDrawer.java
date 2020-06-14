package com.grin.appforuniver.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.utils.CircularTransformation;
import com.grin.appforuniver.utils.Constants;
import com.grin.appforuniver.utils.LocaleUtils;
import com.grin.appforuniver.utils.ThemeUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;

public class NavigationDrawer extends AppCompatActivity {

    private static final String TAG = NavigationDrawer.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;

    private Unbinder mUnbinder;

    //region BindView
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private HeaderViewHolder mHeaderView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switch (ThemeUtils.getTheme()) {
            case ThemeUtils.Theme.SYSTEM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case ThemeUtils.Theme.LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case ThemeUtils.Theme.DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        super.onCreate(savedInstanceState);
        LocaleUtils.loadLocale(this);
        setContentView(R.layout.activity_navigation_drawer);
        mUnbinder = ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        mHeaderView = new HeaderViewHolder(header);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_schedule,
                R.id.nav_consultation,
                R.id.nav_admin,
                R.id.nav_personal_area,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("SetTextI18n")
    public void userInfo() {
        mHeaderView.userLastFirstName.setText(AuthManager.getInstance().getFirstName() + " " + AuthManager.getInstance().getLastName());
        mHeaderView.email.setText(AuthManager.getInstance().getEmail());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor.getInstance())
                .build();

        Uri photoUri = Uri.parse(Constants.API_BASE_URL + "photo/current_user/get_avatar");
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();
        picasso
                .load(photoUri)
                .placeholder(R.drawable.account_circle_outline)
                .error(R.drawable.ic_warning)
                .transform(new CircularTransformation(0))
                .into(mHeaderView.userIcon, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
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
        AuthManager.getInstance().cancelRequest();
    }

    protected static class HeaderViewHolder {
        @BindView(R.id.user_name_icon)
        protected ImageView userIcon;
        @BindView(R.id.nav_header_firstName_lastName)
        protected TextView userLastFirstName;
        @BindView(R.id.nav_header_email)
        protected TextView email;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
