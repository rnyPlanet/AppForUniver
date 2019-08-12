package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.authInterface.AuthInterface;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_launch);

        String sharedUsername = PreferenceUtils.getUsername(this);
        String sharedUserPassword = PreferenceUtils.getPassword(this);

        if(sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
            loginUser(sharedUsername, sharedUserPassword);
            getMe();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void loginUser(String username, String password) {
        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);

        Call<Map<Object, Object>> call = authInterface.loginUser(new AuthenticationRequestDto(username, password));

        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(Call<Map<Object, Object>> call, Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        for(Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if(item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString(), getApplicationContext());
                            }
                        }

                        if(PreferenceUtils.getUsername(getApplicationContext()) == null || PreferenceUtils.getUsername(getApplicationContext()).isEmpty()) {
                            PreferenceUtils.saveUsername(username, getApplicationContext());
                            PreferenceUtils.savePassword(password, getApplicationContext());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<Object, Object>> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    private void getMe() {
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken(getApplicationContext()));

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        PreferenceUtils.saveUser(response.body(), getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), NavigationDrawer.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        });
    }


}
