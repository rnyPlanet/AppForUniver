package com.grin.appforuniver.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.WebServices.authInterface.AuthInterface;
import com.grin.appforuniver.data.WebServices.userInterface.UserInterface;
import com.grin.appforuniver.data.model.dto.AuthenticationRequestDto;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.activity_login_username_et)
    TextInputLayout usernameTIL;
    @BindView(R.id.activity_login_password_et)
    TextInputLayout passwordTIL;

    private Boolean mIfFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorWhite));
        }
    }

    private boolean validateLoginInput() {
        String loginInput = Objects.requireNonNull(usernameTIL.getEditText()).getText().toString().trim();

        if(loginInput.isEmpty()) {
            usernameTIL.setError("Field can't be empty");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }

    }
    private boolean validatePasswordInput() {
        String passwordInput = Objects.requireNonNull(passwordTIL.getEditText()).getText().toString().trim();

        if(passwordInput.isEmpty()) {
            passwordTIL.setError("Field can't be empty");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }

    }

    @OnClick(R.id.activity_login_login_btn)
    public void logIn() {
        if(!validateLoginInput() | !validatePasswordInput()) {
            return;
        }

        loginUser(Objects.requireNonNull(usernameTIL.getEditText()).getText().toString(), Objects.requireNonNull(passwordTIL.getEditText()).getText().toString());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(Constants.USERNAME_KEY, Objects.requireNonNull(usernameTIL.getEditText()).getText().toString());
        outState.putString(Constants.PASSWORD_KEY, Objects.requireNonNull(passwordTIL.getEditText()).getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mIfFirstLoad) {
            logIn();
        }

        mIfFirstLoad = false;

    }

    public void loginUser(String username, String password) {

        Context context = getApplicationContext();

        AuthInterface authInterface = ServiceGenerator.createService(AuthInterface.class);

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto(username, password);
        Call<Map<Object, Object>> call = authInterface.loginUser(authenticationRequestDto);

        findViewById(R.id.network_error_view).setVisibility(View.GONE);
        findViewById(R.id.activity_login_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_login_login_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_login_sign_in_btn).setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Map<Object, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Object, Object>> call, @NonNull Response<Map<Object, Object>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        for (Map.Entry<Object, Object> item : response.body().entrySet()) {
                            if (item.getKey().equals("token")) {
                                PreferenceUtils.saveUserToken(item.getValue().toString(), context);
                            }
                        }

                        if (PreferenceUtils.getUsername(context) == null) {
                            PreferenceUtils.saveUsername(username, context);
                            PreferenceUtils.savePassword(password, context);
                        }

                        getMe();
                    }
                } else {
                    Toasty.error(context, "Fail username OR acc is NOT ACTIVE", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Object, Object>> call, @NonNull Throwable t) {
                if (t.getMessage().contains("Failed to connect to /194.9.70.244:8075")) {
                    findViewById(R.id.network_error_view).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_login_ll).setVisibility(View.GONE);
                    findViewById(R.id.activity_login_login_btn).setVisibility(View.GONE);
                    findViewById(R.id.activity_login_sign_in_btn).setVisibility(View.GONE);
                }
            }
        });

    }

    private void getMe() {
        Context context = getApplicationContext();
        UserInterface userInterface = ServiceGenerator.createService(UserInterface.class);

        Call<User> call = userInterface.getMe(PreferenceUtils.getUserToken(context));

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
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}
