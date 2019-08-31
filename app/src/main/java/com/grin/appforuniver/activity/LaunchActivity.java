package com.grin.appforuniver.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.LoginUtils;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


/**
 * boot activity
 */
public class LaunchActivity extends AppCompatActivity {
    public final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        ButterKnife.bind(this);

        checkLoginUser();

    }

    private Boolean mIs3g = true;
    private Boolean mIsWifi = true;

    private void checkInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // 3G confirm
        assert manager != null;
        mIs3g = Objects.requireNonNull(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).isConnectedOrConnecting();
        // wifi confirm
        mIsWifi = Objects.requireNonNull(manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).isConnectedOrConnecting();

    }

    private void checkLoginUser() {
        checkInternetConnection();

        String sharedUsername = PreferenceUtils.getUsername(this);
        String sharedUserPassword = PreferenceUtils.getPassword(this);

        if(mIs3g || mIsWifi) {
            if (sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
                LoginUtils.loginUser(sharedUsername, sharedUserPassword, LaunchActivity.this);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            findViewById(R.id.activity_launch_tv).setVisibility(View.VISIBLE);
            findViewById(R.id.network_error_view).setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkLoginUser();
    }

}
