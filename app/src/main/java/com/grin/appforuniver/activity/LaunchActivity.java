package com.grin.appforuniver.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.AuthInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.CheckInternetBroadcast;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import org.w3c.dom.Text;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * boot activity
 */
public class LaunchActivity extends AppCompatActivity implements CheckInternetBroadcast.ConnectivityReceiverListener {
    public final String TAG = LaunchActivity.class.getSimpleName();

    @BindView(R.id.network_error_view)
    ConstraintLayout networkErrorView;

    @BindView(R.id.activity_launch_tv)
    TextView try_to_login_with_192_168_0_1;

    CheckInternetBroadcast checkInternetBroadcast = new CheckInternetBroadcast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        ButterKnife.bind(this);

        try_to_login_with_192_168_0_1.setVisibility(View.GONE);

        registerReceiver(checkInternetBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void loginUser(String username, String password) {

        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);
        Call<Map<Object, Object>> call = authInterface.loginUser(authenticationRequestDto);

        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Object, Object>> call, @NonNull Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        for (Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if (item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString(), getApplicationContext());
                            }
                        }

                        getMe();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Object, Object>> call, @NonNull Throwable t) {
//                if (t.getMessage().contains("Failed to connect to /194.9.70.244:8075")) {
//                    findViewById(R.id.network_error_view).setVisibility(View.VISIBLE);
//                    findViewById(R.id.activity_launch_tv).setVisibility(View.VISIBLE);
//                }
            }
        });

    }

    private void getMe() {

        Context context = getApplicationContext();

        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken(this));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !PreferenceUtils.getUserToken(context).isEmpty()) {
                        PreferenceUtils.saveUser(response.body(), context);
                        PreferenceUtils.saveUserRoles(response.body().getRoles(), context);

                        Intent intent = new Intent(context, NavigationDrawer.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CheckInternetBroadcast.connectivityReceiverListener = this;
    }

    private void checkConnection(boolean isConnected) {
        if (!isConnected) {
            try_to_login_with_192_168_0_1.setVisibility(View.VISIBLE);
            networkErrorView.setVisibility(View.VISIBLE);
        } else {
            try_to_login_with_192_168_0_1.setVisibility(View.GONE);
            networkErrorView.setVisibility(View.GONE);

            String sharedUsername = PreferenceUtils.getUsername(this);
            String sharedUserPassword = PreferenceUtils.getPassword(this);

            if (sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
                loginUser(sharedUsername, sharedUserPassword);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection(isConnected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(checkInternetBroadcast);
        CheckInternetBroadcast.connectivityReceiverListener = null;
    }
}
